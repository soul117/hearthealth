package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static ua.com.crosp.solutions.hearthealthmonitor.configuration.DateTimeConfiguration.DEFAULT_DATE_FORMAT;
import static ua.com.crosp.solutions.hearthealthmonitor.configuration.DateTimeConfiguration.DEFAULT_DATE_TIME_FORMAT;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DateUtils {
    private final static Date CURRENT_DATE = new Date();
    private static Date sInitialDate = new Date(0);

    private DateUtils() {
        throw new AssertionError();
    }

    public static String formatDate(Date dateToFormat) {
        return formatDate(dateToFormat, DEFAULT_DATE_FORMAT);
    }

    public static String formatDateTime(Date dateToFormat) {
        return formatDate(dateToFormat, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String formatCurrentDateTime(String format) {
        return formatDate(new Date(), format);
    }

    public static Date createDateFromValues(int year, int monthOfYear, int dayOfMonth) {
        return new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
    }

    public static int calculateAge(Date date) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public static String formatDate(Date date, String defaultDateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(defaultDateFormat);
        return format.format(date);
    }

    public static boolean dateInFuture(Date selectedBirthDate) {
        return selectedBirthDate.after(CURRENT_DATE);
    }

    public static Date getInitialDate() {
        return sInitialDate;
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
}