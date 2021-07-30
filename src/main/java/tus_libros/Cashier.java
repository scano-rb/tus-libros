package tus_libros;

import java.time.LocalDate;
import java.util.List;

public class Cashier {
    public static final String CANNOT_CHECKOUT_EMPTY_CART = "Cannot checkout an empty cart";
    public static final String CANNOT_CHECKOUT_WITH_EXPIRED_CREDIT_CARD = "Cannot checkout with expired credit card";
    public static final String CAN_CHECKOUT_CART_ONLY_ONCE = "Can checkout cart only once";

    private final Cart cart;
    private final CreditCard card;
    private final MerchantProcessor merchantProcessor;
    private final Double total;
    private final LocalDate today;
    private Boolean hasCheckedOut = false;


    public Cashier(Cart cart, CreditCard creditCard, LocalDate aDate, MerchantProcessor merchantProcessor) {
        assertCartNotEmpty(cart);
        assertCardNotExpired(creditCard, aDate);

        this.cart = cart;
        card = creditCard;
        today = aDate;
        this.merchantProcessor = merchantProcessor;
        total = cart.total();

    }

    public Object checkout() {
        assertCartHasNotCheckedout();

        try {
            merchantProcessor.debit(total, card);
            return total;
        } finally {
            hasCheckedOut = true;
        }
    }

    private void assertCardNotExpired(CreditCard creditCard, LocalDate aDate) {
        if(creditCard.isExpiredOn(aDate)) {
            throw new RuntimeException(CANNOT_CHECKOUT_WITH_EXPIRED_CREDIT_CARD);
        }
    }

    private void assertCartNotEmpty(Cart cart) {
        if(cart.isEmpty()) {
            throw new RuntimeException(CANNOT_CHECKOUT_EMPTY_CART);
        }
    }

    private void assertCartHasNotCheckedout() {
        if(hasCheckedOut) {
            throw new RuntimeException((CAN_CHECKOUT_CART_ONLY_ONCE));
        }
    }
}
