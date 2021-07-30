package tus_libros;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ManualClock implements RegularClock {

    private LocalDateTime date;

    public ManualClock(LocalDateTime aDate) {
        this.date = aDate;
    }

    @Override
    public LocalDateTime now() {
        return date;
    }


    public void advance(int amount, ChronoUnit unit) {
        date = date.plus(amount, unit);
    }
}
