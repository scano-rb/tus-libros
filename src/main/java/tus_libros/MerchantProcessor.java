package tus_libros;

public interface MerchantProcessor {
    public static final String CANNOT_CHECKOUT_WITH_STOLEN_CREDIT_CARD = "Stolen credit card";
    public static final String CANNOT_CHECKOUT_WHEN_CREDIT_CARD_HAS_NO_CREDIT = "Credit card has no credit";

    void debit(Double anAmount, CreditCard aCard);
}
