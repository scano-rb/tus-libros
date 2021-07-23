package com.tdd.tuslibros.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CartTest {

    @Test
    void emptyCartCreation(){
        Cart cart  = createEmptyCart();
        assertEquals(new ArrayList<Book>(), cart.getBooks());
    }

    @Test
    void addingBookToCart(){
        Cart cart = createEmptyCart();
        Book book = new Book(1, "Harry Potter");
        cart.addBook(book);
        assertTrue(cart.contains(book));
    }

    @Test
    void addingMoreThanOneBooksToCart(){
        Book bookHPI = new Book(1, "Harry Potter I");
        Book bookHPII = new Book(1, "Harry Potter II");
        Cart cart = new Cart();
        ArrayList<Book> otherBooks = new ArrayList<>();
        otherBooks.add(bookHPI);
        otherBooks.add(bookHPII);
        cart.addBook(bookHPI);
        cart.addBook(bookHPII);
        assertTrue(cart.containsAll(otherBooks));
    }

    private Cart createEmptyCart() {
        return new Cart();
    }
}
