package com.example.notesapplication.model;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String text;

    public Note(int id, String text){
        this.id = id;
        this.text = text;
    }

    public int getId(){ return id; }

    public String getText(){ return text; }
}
