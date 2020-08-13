package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public String insertNote(Authentication auth, Note note, Model model) {

        String errorMessage;
        int rowAffected;

        // if note already exists, update the content
        if (note.getNoteId() != null) {
            rowAffected = noteService.updateNote(note);
        }

        // if note doesn't exist, add new note
        else {
            note.setUserId(userService.getUser(auth.getName()).getUserId());
            rowAffected = noteService.addNote(note);
        }

        if (rowAffected <= 0) {
            errorMessage = "An unexpected error occurred. Please try again later.";
            model.addAttribute("errorMessage", errorMessage);
            return "result";
        }

        return "result";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(Note note) {
        noteService.deleteNote(note.getNoteId());

        return "result";
    }

}

