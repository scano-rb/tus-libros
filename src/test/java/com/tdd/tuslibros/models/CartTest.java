package com.tdd.tuslibros.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CartTest {

    //1. Comienzo shopping con carrito vacío
    @Test
    void test001(){
        Cart cart  = new Cart();
        assertEquals(new ArrayList<Book>(), cart.getBooks());
    }

    //2. Agrego un libro y el carrito lo contiene
    @Test
    void test002(){
        Book book = new Book(1, "Harry Potter");
        Cart cart = new Cart();
        cart.addBook(book);
        ArrayList<Book> listofBooks = new ArrayList<>();
        listofBooks.add(book);
        assertTrue(cart.containsBook(listofBooks));
    }

    // Agrego 2 o más libros diferentes y los contiene
    @Test
    void test003(){
        Book bookHPI = new Book(1, "Harry Potter I");
        Book bookHPII = new Book(1, "Harry Potter II");
        Cart cart = new Cart();
        ArrayList<Book> listofBooks = new ArrayList<>();
        listofBooks.add(bookHPI);
        listofBooks.add(bookHPII);
        cart.addBook(bookHPI);
        cart.addBook(bookHPII);
        assertTrue(cart.containsBook(listofBooks));
    }
}
