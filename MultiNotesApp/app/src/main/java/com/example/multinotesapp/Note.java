package com.example.multinotesapp;

public class Note {
    private String lastSaveDate;
    private String noteTitle;
    private String noteText;
    private String trimText;

    public Note(){
        lastSaveDate = "";
        noteTitle = "";
        noteText = "";
        if(noteText.length() > 80){
            trimText = noteText.substring(0, 79) + "...";
        }
        else{
            trimText = noteText;
        }
    }

    public Note(String t, String n, String l){
        lastSaveDate = l;
        noteTitle = t;
        noteText = n;
        if(n.length() > 80){
            trimText = noteText.substring(0, 79) + "...";
        }
        else{
            trimText = noteText;
        }
    }

    public String getLastSaveDate() {
        return lastSaveDate;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setLastSaveDate(String lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }

    public void setNoteTitle(String noteTitle){
        this.noteTitle = noteTitle;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
        if(noteText.length() > 80){
            trimText = noteText.substring(0, 79) + "...";
        }
        else{
            trimText = noteText;
        }
    }

    //We don't really need to mutate trimText i.e. setTrimText() method not needed
    // The other methods account for trimText being 80 chars or more
    public String getTrimText(){
        return this.trimText;
    }
}
