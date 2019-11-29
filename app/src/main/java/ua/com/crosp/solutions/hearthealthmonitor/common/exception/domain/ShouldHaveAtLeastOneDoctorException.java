package ua.com.crosp.solutions.hearthealthmonitor.common.exception.domain;

/**
 * Created by Alexander Molochko on 10/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ShouldHaveAtLeastOneDoctorException extends BusinessException {
    public ShouldHaveAtLeastOneDoctorException(String message) {
        super(message);
    }
}
