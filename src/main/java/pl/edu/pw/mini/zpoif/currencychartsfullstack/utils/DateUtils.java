package pl.edu.pw.mini.zpoif.currencychartsfullstack.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class DateUtils {

    private DateUtils() {}

    public static long DateDiffDays(Date d1, Date d2) {
        long diffInMsec = Math.abs(d1.getTime() - d2.getTime());
        return TimeUnit.DAYS.convert(diffInMsec, TimeUnit.MILLISECONDS);
    }

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
}
