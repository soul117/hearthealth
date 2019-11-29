package ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject;

import java.util.Date;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;

import static ua.com.crosp.solutions.hearthealthmonitor.configuration.DateTimeConfiguration.DEFAULT_DATE_FORMAT;

/**
 * Created by Alexander Molochko on 10/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DateOfBirth {

    private Date mDate;

    public DateOfBirth() {
        mDate = new Date();
    }

    public DateOfBirth(Date date) {
        mDate = date;
    }

    public Date getDate() {
        if (null == mDate) {
            mDate = new Date();
        }
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return DateUtils.formatDate(mDate, DEFAULT_DATE_FORMAT);
    }

    public Integer getAge() {
        return DateUtils.calculateAge(mDate);
    }
}
