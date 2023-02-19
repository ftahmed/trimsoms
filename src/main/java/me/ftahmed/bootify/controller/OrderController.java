package me.ftahmed.bootify.controller;

import static me.ftahmed.bootify.config.Constants.CARELABEL;
import static me.ftahmed.bootify.config.Constants.HANGTAG;
import static me.ftahmed.bootify.config.Constants.UPLOAD_DIR;
import static me.ftahmed.bootify.config.Constants.orderTypeValues;
import static me.ftahmed.bootify.config.Constants.productValues;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.ftahmed.bootify.domain.Order;
import me.ftahmed.bootify.domain.PurchaseOrder;
import me.ftahmed.bootify.domain.PurchaseOrderDetails;
import me.ftahmed.bootify.domain.Vendor;
import me.ftahmed.bootify.service.CompositionItemService;
import me.ftahmed.bootify.service.OrderService;
import me.ftahmed.bootify.service.PartService;
import me.ftahmed.bootify.service.PurchaseOrderDetailsService;
import me.ftahmed.bootify.service.VendorService;
import me.ftahmed.bootify.util.ByteUtils;
import me.ftahmed.bootify.util.WebUtils;

@Controller
@Slf4j
public class OrderController {

    @Autowired 
    private OrderService orderService;
    @Autowired 
    private PartService partService;
    @Autowired 
    private CompositionItemService compositionItemService;
    @Autowired
    PurchaseOrderDetailsService podService;

    @ModelAttribute
    public void prepareContext(final Model model) {
        // upload page
        model.addAttribute("productValues", productValues);
        model.addAttribute("orderTypeValues", orderTypeValues);

        // list page
        model.addAttribute("pos", List.of());
        model.addAttribute("orders", List.of());
    }

    @Data
    public static final class UploadDto {

        @NotNull
        private String product;

        private String orderType;

        private MultipartFile headerFile;

        private MultipartFile orderFile;
    }

    @GetMapping("/order/upload")
    public String uploadPage(@ModelAttribute("upload") final UploadDto uploadDto) {
        return "order/upload";
    }

    @PostMapping("/order/upload")
    public String uploadFile(@ModelAttribute("upload") @Valid final UploadDto uploadDto, 
            final BindingResult bindingResult, RedirectAttributes attributes) {

        if (!bindingResult.hasFieldErrors("product")) {
            if (CARELABEL.equals(uploadDto.getProduct()) && uploadDto.getOrderType() == null) {
                bindingResult.rejectValue("orderType", "upload.orderType.isnull");
            }
            // check if header file is empty
            if (HANGTAG.equals(uploadDto.getProduct()) && uploadDto.getHeaderFile().isEmpty()) {
                bindingResult.rejectValue("headerFile", "upload.headerFile.empty");
            }
        }
        if (uploadDto.getOrderFile().isEmpty()) {
            bindingResult.rejectValue("orderFile", "upload.orderFile.empty");
        }
        if (bindingResult.hasErrors()) {
            return "order/upload";
        }

        MultipartFile headerFile = uploadDto.getHeaderFile();
        if (headerFile != null && !headerFile.isEmpty()) {
            // normalize the file path and save in the local file system
            String fileName = StringUtils.cleanPath(headerFile.getOriginalFilename());
            try {
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(headerFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error("Failed to upload file", e);
                attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.headerFile.fail") + " " + e.getMessage() + '!');
                return "redirect:/order/upload";
            }

            // return success response
            attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("upload.headerFile.success") + " " + fileName + '!');
        }

        MultipartFile orderFile = uploadDto.getOrderFile();
        if (orderFile != null && !orderFile.isEmpty()) {
            // normalize the file path and save in the local file system
            String fileName = StringUtils.cleanPath(orderFile.getOriginalFilename());
            try {
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(orderFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error("Failed to upload file", e);
                attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.orderFile.fail") + " " + e.getMessage() + '!');
                return "redirect:/order/upload";
            }

            uploadOrders(fileName, uploadDto.orderType);

            // return success response
            attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("upload.orderFile.success") + " " + fileName + '!');
        }

        return "redirect:/order/list";
    }

    private int uploadOrders(String fileName, String orderType) {
        Charset cs = StandardCharsets.UTF_8;
        try (FileInputStream fis = new FileInputStream(UPLOAD_DIR + fileName)) {
            cs = ByteUtils.detectEncoding(fis);
        } catch (Exception e) {
            log.warn("Failed to detect charset", e);
        }
        try {
            
            HeaderColumnNameMappingStrategy<Order> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Order.class);
            List<Order> orders = new CsvToBeanBuilder<Order>(new FileReader(UPLOAD_DIR + fileName, cs))
                .withSeparator(';')
                .withIgnoreEmptyLine(true)
                .withIgnoreLeadingWhiteSpace(true)
                .withOrderedResults(true)
                .withFieldAsNull(CSVReaderNullFieldIndicator.NEITHER)
                .withType(Order.class)
                .withMappingStrategy(strategy)
                .build()
                .parse();

            Map<String, PurchaseOrderDetails> pods = new HashMap<>();
            for (Order order : orders) {
                order.setOrderType(orderType);
                order.setOrderStatus("New");
                Long oid = orderService.create(order);

                if (pods.get(order.getPoNumber()) == null) {
                    PurchaseOrderDetails pod = new PurchaseOrderDetails();

                    pod.setPoNumber(order.getPoNumber());
                    pod.setStatus(order.getOrderStatus());
                    pod.setType(order.getOrderType());
                    pod.setSeason(order.getSeason());
                    pod.setBrand(order.getBrand());
                    pod.setArticleNumber(order.getArticleNumber());
                    pod.setVendorId(order.getVendorId());
                    pod.setFactoryName1(order.getFactoryName1());

                    pods.put(order.getPoNumber(), pod);
                }
            }
            for (Entry<String, PurchaseOrderDetails> pod : pods.entrySet()) {
                podService.create(pod.getValue());
            }
            return orders.size();
        } catch (Exception e) {
            log.error("Failed to parse orders CSV", e);
        }
        return -1;
    }

    @GetMapping("/order/list")
    public String list(final Model model) {
        model.addAttribute("pos", orderService.findAllDistinctPurchaseOrders());
        // model.addAttribute("orders", orderService.findAll());
        return "order/list";
    }

    @Autowired
    VendorService verndorService;
    
    @GetMapping("/order/manage/{poNumber}")
    public String manage(@PathVariable final String poNumber, final RedirectAttributes redirectAttributes, final Model model) {
        List<PurchaseOrder> pos = orderService.findDistinctPurchaseOrdersByPoNumber(poNumber);
        model.addAttribute("pos", pos);
        model.addAttribute("orders", orderService.findByPoNumber(poNumber));

        model.addAttribute("parts", partService.findAll());
        model.addAttribute("cis", compositionItemService.findAll());

        // TODO: Load from purchase order details
        List<String> cilist = Arrays.asList(
            "part:Lining",
            "ci:Coir:90%",
            "ci:Cashgora:10%",
            "part:Hoodlining",
            "ci:Badger:100%"
            );
        model.addAttribute("cilist", cilist);

        Optional<Vendor> v = verndorService.findByVendorCode(pos.get(0).getVendorId());
        if (v.isPresent()) {
            model.addAttribute("vaddrs", v.get().getAddresses());
        }

        return "order/manage";
    }

    @PostMapping("/order/manage/{poNumber}/layoutfile")
    public String layoutfile(@PathVariable() final String poNumber, @RequestPart() MultipartFile layoutfile, 
            final RedirectAttributes attributes, final Model model) {

        if (layoutfile.isEmpty()) {
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("manage.layoutfile.fail") + '!');
            return "redirect:/order/manage/"+poNumber;
        }

        // normalize the file path and save in the local file system
        String fileName = StringUtils.cleanPath(layoutfile.getOriginalFilename());
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(layoutfile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("manage.layoutfile.fail") + " " + e.getMessage() + '!');
            return "redirect:/order/upload";
        }

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setLayoutFile(fileName);
        pod.setLayoutDate(OffsetDateTime.now());
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.layoutfile.success") + " " + fileName + '!');

        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/status")
    public String status(@PathVariable() final String poNumber, @RequestParam(required = false) String status, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setStatus(status);
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.status.success") + " " + status + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/composition")
    public String composition(@PathVariable() final String poNumber, @RequestParam() List<String> cilist, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setCompositions(cilist);
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.composition.success") + " " + cilist + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/approve")
    public String approve(@PathVariable() final String poNumber,
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setApprovalDate(OffsetDateTime.now());
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.approve.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/reject")
    public String reject(@PathVariable() final String poNumber, @RequestParam(required = false) String reason, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setRejectReason(reason);
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.reject.success") + " " + reason + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/quantities")
    public String quantities(@PathVariable() final String poNumber, @RequestParam() List<String> quantity, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.quantities.success") + " " + quantity + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/deladdr")
    public String deladdr(@PathVariable() final String poNumber, @RequestParam(required = false) String name, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.deladdr.success") + " " + name + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/confirm")
    public String confirm(@PathVariable() final String poNumber, @RequestParam() Map<String,String> params, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setOrderBy(params.get("orderby"));
        pod.setVendorPo(params.get("vendorpo"));
        pod.setPrinterNotes(params.get("notes"));
        pod.setStatus("Confirmed");
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.confirm.success") + " " + params + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

}