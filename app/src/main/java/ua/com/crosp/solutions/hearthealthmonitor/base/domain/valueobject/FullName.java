package ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FullName {
    public static final FullName ANONYMOUS = new FullName("Anonymous", "User");
    private String mFirstName;
    private String mLastName;
    private static final String FORMAT_USER_FULL_NAME = "%s %s";

    public FullName(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public FullName() {


    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    @Override
    public String toString() {
        return String.format(FORMAT_USER_FULL_NAME, mFirstName, mLastName);
    }
}
