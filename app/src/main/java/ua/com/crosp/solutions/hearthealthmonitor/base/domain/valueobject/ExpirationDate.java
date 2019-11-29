package ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject;

import java.util.Date;

/**
 * Created by Alexander Molochko on 10/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExpirationDate {
    public static final ExpirationDate NO_EXPIRATION_DATE = new ExpirationDate(new Date(0));
    private Date mDate;

    public ExpirationDate(Date date) {
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
