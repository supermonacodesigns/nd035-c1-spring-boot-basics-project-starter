package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private CredentialsService credentialsService;
    private FileService fileService;

    public HomeController(NoteService noteService, CredentialsService credentialsService, FileService fileService) {
        this.noteService = noteService;
        this.credentialsService = credentialsService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String getHomePage(Authentication auth, Model model) {
        List<Note> notes = noteService.getNoteListByUsername(auth.getName());
        model.addAttribute("note",notes);

        List<Credentials> credentials = credentialsService.getCredentialsByUsername(auth.getName());
        model.addAttribute("credentials",credentials);

        List<File> files = fileService.getFilesByUsername(auth.getName());
        model.addAttribute("files",files);

        return "home";
    }

}

