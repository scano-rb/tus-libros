package com.tdd.tuslibros.models;

import com.tdd.tuslibros.factory.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//        1. Comienzo shopping con carrito vacío
//        2. Agrego un libro y el carrito lo contiene
//        3. Agrego 2 o más libros diferentes y los contiene
//        4. Agrego más de 1 ejemplar al mismo y los contiene
//        5. Agrego un libro más de una vez y los contiene
//        6. No puedo agregar libros que no pertenecen a la editorial
//        7. Sólo puedo agregar cantidades estrictamente positivas de libros
public class CartTest {

    private FactoryTest factory = new FactoryTest();
    @Test
    void emptyCartCreation(){
        Cart cart  = factory.createEmptyCart();
        assertEquals(new ArrayList<Book>(), cart.getBooks());
    }

    @Test
    void addingBookToCart(){
        Cart cart = factory.createEmptyCart();
        cart.addBook(Catalog.HPI);
        assertTrue(cart.contains(Catalog.HPI));
    }

    @Test
    void addingMoreThanOneBooksToCart(){
        Cart cart = factory.createEmptyCart();
        ArrayList<Book> otherBooks = new ArrayList<>();
        otherBooks.add(Catalog.HPI);
        otherBooks.add(Catalog.HPII);
        cart.addBook(Catalog.HPI);
        cart.addBook(Catalog.HPII);
        assertTrue(cart.containsAll(otherBooks));
    }

    @Test
    void addingSameBookMoreThanOneToCart() {
        Cart cart = factory.createEmptyCart();
        cart.addBook(Catalog.HPI);
        cart.addBook(Catalog.HPI);

        assertTrue(cart.containsSameBooksQuantity(Catalog.HPI) >= 2);
    }

    @Test
    void onlySameEditor() {
        Cart cart = factory.createEmptyCart();
        cart.addBook(Catalog.HPI);
        cart.addBook(Catalog.HPIII);

        assertEquals();
    }
}
