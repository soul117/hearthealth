package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class MathUtils {

    private MathUtils() {
        throw new AssertionError();
    }


    public static long getValueDeviationInPercentage(long value, long targetValue) {
        if (targetValue > 0) {
            return (long) (Math.abs((float) (targetValue - value) / (float) ((targetValue + value) / 2)) * 100);
        } else {
            return 0;
        }
    }

    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    public static long findNearestFactorValue(long number, long divisor) {
        long result = number / divisor;
        return result * divisor;
    }


    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    public static long gcd(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = gcd(result, input[i]);
        return result;
    }
}