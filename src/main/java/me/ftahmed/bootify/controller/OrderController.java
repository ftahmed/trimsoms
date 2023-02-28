package me.ftahmed.bootify.controller;

import static me.ftahmed.bootify.config.Constants.CARELABEL;
import static me.ftahmed.bootify.config.Constants.HANGTAG;
import static me.ftahmed.bootify.config.Constants.UPLOAD_DIR;
import static me.ftahmed.bootify.config.Constants.orderTypeValues;
import static me.ftahmed.bootify.config.Constants.productValues;

import java.io.File;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.ftahmed.bootify.config.Constants;
import me.ftahmed.bootify.domain.Address;
import me.ftahmed.bootify.domain.Order;
import me.ftahmed.bootify.domain.PurchaseOrderDetails;
import me.ftahmed.bootify.domain.User;
import me.ftahmed.bootify.domain.Vendor;
import me.ftahmed.bootify.repos.UserRepository;
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
        model.addAttribute("pods", List.of());

        // manage page
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

    @GetMapping("/order/upload/{product}")
    public String uploadPage(@PathVariable final String product, final Model model) {
        model.addAttribute("product", product);
        return "order/upload";
    }

    @PostMapping("/order/upload/{product}")
    public String uploadFile(@PathVariable final String product, final MultipartFile orderFile, 
            final Model model, RedirectAttributes attributes) {

        if (orderFile.isEmpty()) {
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.file.empty"));
            return "redirect:/order/upload";
        }
        String originalFilename = StringUtils.cleanPath(orderFile.getOriginalFilename());
        // already uploaded?
        if (podService.orderOriginalFileExists(originalFilename)) {
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.file.exists"));
            return "redirect:/order/upload";
        }
    
        // save in the local file system with a unique name
        try {
            Path path = Files.createTempFile(Paths.get(UPLOAD_DIR), "ORDER-", ".CSV");
            Files.copy(orderFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            if ("carelabel".equals(product)) {
                uploadCarelabelOrders(path, originalFilename);
            } else if ("hangtag".equals(product)) {
                uploadHangtagOrders(path, originalFilename);
            } else {
                attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.orderFile.fail") + ": Unknown product!");
                return "redirect:/order/upload";
            }
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.orderFile.fail") + " " + e.getMessage() + '!');
            return "redirect:/order/upload";
        }

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("upload.orderFile.success") + " " + originalFilename + '!');

        return "redirect:/order/list";
    }

    private int uploadCarelabelOrders(final Path path, final String originalFilename) {
        // String fileName = path.getFileName().toString();
        Charset cs = StandardCharsets.UTF_8;
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            cs = ByteUtils.detectEncoding(fis);
        } catch (Exception e) {
            log.warn("Failed to detect charset", e);
        }
        try {
            
            HeaderColumnNameMappingStrategy<Order> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Order.class);
            List<Order> orders = new CsvToBeanBuilder<Order>(new FileReader(path.toFile(), cs))
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
                order.setOrderType("BULK");
                order.setOrderStatus("New");
                Long oid = orderService.create(order);

                PurchaseOrderDetails pod = pods.get(order.getPoNumber());
                if (pod == null) {
                    pod = new PurchaseOrderDetails();
                    pod.setTotalQty(0L);

                    pod.setPoNumber(order.getPoNumber());
                    pod.setStatus(order.getOrderStatus());
                    pod.setType(order.getOrderType());
                    pod.setSeason(order.getSeason());
                    pod.setBrand(order.getBrand());
                    pod.setArticleNumber(order.getArticleNumber());
                    pod.setVendorCode(order.getVendorId());
                    pod.setFactoryName1(order.getFactoryName1());

                    pod.setOrderFile(path.toFile().getName());
                    pod.setOrderOriginalFile(originalFilename);

                    pods.put(order.getPoNumber(), pod);
                }
                pod.setTotalQty(pod.getTotalQty() + order.getQuantity());
                pod.setTotalQtyOrig(pod.getTotalQty());
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

    private int uploadHangtagOrders(final Path path, final String originalFilename) {
        // TODO upload hangtag orders
        return -1;
    }

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/order/list")
    public String list(final Model model) {
        // TODO: Add filters
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUsername = authentication.getName();
        User user = userRepository.findByUsername(loginUsername).get();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VEN"))) {
            model.addAttribute("pods", podService.findByVendorCode(user.getVendor().getVendorCode()));
        } else {
            model.addAttribute("pods", podService.findAll());
        }
        
        return "order/list";
    }

    @Autowired
    VendorService verndorService;
    
    @GetMapping("/order/manage/{poNumber}")
    public String manage(@PathVariable final String poNumber, final RedirectAttributes redirectAttributes, final Model model) {
        model.addAttribute("orderStatusValues", Constants.orderStatusValues);

        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        model.addAttribute("pods", List.of(pod));
        model.addAttribute("cilist", pod.getCompositions());
        model.addAttribute("orders", orderService.findByPoNumber(poNumber));

        model.addAttribute("parts", partService.findAll());
        model.addAttribute("cis", compositionItemService.findAll());

        model.addAttribute("deladdr", new Address());
        model.addAttribute("invaddr", new Address());

        Optional<Vendor> v = verndorService.findByVendorCode(pod.getVendorCode());
        if (v.isPresent()) {
            model.addAttribute("vaddrs", v.get().getAddresses());
            for (Address addr: v.get().getAddresses()) {
                if (addr.getName().equals(pod.getDeliveryAddress())) {
                    model.addAttribute("deladdr", addr);
                }
            }
            model.addAttribute("invaddr", v.get().getInvoiceAddress());
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

        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        
        // normalize the file path and save in the local file system
        String fileName = StringUtils.cleanPath(layoutfile.getOriginalFilename());
        pod.setLayoutOriginalFile(fileName);

        try {
            Path path = Files.createTempFile(Paths.get(UPLOAD_DIR), "LAYOUT-" + poNumber + "-", ".PDF");
            // Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(layoutfile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            pod.setLayoutFile(path.getFileName().toString());
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("manage.layoutfile.fail") + " " + e.getMessage() + '!');
            return "redirect:/order/upload";
        }

        // TODO: update order status
        if (pod.getLayoutDate() != null) {
            pod.setStatus("Layout resubmitted");
            pod.setRejectReason("");
        }
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
        if (status == null) {
            // return success response
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("manage.status.fail"));
            return "redirect:/order/manage/"+poNumber;
        }

        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setStatus(status);
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.status.success") + ": " + status + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/composition")
    public String composition(@PathVariable() final String poNumber, @RequestParam() List<String> cilist, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setCompositions(cilist);
        pod.setStatus("Composition updated");
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.composition.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @GetMapping("/order/manage/{poNumber}/viewlayout")
    @ResponseBody
    public ResponseEntity<InputStreamResource> viewlayout(@PathVariable() final String poNumber) {
        try {
            PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
            final HttpHeaders httpHeaders = new HttpHeaders();
            final File file = new File(UPLOAD_DIR + pod.getLayoutFile());
            final InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            httpHeaders.set(HttpHeaders.LAST_MODIFIED, String.valueOf(file.lastModified()));
            httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
            httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
            return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
        } catch (Exception e) {
            log.error("Unable to open layout file", e);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/order/manage/{poNumber}/approve")
    public String approve(@PathVariable() final String poNumber,
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setApprovalDate(OffsetDateTime.now());
        pod.setRejectReason("");
        pod.setStatus("Layout approved");
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
        pod.setStatus("Layout rejected");
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("manage.reject.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/quantities")
    public String quantities(@PathVariable() final String poNumber, 
            @RequestParam() List<String> oid, @RequestParam() List<String> oqty, @RequestParam() List<String> nqty, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        try {
            PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
            for (int i=0; i<oid.size(); i++) {
                Order o = orderService.findById(oid.get(i));
                long oq = Long.parseLong(oqty.get(i));
                long nq = Long.parseLong(nqty.get(i));
                o.setQuantity(nq);
                orderService.update(o);
                pod.setTotalQty(pod.getTotalQty() - oq + nq);
                podService.update(poNumber, pod);
            }
        } catch (Exception e) {
            log.error("Unable to update quantities", e);
        }

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.quantities.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/deladdr")
    public String deladdr(@PathVariable() final String poNumber, @RequestParam(required = false) String deladdr, 
            final RedirectAttributes attributes, final Model model) {

        // TODO: update order status
        PurchaseOrderDetails pod = podService.findByPoNumber(poNumber);
        pod.setDeliveryAddress(deladdr);
        podService.update(poNumber, pod);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.deladdr.success") + '!');
        
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
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.confirm.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

}