package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NoteService bean");
    }



    // POST
    public Integer addNote(Note note) {
        return noteMapper.insertNote(new Note(null,note.getNoteTitle(),note.getNoteDescription(),note.getUserId()));
    }


    // GET
    public Note getNoteByNoteID(Integer noteId) {
        return noteMapper.getNoteByNoteID(noteId);
    }

    public List<Note> getNoteListByUsername(String userName) {
        return noteMapper.getAllNotesByUserID(userMapper.getUser(userName).getUserId());
    }


    // PUT
    public Integer updateNote(Note note) {
        return noteMapper.updateNote(note);
    }


    // DELETE
    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }
}
