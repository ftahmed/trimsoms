package me.ftahmed.bootify.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.ftahmed.bootify.config.Constants;
import me.ftahmed.bootify.domain.Address;
import me.ftahmed.bootify.domain.HangtagOrder;
import me.ftahmed.bootify.domain.Order;
import me.ftahmed.bootify.domain.PurchaseOrder;
import me.ftahmed.bootify.domain.Ticket;
import me.ftahmed.bootify.domain.User;
import me.ftahmed.bootify.domain.Vendor;
import me.ftahmed.bootify.repos.UserRepository;
import me.ftahmed.bootify.service.CompositionItemService;
import me.ftahmed.bootify.service.HangtagOrderService;
import me.ftahmed.bootify.service.OrderService;
import me.ftahmed.bootify.service.PartService;
import me.ftahmed.bootify.service.PurchaseOrderService;
import me.ftahmed.bootify.service.TicketService;
import me.ftahmed.bootify.service.VendorService;
import me.ftahmed.bootify.util.ByteUtils;
import me.ftahmed.bootify.util.CsvNameAndPositionMappingStrategy;
import me.ftahmed.bootify.util.WebUtils;

@Controller
@Slf4j
public class OrderController {

    public static final String UPLOAD_DIR = "./uploads/";

	final int[] HT_COL_LENGHT = { 1, 5, 9, 2, 6, 3, 2, 6, 6, 13, 4, 4, 4, 3, 20, 20, 4, 3, 3, 3, 3, 3, 3, 3, 3, 1, 9, 15, 3, 12, 6, 3, 10, 10, 4, 20, 20, 20, 20, 3, 3, 1, 12, 20, 1, 10, 3, 6, 10, 3, 6, 10, 3, 6, 10, 3, 6, 10, 3, 6, 3, 3, 6, 3, 3, 6, 3, 3, 6, 3, 3, 6, 3, 3, 6, 3, 3, 5, 4, 30, 5 };

    @Autowired
    PurchaseOrderService poService;
    @Autowired 
    private OrderService clOrderService;
    @Autowired 
    private PartService partService;
    @Autowired 
    private CompositionItemService compositionItemService;
    @Autowired
    HangtagOrderService htOrderService;
    @Autowired
    VendorService vendorService;
    @Autowired
    TicketService ticketService;

    @ModelAttribute
    public void prepareContext(final Model model) {
        // list page
        model.addAttribute("pos", List.of());

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
            return "redirect:/order/upload/"+product;
        }
        String originalFilename = StringUtils.cleanPath(orderFile.getOriginalFilename());
        // already uploaded?
        if (poService.orderOriginalFileExists(originalFilename)) {
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.file.exists"));
            return "redirect:/order/upload/"+product;
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
                return "redirect:/order/upload/"+product;
            }
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.orderFile.fail") + " " + e.getMessage() + '!');
            return "redirect:/order/upload/"+product;
        }

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("upload.orderFile.success") + " " + originalFilename + '!');

        return "redirect:/order/list/"+product;
    }

    @Transactional
    private int uploadCarelabelOrders(final Path path, final String originalFilename) {
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

            for (Order order : orders) {
                order.setOrderType("BULK");
                order.setOrderStatus("New");
                clOrderService.create(order);

                PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", order.getPoNumber());
                if (po == null) {
                    po = new PurchaseOrder();
                    po.setProduct("carelabel");
                    po.setTotalQty(order.getQuantity());
                    po.setTotalQtyOrig(order.getQuantity());

                    po.setPoNumber(order.getPoNumber());
                    po.setStatus(order.getOrderStatus());
                    po.setType(order.getOrderType());
                    po.setSeason(order.getSeason());
                    po.setBrand(order.getBrand());
                    po.setArticleNumber(order.getArticleNumber());
                    po.setVendorCode(order.getVendorId());
                    po.setFactoryName1(order.getFactoryName1());

                    po.setOrderFile(path.toFile().getName());
                    po.setOrderOriginalFile(originalFilename);

                    poService.create(po);
                } else {
                    po.setTotalQty(po.getTotalQty() + order.getQuantity());
                    po.setTotalQtyOrig(po.getTotalQtyOrig() + order.getQuantity());
                    poService.update(order.getPoNumber(), po);
                }
            }
            return orders.size();
        } catch (Exception e) {
            log.error("Failed to parse orders CSV", e);
        }
        return -1;
    }

    @Transactional
    private int uploadHangtagOrders(final Path path, final String originalFilename) {
        // TODO upload hangtag orders
        Charset cs = StandardCharsets.UTF_8;
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            cs = ByteUtils.detectEncoding(fis);
        } catch (Exception e) {
            log.warn("Failed to detect charset", e);
        }
        int count = 0;
        String poNumber = "";
        try (BufferedReader reader = Files.newBufferedReader(path, cs)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() != 534) {
                    continue; 
                }
                count++;
                char[] row = line.toCharArray();
                List<String> cols = new ArrayList<>();
                for (int i=0, pos=0; i<HT_COL_LENGHT.length; pos += HT_COL_LENGHT[i], i++) {
                    cols.add(String.valueOf(row, pos, HT_COL_LENGHT[i]));
                }
                HangtagOrder order = new HangtagOrder();
                // order.get
                order.setRecord_id(cols.get(0));
                order.setLine_Quantity(cols.get(1));
                order.setTicket_Type(cols.get(2));
                order.setBrand(cols.get(3)); // Vp_Alpha
                order.setVendorId(cols.get(4)); // Prod_Betrieb
                order.setSeason(cols.get(5)); // Saison
                order.setExf_Termin(cols.get(6));
                order.setPoNumber(cols.get(7)); // Fa_Nr                 
                order.setKt_Woche(cols.get(8));
                order.setEan_13(cols.get(9));

                order.setModell(cols.get(10));
                order.setStoff(cols.get(11));
                order.setFarbe(cols.get(12));
                order.setSetnummerBlank(cols.get(13));
                order.setProgrammtext_1(cols.get(14));
                order.setProgrammtext_2(cols.get(15));
                order.setGrosse(cols.get(16));
                order.setInter_Bez_1(cols.get(17));
                order.setInter_Bez_2(cols.get(18));
                order.setInter_Bez_3(cols.get(19));

                order.setInter_Bez_4(cols.get(20));
                order.setInter_Gr_1(cols.get(21));
                order.setInter_Gr_2(cols.get(22));
                order.setInter_Gr_3(cols.get(23));
                order.setInter_Gr_4(cols.get(24));
                order.setDummy(cols.get(25));
                order.setEinzelteil_Identcode(cols.get(26));
                order.setReferenceorder(cols.get(27));
                order.setVp_Numeric(cols.get(28));
                order.setVariante(cols.get(29));

                order.setPreis(cols.get(30));
                order.setWahrung(cols.get(31));
                order.setLogo(cols.get(32));
                order.setKundennummer(cols.get(33));
                order.setKundenfilialnummer(cols.get(34));
                order.setStoffzusammensetzung_1(cols.get(35));
                order.setStoffzusammensetzung_2(cols.get(36));
                order.setStoffzusammensetzung_3(cols.get(37));
                order.setStoffzusammensetzung_4(cols.get(38));
                order.setSprache(cols.get(39));

                order.setFolienart(cols.get(40));
                order.setEtikettenart(cols.get(41));
                order.setSortnummer(cols.get(42));
                order.setLifestyle_Kollektion(cols.get(43));
                order.setKurz_Aus_Ort(cols.get(44));
                order.setCountry_1(cols.get(45));
                order.setCurrency_1(cols.get(46));
                order.setPrice_1(cols.get(47));
                order.setCountry_2(cols.get(48));
                order.setCurrency_2(cols.get(49));

                order.setPrice_2(cols.get(50));
                order.setCountry_3(cols.get(51));
                order.setCurrency_3(cols.get(52));
                order.setPrice_3(cols.get(53));
                order.setCountry_4(cols.get(54));
                order.setCurrency_4(cols.get(55));
                order.setPrice_4(cols.get(56));
                order.setCountry_5(cols.get(57));
                order.setCurrency_5(cols.get(58));
                order.setPrice_5(cols.get(59));

                order.setCountry_6(cols.get(60));
                order.setCurrency_6(cols.get(61));
                order.setPrice_6(cols.get(62));
                order.setCountry_7(cols.get(63));
                order.setCurrency_7(cols.get(64));
                order.setPrice_7(cols.get(65));
                order.setCountry_8(cols.get(66));
                order.setCurrency_8(cols.get(67));
                order.setPrice_8(cols.get(68));
                order.setCountry_9(cols.get(69));

                order.setCurrency_9(cols.get(70));
                order.setPrice_9(cols.get(71));
                order.setCountry_10(cols.get(72));
                order.setCurrency_10(cols.get(73));
                order.setPrice_10(cols.get(74));
                order.setInter_Bez_5(cols.get(75));
                order.setInter_Gr_5(cols.get(76));
                order.setEkpreis(cols.get(77));
                order.setPaArt(cols.get(78));
                order.setPaArtBez(cols.get(79));

                order.setSetnummer(cols.get(80));
                
                htOrderService.create(order);

                poNumber = order.getPoNumber();
                PurchaseOrder po = poService.findByProductAndPoNumber("hangtag", order.getPoNumber());
                if (po == null) {
                    po = new PurchaseOrder();
                    po.setProduct("hangtag");
                    po.setTotalQty(1L);
                    po.setTotalQtyOrig(1L);

                    po.setPoNumber(order.getPoNumber());
                    po.setStatus("New");
                    po.setType("");
                    po.setSeason(order.getSeason());
                    po.setBrand(order.getBrand());
                    po.setReferenceOrder(order.getReferenceorder());

                    po.setTicket(ticketService.findByCode(order.getTicket_Type().trim()).get());
                    po.setVendorCode(order.getVendorId());
                    po.setFactoryName1(vendorService.findByVendorCode(order.getVendorId()).get().getVendorName());

                    po.setOrderFile(path.toFile().getName());
                    po.setOrderOriginalFile(originalFilename);

                    poService.create(po);
                } else {
                    po.setTotalQty(po.getTotalQty() + 1L);
                    po.setTotalQtyOrig(po.getTotalQtyOrig() + 1L);
                    poService.update(order.getPoNumber(), po);
                }

            }

        } catch (Exception e) {
            log.error("Failed to parse hangtag orders data", e);
        }

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(UPLOAD_DIR + "hangtag-" + poNumber + ".csv"))) {
            List<HangtagOrder> orders = htOrderService.findByPoNumber(poNumber);

            CsvNameAndPositionMappingStrategy<HangtagOrder> mappingStrategy = new CsvNameAndPositionMappingStrategy<>();
            mappingStrategy.setType(HangtagOrder.class);
            StatefulBeanToCsv<HangtagOrder> bc = new StatefulBeanToCsvBuilder<HangtagOrder>(csvWriter)
                .withMappingStrategy(mappingStrategy)
                .withApplyQuotesToAll(false)
                .build();
            bc.write(orders);

        } catch (Exception e) {
            log.error("Failed to parse hangtag orders data", e);
        }
        
        return count;
    }

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/order/list")
    public String list() {
        return "redirect:/order/list/carelabel";
    }

    @GetMapping("/order/list/{product}")
    public String list(@PathVariable final String product, final Model model) {
        // TODO: Add filters
        
        model.addAttribute("product", product);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUsername = authentication.getName();
        User user = userRepository.findByUsername(loginUsername).orElse(new User());
        if (user.getVendor() != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VEN"))) {
            model.addAttribute("pos", poService.findByProductAndVendorCode(product, user.getVendor().getVendorCode()));
        } else {
            model.addAttribute("pos", poService.findByProduct(product));
        }
        
        return "order/list";
    }

    @GetMapping("/order/htcsv/{poNumber}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> htCsv(@PathVariable() final String poNumber) {
        try {
            final HttpHeaders httpHeaders = new HttpHeaders();
            final File file = new File(UPLOAD_DIR + "hangtag-" + poNumber + ".csv");
            final InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            httpHeaders.set(HttpHeaders.LAST_MODIFIED, String.valueOf(file.lastModified()));
            httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
            httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
            return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
        } catch (Exception e) {
            log.error("Unable to open layout file", e);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/order/manage/{poNumber}")
    public String manage(@PathVariable final String poNumber, final RedirectAttributes redirectAttributes, final Model model) {
        model.addAttribute("orderStatus", Constants.orderStatus);

        PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
        model.addAttribute("pos", List.of(po));
        model.addAttribute("cilist", po.getCompositions());
        model.addAttribute("orders", clOrderService.findByPoNumber(poNumber));

        model.addAttribute("parts", partService.findAll());
        model.addAttribute("cis", compositionItemService.findAll());

        model.addAttribute("deladdr", new Address());
        model.addAttribute("invaddr", new Address());

        Optional<Vendor> v = vendorService.findByVendorCode(po.getVendorCode());
        if (v.isPresent()) {
            model.addAttribute("vaddrs", v.get().getAddresses());
            for (Address addr: v.get().getAddresses()) {
                if (addr.getName().equals(po.getDeliveryAddress())) {
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

        PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
        
        // normalize the file path and save in the local file system
        String fileName = StringUtils.cleanPath(layoutfile.getOriginalFilename());
        po.setLayoutOriginalFile(fileName);

        try {
            Path path = Files.createTempFile(Paths.get(UPLOAD_DIR), "LAYOUT-" + poNumber + "-", ".PDF");
            Files.copy(layoutfile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            po.setLayoutFile(path.getFileName().toString());
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("manage.layoutfile.fail") + " " + e.getMessage() + '!');
            return "redirect:/order/upload";
        }

        po.setStatus("Layout submitted");
        po.setRejectReason("");
        po.setLayoutDate(OffsetDateTime.now());
        poService.update(poNumber, po);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.layoutfile.success") + " " + fileName + '!');

        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/status")
    public String status(@PathVariable() final String poNumber, 
            @RequestParam() final String product, @RequestParam(required = false) String status, 
            final RedirectAttributes attributes, final Model model) {

        if (status == null) {
            // return failure response
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("manage.status.fail"));
            return "redirect:/order/manage/"+poNumber;
        }

        PurchaseOrder po = poService.findByProductAndPoNumber(product, poNumber);
        po.setStatus(status);
        poService.update(poNumber, po);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.status.success") + ": " + status + '!');
        
        return "redirect:/order/list/"+po.getProduct();
    }

    @PostMapping("/order/manage/{poNumber}/composition")
    public String composition(@PathVariable() final String poNumber, @RequestParam() List<String> cilist, 
            final RedirectAttributes attributes, final Model model) {

        PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
        po.setCompositions(cilist);
        po.setStatus("Composition updated");
        poService.update(poNumber, po);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.composition.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @GetMapping("/order/manage/{poNumber}/viewlayout")
    @ResponseBody
    public ResponseEntity<InputStreamResource> viewlayout(@PathVariable() final String poNumber) {
        try {
            PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
            final HttpHeaders httpHeaders = new HttpHeaders();
            final File file = new File(UPLOAD_DIR + po.getLayoutFile());
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

        PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
        po.setApprovalDate(OffsetDateTime.now());
        po.setRejectReason("");
        po.setStatus("Layout approved");
        poService.update(poNumber, po);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.approve.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/reject")
    public String reject(@PathVariable() final String poNumber, @RequestParam(required = false) String reason, 
            final RedirectAttributes attributes, final Model model) {

        PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
        po.setRejectReason(reason);
        po.setStatus("Layout rejected");
        poService.update(poNumber, po);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("manage.reject.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/quantities")
    public String quantities(@PathVariable() final String poNumber, 
            @RequestParam() List<String> oid, @RequestParam() List<String> oqty, @RequestParam() List<String> nqty, 
            final RedirectAttributes attributes, final Model model) {

        try {
            PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
            for (int i=0; i<oid.size(); i++) {
                Order o = clOrderService.findById(oid.get(i));
                long oq = Long.parseLong(oqty.get(i));
                long nq = Long.parseLong(nqty.get(i));
                o.setQuantity(nq);
                clOrderService.update(o);
                po.setTotalQty(po.getTotalQty() - oq + nq);
                poService.update(poNumber, po);
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

        PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
        po.setDeliveryAddress(deladdr);
        poService.update(poNumber, po);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.deladdr.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

    @PostMapping("/order/manage/{poNumber}/confirm")
    public String confirm(@PathVariable() final String poNumber, @RequestParam() Map<String,String> params, 
            final RedirectAttributes attributes, final Model model) {

        PurchaseOrder po = poService.findByProductAndPoNumber("carelabel", poNumber);
        po.setOrderBy(params.get("orderby"));
        po.setVendorPo(params.get("vendorpo"));
        po.setPrinterNotes(params.get("notes"));
        po.setStatus("Confirmed");
        poService.update(poNumber, po);

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("manage.confirm.success") + '!');
        
        return "redirect:/order/manage/"+poNumber;
    }

}