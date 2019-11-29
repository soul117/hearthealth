package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */


public class ValueUtils {
    public static final int DEFAULT_INT_VALUE = 0;
    public static final long DEFAULT_LONG_VALUE = 0L;

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static int getValueOrDefaultPositiveInteger(int value, int defaultValue) {
        return value > DEFAULT_INT_VALUE ? value : defaultValue;
    }

    public static long getValueOrDefaultPositiveLong(long value, long defaultValue) {
        return value > DEFAULT_LONG_VALUE ? value : defaultValue;
    }
}