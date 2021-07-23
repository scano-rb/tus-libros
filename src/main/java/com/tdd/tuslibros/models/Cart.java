package com.tdd.tuslibros.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private int id;
    private List<Book> books = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Boolean containsBook(ArrayList<Book> anotherListOfBooks) {
        return anotherListOfBooks.stream().allMatch(book -> this.books.contains(book));
    }
}