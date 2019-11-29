package ua.com.crosp.solutions.hearthealthmonitor.common.exception.presentation;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
