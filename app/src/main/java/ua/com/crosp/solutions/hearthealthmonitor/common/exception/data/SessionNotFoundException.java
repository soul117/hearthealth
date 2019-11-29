package ua.com.crosp.solutions.hearthealthmonitor.common.exception.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SessionNotFoundException extends DataException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
