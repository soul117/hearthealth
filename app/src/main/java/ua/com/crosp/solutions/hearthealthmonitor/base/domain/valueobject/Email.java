package ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Email {
    public static final String DEFAULT_EMAIL = "example@crosp.net";
    private String mEmailString = DEFAULT_EMAIL;

    public Email() {
    }

    public Email(String emailString) {
        mEmailString = emailString;
    }

    public String getEmailString() {
        return mEmailString;
    }

    public void setEmailString(String emailString) {
        mEmailString = emailString;
    }

    @Override
    public String toString() {
        return getEmailString();
    }

    public static Email createDefault() {
        Email email = new Email();
        email.setEmailString(DEFAULT_EMAIL);
        return email;
    }
}
