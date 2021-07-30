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

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tus_libros.Cashier.*;
import static tus_libros.MerchantProcessor.CANNOT_CHECKOUT_WHEN_CREDIT_CARD_HAS_NO_CREDIT;
import static tus_libros.MerchantProcessor.CANNOT_CHECKOUT_WITH_STOLEN_CREDIT_CARD;

public class CheckOutTest {
    private TusLibrosTestObjects factory = new TusLibrosTestObjects();

    @Test
    void cannotCheckoutAnEmptyCart() {

        factory.assertThrowsRuntimeException(() -> new Cashier(factory.emptyCart(),
                        factory.expiredCreditCard(),
                        factory.now(),
                        factory.merchantProcessorDummy()),
                CANNOT_CHECKOUT_EMPTY_CART);
    }

    @Test
    void cannotCheckoutAnExpiredCreditCard() {
        var cart = factory.emptyCart();
        cart.add(factory.validProduct());

        factory.assertThrowsRuntimeException(() -> new Cashier(cart,
                        factory.expiredCreditCard(),
                        factory.now(),
                        factory.merchantProcessorDummy()),
                CANNOT_CHECKOUT_WITH_EXPIRED_CREDIT_CARD);
    }

    @Test
    void cashierCalculatesSalesTotalOfOneProductCorrectly() {

        var expectedTotal = 2 * factory.validProductPrice();
        var salesBook = factory.emptySalesBook();
        var cart = factory.emptyCart();
        cart.addWithQuantity(factory.validProduct(), 2);
        var cashier = new Cashier((cart),
                factory.notExpiredCreditCard(),
                factory.now(),
                factory.merchantProcessorDummy());

        var total = cashier.checkout();

        assertEquals(expectedTotal, total);
    }

    @Test
    void cashierCalculatesSalesTotalOfMoreThanOneProductCorrectly() {
        var expectedTotal = 2 * factory.validProductPrice() + 3 * factory.anotherValidProductPrice();
        var salesBook = factory.emptySalesBook();
        var cart = factory.emptyCart();
        cart.addWithQuantity(factory.validProduct(), 2);
        cart.addWithQuantity(factory.anotherValidProduct(), 3);
        var cashier = new Cashier((cart),
                factory.notExpiredCreditCard(),
                factory.now(),
                factory.merchantProcessorDummy());

        var total = cashier.checkout();

        assertEquals(expectedTotal, total);
    }

    @Test
    void canCheckOutOnlyOnce() {
        Cart cart = factory.emptyCart();
        cart.add(factory.validProduct());
        var cashier = new Cashier(cart,
                factory.notExpiredCreditCard(),
                factory.now(),
                factory.merchantProcessorDummy());

        cashier.checkout();

        factory.assertThrowsRuntimeException(() -> cashier.checkout(), CAN_CHECKOUT_CART_ONLY_ONCE);
    }

    @Test
    void cannotCheckOutWithAnStolenCreditCard() {
        var cart = factory.emptyCart();
        cart.add(factory.validProduct());
        var merchantProcessor = new MerchantProcessorStub((amount, card) -> {
            throw new RuntimeException(CANNOT_CHECKOUT_WITH_STOLEN_CREDIT_CARD);
        });
        var cashier = new Cashier(cart,
                factory.notExpiredCreditCard(),
                factory.now(), merchantProcessor);

        factory.assertThrowsRuntimeException(() -> cashier.checkout(),
                CANNOT_CHECKOUT_WITH_STOLEN_CREDIT_CARD);
    }

    @Test
    void cannotCheckOutWhenCardHasNoCredit() {
        var cart = factory.emptyCart();
        cart.add(factory.validProduct());
        var merchantProcessor = new MerchantProcessorStub((amount, card) -> {
            throw new RuntimeException(CANNOT_CHECKOUT_WHEN_CREDIT_CARD_HAS_NO_CREDIT);
        });
        var cashier = new Cashier(cart,
                factory.notExpiredCreditCard(),
                factory.now(), merchantProcessor);

        factory.assertThrowsRuntimeException(() -> cashier.checkout(),
                CANNOT_CHECKOUT_WHEN_CREDIT_CARD_HAS_NO_CREDIT);
    }

    @Test
    void successfulMerchantProcessorReceivesRightCardAndAmount() {
        var cart = factory.emptyCart();
        var aQuantity = 2;
        var aProduct = factory.validProduct();
        cart.addWithQuantity(aProduct, aQuantity);
        var expectedTotal = factory.validProductPrice() * aQuantity;
        var credit_card = factory.notExpiredCreditCard();
        //var salesBook = factory.emptySalesBook();
        var successfulMerchantProcessor = new MerchantProcessorStub((amount, card) ->
        {
            assertEquals(credit_card, card);
            assertEquals(expectedTotal, amount);
        });

        new Cashier(cart,
                credit_card,
                factory.now(),
                successfulMerchantProcessor).checkout();
    }
}
