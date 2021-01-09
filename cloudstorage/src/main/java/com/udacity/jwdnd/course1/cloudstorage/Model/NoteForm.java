package com.udacity.jwdnd.course1.cloudstorage.Model;

public class NoteForm
{
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userid;

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public Integer getUserid() {
        return userid;
    }
}
