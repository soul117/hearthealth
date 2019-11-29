package ua.com.crosp.solutions.hearthealthmonitor.common.exception.infrastructure;

import java.io.IOException;

/**
 * Created by Alexander Molochko on 11/5/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BaseIOException extends IOException {
    public BaseIOException(String message) {
        super(message);
    }
}
