package tus_libros;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TusLibrosApplication {

    public static final String INVALID_PASSWORD_MESSAGE = "Invalid password";
    public static final String INVALID_CLIENT_ID_MESSAGE ="Invalid clientId";
    public static final String INVALID_CART_ID_MESSAGE = "Invalid cartId";
    public static final String INVALID_ISBN_MESSAGE = "Invalid ISBN";
    public static final String INVALID_BOOK_QUANTITY_MESSAGE = "Invalid book quantity";
    public static final String SESSION_TIMED_OUT = "Session timeout";

    private static final String ADD_TO_CART_SUCCESS_MESSAGE = "Ok";
    private final RegularClock clock;

    private Map<Integer, String> users = new HashMap<>();
    private Map<String, Double> catalog = new HashMap<>();
    private Map<UUID, Cart> carts = new HashMap<UUID, Cart>();

    // TODO add constructor with users map

    public TusLibrosApplication(RegularClock clock, Map<Integer, String> users, Map<String, Double> catalog) {
        this.users = users;
        this.catalog = catalog;
        this.clock = clock;
    }

    // Main API methods
    public UUID createCart(Integer clientId, String password) {

        validateClientId(clientId);

        validatePassword(clientId, password);

        UUID newCartId = UUID.randomUUID();

        carts.put(newCartId, new Cart(catalog));

        return newCartId;
    }

    public void addToCart(UUID cartId, String bookIsbn, Integer bookQuantity) {

        validateCartId(cartId);

        validateIsbn(bookIsbn);

        validateBookQuantity(bookQuantity);

        Cart userCart = carts.get(cartId);
        userCart.addWithQuantity(bookIsbn, bookQuantity);
    }

    private void validateBookQuantity(Integer bookQuantity) {
        if (bookQuantity <= 0) {
            throw new RuntimeException(INVALID_BOOK_QUANTITY_MESSAGE);
        }
    }

    // helpers
    public void validateIsbn(String isbn) {
        if (!catalog.containsKey(isbn)) {
            throw new RuntimeException(INVALID_ISBN_MESSAGE);
        }
    }


    public void validateCartId(UUID cartId) {
        if (!carts.containsKey(cartId)) {
            throw new RuntimeException(INVALID_CART_ID_MESSAGE);
        }
    }

    public void addProductToCatalog(String product, Double prize) {
        catalog.put(product, prize);
    }

    private void validateClientId(Integer clientId) {
        if (!users.containsKey(clientId)) throw new RuntimeException(INVALID_CLIENT_ID_MESSAGE);
    }

    private void validatePassword(Integer clientId, String password) {
        String userPassword = users.get(clientId);
        if (!userPassword.equals(password)) {
            throw new RuntimeException(INVALID_PASSWORD_MESSAGE);
        };
    }

    public Cart listCart(UUID cartId) {
        return carts.get(cartId);
    }
}
