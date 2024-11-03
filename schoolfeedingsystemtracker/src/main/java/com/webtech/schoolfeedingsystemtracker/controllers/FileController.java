package com.webtech.schoolfeedingsystemtracker.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    // Define the directory where files will be stored
    private final String UPLOAD_DIR = "uploads/";

    // Endpoint to display the upload form
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        // Add the list of files to the model
        model.addAttribute("files", listUploadedFiles());
        return "fileUpload"; // This refers to a Thymeleaf template "uploadForm.html"
    }

    // Updated method to handle file upload
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/files/upload";
        }

        try {
            // Ensure the directory exists by creating it if it doesn't
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Save the file in the defined upload directory
            Path copyLocation = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            redirectAttributes.addFlashAttribute("message", "File uploaded successfully: " + file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload the file: " + e.getMessage());
        }

        return "redirect:/files/upload";
    }

    // Endpoint to download the uploaded file
    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Path filePath = Paths.get(UPLOAD_DIR + filename);
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint to delete the uploaded file
    @PostMapping("/delete/{filename:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        Path filePath = Paths.get(UPLOAD_DIR + filename);
        try {
            Files.deleteIfExists(filePath);
            return ResponseEntity.ok("File deleted successfully: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to delete the file: " + e.getMessage());
        }
    }

    // Method to list uploaded files
    private List<String> listUploadedFiles() {
        List<String> files = new ArrayList<>();
        File folder = new File(UPLOAD_DIR);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        }
        return files;
    }
}
