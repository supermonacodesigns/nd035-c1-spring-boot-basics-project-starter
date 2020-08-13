package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId) {
        fileService.deleteFile(fileId);

        return "result";
    }

    @PostMapping("/file")
    public String addFile(Authentication auth, @RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {

        String errorMessage;
        int rowAffected;

        try {
            if (file.isEmpty()) {
                errorMessage = "File doesn't exist.  Please try again.";
                model.addAttribute("errorMessage",errorMessage);
                return "result";
            }
            else if (fileService.isDuplicate(file.getOriginalFilename())) {
                errorMessage = "Filename already exists.  Please try again with a different file or filename.";
                model.addAttribute("errorMessage",errorMessage);
                return "result";
            }
            else {
                rowAffected = fileService.addFile(file, userService.getUser(auth.getName()).getUserId());

                if (rowAffected <= 0) {
                    errorMessage = "There was an error in uploading the file.  Please try again later.";
                    model.addAttribute("errorMessage",errorMessage);
                    return "result";
                }
            }

            return "result";
        }
        catch (Exception ex) {
            errorMessage = "An error occurred while processing the request.  Please try again.";
            model.addAttribute("errorMessage",errorMessage);
            return "result";
        }


    }

}
