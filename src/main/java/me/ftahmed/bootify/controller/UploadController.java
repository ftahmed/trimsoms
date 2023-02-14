package me.ftahmed.bootify.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.ftahmed.bootify.util.WebUtils;

@Controller
@Slf4j
public class UploadController {

    // @Autowired
    // private Validator validator;

    private static final String UPLOAD_DIR = "./uploads/";

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("productValues", Map.of(
            "carelabel", "Care label", 
            "hangtag", "Hangtag"));

        model.addAttribute("orderTypeValues", Map.of(
            "SMS", "SMS",
            "BULK", "BULK"));
    }

    @GetMapping("/upload")
    public String uploadPage(@ModelAttribute("upload") final UploadDto uploadDto) {
        return "upload/page";
    }

    // public String uploadFile(@ModelAttribute("uploadDto") @Valid final Upload upload, 
    //         @RequestParam("headerFile") MultipartFile headerFile, @RequestParam("orderFile") MultipartFile orderFile,
    //         final BindingResult bindingResult, RedirectAttributes attributes) {
    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute("upload") @Valid final UploadDto uploadDto, 
            final BindingResult bindingResult, RedirectAttributes attributes) {

        // https://reflectoring.io/bean-validation-with-spring-boot/
        // https://stackoverflow.com/questions/9257673/conditional-validations-in-spring
        //
        // Set<ConstraintViolation<Upload>> violations = validator.validate(upload);
        // if (!violations.isEmpty()) {
        //     for (ConstraintViolation<?> violation : violations) {
        //         log.error(violation.getPropertyPath() + ": " + violation.getMessage());
        //     }
        //     throw new ConstraintViolationException(violations);
        // }

        if (!bindingResult.hasFieldErrors("product")) {
            if ("carelabel".equals(uploadDto.getProduct()) && uploadDto.getOrderType() == null) {
                bindingResult.rejectValue("orderType", "upload.orderType.isnull");
            }
            // check if header file is empty
            if ("hangtag".equals(uploadDto.getProduct()) && uploadDto.getHeaderFile().isEmpty()) {
                bindingResult.rejectValue("headerFile", "upload.headerFile.empty");
            }
        }
        if (uploadDto.getOrderFile().isEmpty()) {
            bindingResult.rejectValue("orderFile", "upload.orderFile.empty");
        }
        if (bindingResult.hasErrors()) {
            return "upload/page";
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
                return "redirect:/upload";
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
                return "redirect:/upload";
            }

            // return success response
            attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("upload.orderFile.success") + " " + fileName + '!');
        }

        return "redirect:/upload";
    }

    @Data
    public static final class UploadDto {

        @NotNull
        private String product;

        private String orderType;

        MultipartFile headerFile;

        MultipartFile orderFile;
    }

}