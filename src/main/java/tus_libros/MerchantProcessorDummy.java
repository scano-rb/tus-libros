package tus_libros;

public class MerchantProcessorDummy implements MerchantProcessor {
    @Override
    public void debit(Double anAmount, CreditCard aCard) {
        //do nothing
    }
}
