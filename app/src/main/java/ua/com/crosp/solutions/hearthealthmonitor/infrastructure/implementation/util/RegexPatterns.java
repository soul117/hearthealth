package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RegexPatterns {

    private RegexPatterns() {
        throw new AssertionError();
    }

    public static final String LAST_NAME = "[a-zA-zА-Яа-я]+([ '-][a-zA-ZА-Яа-я]+)*";
    public static final String FIST_NAME = "[A-ZА-Я][a-zA-ZА-Яа-я]*";
    public static final String EMAIL = android.util.Patterns.EMAIL_ADDRESS.pattern();
//    public static final String PHONE_NUMBER = RegexTemplate.TELEPHONE;
//    public static final String NOT_EMPTY = RegexTemplate.NOT_EMPTY;
}