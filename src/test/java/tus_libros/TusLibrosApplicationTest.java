package tus_libros;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static tus_libros.TusLibrosApplication.*;

public class TusLibrosApplicationTest {

    private TusLibrosTestObjects factory = new TusLibrosTestObjects();

    private ManualClock clock = factory.createClock();
    private TusLibrosApplication application = new TusLibrosApplication(clock, factory.createUsers(), factory.createDummyCatalog());

    @Test
    void checkoutWithInvalidId() {
        // TODO extract to factory
        Integer clientId = -1;
        String invalidPassword = "password1234";

        assertThrows(RuntimeException.class, () -> application.createCart(clientId, invalidPassword), INVALID_CLIENT_ID_MESSAGE);
    }

    @Test
    void checkoutWithInvalidPassword() {
        Integer clientId = factory.validClientId();
        String invalidPassword = factory.invalidPassword();

        assertThrows(RuntimeException.class, () -> application.createCart(clientId, invalidPassword), INVALID_PASSWORD_MESSAGE);
    }

    @Test
    void createEmptyCart() {
        Integer clientId = factory.validClientId();
        String password = factory.validPassword();

        UUID cartId = application.createCart(clientId, password);


        Cart cart = application.listCart(cartId);

        assertTrue(cart.isEmpty());
    }

    @Test
    void addToCartWithInvalidCartId() {
        UUID cartId = UUID.randomUUID();
        String bookIsbn =  "9780747532743";
        Integer bookQuantity = 2;

        assertThrows(RuntimeException.class, () -> application.addToCart(cartId, bookIsbn, bookQuantity), INVALID_CART_ID_MESSAGE);
    }

    @Test
    void addToCartWithInvalidIsbn() {
        application.addProductToCatalog(factory.validIsbn(), factory.validPrice());

        UUID cartId = application.createCart(factory.validClientId(), factory.validPassword());

        assertThrows(RuntimeException.class, () -> application.addToCart(cartId, factory.invalidIsbn(), 2), INVALID_ISBN_MESSAGE);
    }

    @Test
    void cannotListCartAfter30MinutesOfLastUse() {
        UUID cartId = application.createCart(factory.validClientId(), factory.validPassword());
        clock.advance(31, ChronoUnit.MINUTES);

        factory.assertThrowsRuntimeException(() -> application.listCart(cartId),
                TusLibrosApplication.SESSION_TIMED_OUT);
    }
}
