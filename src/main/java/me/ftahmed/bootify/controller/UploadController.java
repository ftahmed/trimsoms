package me.ftahmed.bootify.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
import static me.ftahmed.bootify.config.Constants.*;
import me.ftahmed.bootify.util.WebUtils;

@Controller
@Slf4j
public class UploadController {

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("productValues", productValues);

        model.addAttribute("orderTypeValues", orderTypeValues);
    }

    @GetMapping("/upload")
    public String uploadPage(@ModelAttribute("upload") final UploadDto uploadDto) {
        return "upload/page";
    }

    @PostMapping("/upload")
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