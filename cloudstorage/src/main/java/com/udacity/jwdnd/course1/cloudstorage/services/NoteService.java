package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService
{
    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService)
    {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int addNote(String username, NoteForm noteForm)
    {
        Integer userid = userService.getUserid(username);
        return noteMapper.insert( new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), userid));
    }

    public List<Note> getNotes(String username)
    {
        Integer userid = userService.getUserid(username);
        return noteMapper.retrieve(userid);
    }

    public int removeNote(Integer noteId)
    {
        return noteMapper.deleteNote(noteId);
    }

    public int changeNote(Integer noteId, String noteTitle, String noteDescription)
    {
        return noteMapper.update(noteId, noteTitle, noteDescription);
    }
}
