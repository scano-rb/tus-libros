package tus_libros;

import java.util.function.BiConsumer;

public class MerchantProcessorStub implements MerchantProcessor {
    private final BiConsumer debitBehavior;

    MerchantProcessorStub(BiConsumer debitBehavior) {
        this.debitBehavior = debitBehavior;
    }

    @Override
    public void debit(Double anAmount, CreditCard aCard) {
        debitBehavior.accept(anAmount, aCard);
    }
}
