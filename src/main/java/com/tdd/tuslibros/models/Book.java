package com.tdd.tuslibros.models;

public class Book {

    private int id;
    private String name;
    private String editor;

    public Book(int id, String name, String editor){
        this.id = id;
        this.name = name;
        this.editor = editor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
