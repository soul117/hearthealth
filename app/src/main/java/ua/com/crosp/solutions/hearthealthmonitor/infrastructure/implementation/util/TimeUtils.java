package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class TimeUtils {

    private TimeUtils() {
        throw new AssertionError();
    }


    public static Date createDateFromValues(int year, int monthOfYear, int dayOfMonth) {
        return new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
    }


    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long secondsToMillis(long gameEstimatedTime) {
        return TimeUnit.SECONDS.toMillis(gameEstimatedTime);
    }

    public static String formatTime(Date date, String timeFormat, String timeZone) {
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        Calendar c = Calendar.getInstance(tz);
        c.setTime(date);
        return String.format(timeFormat, String.format("%02d", c.get(Calendar.HOUR_OF_DAY)), String.format("%02d", c.get(Calendar.MINUTE)), String.format("%02d", c.get(Calendar.SECOND)));
    }
}