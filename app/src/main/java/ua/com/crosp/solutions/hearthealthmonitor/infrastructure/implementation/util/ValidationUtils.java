package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ValidationUtils {

    private ValidationUtils() {
        throw new AssertionError();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    // validate first name
    public static boolean isValidFirstName(String firstName) {
        return firstName.matches(RegexPatterns.FIST_NAME);
    } // end method isValidFirstName

    // validate last name
    public static boolean isValidLastName(String lastName) {
        return lastName.matches(RegexPatterns.LAST_NAME);
    } // end method isValidLastName

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber);
    }

    public static boolean isEmpty(String text) {
        return null == text || text.isEmpty();
    }
}