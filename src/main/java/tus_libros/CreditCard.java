package tus_libros;

import java.time.LocalDate;
import java.time.YearMonth;

public class CreditCard {
    private final YearMonth expiration_date;

    public CreditCard(YearMonth aYearMonth) {
        expiration_date = aYearMonth;
    }

    public Boolean isExpiredOn(LocalDate aDate) {
        return expiration_date.isBefore(YearMonth.from(aDate));
    }
}
