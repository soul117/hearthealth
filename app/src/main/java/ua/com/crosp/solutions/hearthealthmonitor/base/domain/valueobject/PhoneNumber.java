package ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PhoneNumber {
    public static final String DEFAULT_PHONE_NUMBER = "380000000000";
    private String mPhoneNumber;

    public PhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public PhoneNumber() {
    }

    public String getPhoneNumberString() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public static PhoneNumber createDefault(){
        return new PhoneNumber(DEFAULT_PHONE_NUMBER);
    }
}
