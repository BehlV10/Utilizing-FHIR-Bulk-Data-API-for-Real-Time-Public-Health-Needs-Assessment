package edu.gatech.curator.provider;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateProvider {
    private static long DAY_IN_MS = 1000 * 60 * 60 * 24;
    private static long FIFTEEN_MIN_IN_MS = 1000 * 60 * 15;

    public Date oneWeekAgo() {
        return new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
    }

    public Date fifteenMinutesFromNow() {
        return new Date(System.currentTimeMillis() + FIFTEEN_MIN_IN_MS);
    }
}
