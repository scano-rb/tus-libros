package tus_libros;

/*
 * Developed by 10Pines SRL
 * License:
 * This work is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View,
 * California, 94041, USA.
 *
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tus_libros.Cart.PRODUCT_MUST_BE_SOLD_BY_PUBLISHER;
import static tus_libros.Cart.QUANTITY_MUST_BE_STRICTLY_POSITIVE;

class ShoppingTest {
	private TusLibrosTestObjects factory = new TusLibrosTestObjects();
	private Cart newCart;
	
	@BeforeEach
	void setUp() {
		newCart = factory.emptyCart();
	}

	@Test
	 void mustBeginWithEmptynewCart() {
		assertTrue(newCart.isEmpty());
	}
	
	@Test
	void cartContainsAddedProduct() {
		newCart.add(factory.validProduct());
		
		assertFalse(newCart.isEmpty());
		assertTrue(newCart.contains(factory.validProduct()));
	}
	
	@Test
	void canAddMoreThanOneItemOfSameProductsAtTheSameTime() {
		newCart.addWithQuantity(factory.validProduct(), 2);
		
		assertFalse(newCart.isEmpty());
		assertEquals(2, newCart.count(factory.validProduct()));
	}
	
	@Test
	void cartCanContainManyProducts() {
		newCart.addWithQuantity(factory.validProduct(), 2);
		newCart.addWithQuantity(factory.validProduct(), 3);
		
		assertFalse(newCart.isEmpty());
		assertEquals(5, newCart.count(factory.validProduct()));
	}
	
	@Test
	void canAddManyDifferentProductsAtTheSameTime() {
		newCart.addWithQuantity(factory.validProduct(), 2);
		newCart.addWithQuantity(factory.anotherValidProduct(), 3);
		
		assertFalse(newCart.isEmpty());
		assertEquals(2, newCart.count(factory.validProduct()));
		assertEquals(3, newCart.count(factory.anotherValidProduct()));
		assertEquals(5, newCart.productCount());
	}

	@Test
	void quantityToAddMustBeStrictlyPositive() {
		Cart cart = factory.emptyCart();

		Exception exception = assertThrows(RuntimeException.class, () -> cart.addWithQuantity(factory.validProduct(), 0),QUANTITY_MUST_BE_STRICTLY_POSITIVE );
		assertEquals(QUANTITY_MUST_BE_STRICTLY_POSITIVE, exception.getMessage());
	}

	@Test
	void productToAddMustBeSellByPublisher() {
		Cart cart = factory.emptyCart();

		Exception exception = assertThrows(RuntimeException.class, () -> cart.addWithQuantity(factory.invalidProduct(), 1),PRODUCT_MUST_BE_SOLD_BY_PUBLISHER );
		assertEquals(PRODUCT_MUST_BE_SOLD_BY_PUBLISHER, exception.getMessage());
	}
}
