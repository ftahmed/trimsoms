package me.ftahmed.bootify.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import me.ftahmed.bootify.util.WebUtils;

@Controller
@Slf4j
public class UploadController {

    private final String UPLOAD_DIR = "./uploads/";

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload/page";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.file.empty"));
            return "redirect:/upload";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            attributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("upload.file.fail") + " " + e.getMessage() + '!');
            return "redirect:/upload";
        }

        // return success response
        attributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("upload.file.success") + " " + fileName + '!');

        return "redirect:/upload";
    }

}