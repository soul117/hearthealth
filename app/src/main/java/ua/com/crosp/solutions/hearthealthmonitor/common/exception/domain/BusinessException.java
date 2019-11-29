package ua.com.crosp.solutions.hearthealthmonitor.common.exception.domain;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
