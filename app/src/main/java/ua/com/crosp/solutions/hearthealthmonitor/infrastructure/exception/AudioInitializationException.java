package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.exception;

import ua.com.crosp.solutions.hearthealthmonitor.common.exception.infrastructure.BaseIOException;

/**
 * Created by Alexander Molochko on 11/5/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AudioInitializationException extends BaseIOException {

    public AudioInitializationException(String message) {
        super(message);
    }
}
