package com.tdd.tuslibros.factory;

import com.tdd.tuslibros.models.Cart;

public class FactoryTest {
    public Cart createEmptyCart() {
        return new Cart();
    }
}
