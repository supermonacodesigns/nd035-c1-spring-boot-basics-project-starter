package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {

    private CredentialsService credentialService;
    private UserService userService;

    public CredentialsController(CredentialsService credentialsService, UserService userService) {
        this.credentialService = credentialsService;
        this.userService = userService;
    }

    @PostMapping("/credentials")
    public String insertCredentials(Authentication auth, Credentials credentials, Model model) {
        String errorMessage;
        int rowAffected;

        if (credentials.getCredentialId() != null) {
            rowAffected = credentialService.updateCredentials(credentials);
        }
        else {
            credentials.setUserId(userService.getUser(auth.getName()).getUserId());
            rowAffected = credentialService.addCredentials(credentials);
        }

        if (rowAffected <= 0) {
            errorMessage = "An unexpected error occurred. Please try again later.";
            model.addAttribute("errorMessage", errorMessage);
            return "result";
        }

        return "result";
    }

    @GetMapping("/credentials/delete/{credentialId}")
    public String deleteCredentials(Credentials credentials) {
        credentialService.deleteCredentials(credentials.getCredentialId());

        return "result";
    }
}
