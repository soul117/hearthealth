package ua.com.crosp.solutions.hearthealthmonitor.common.exception.domain;

/**
 * Created by Alexander Molochko on 10/17/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AtLeastOneUserIsRequiredToBeSet extends BusinessException {
    public AtLeastOneUserIsRequiredToBeSet(String s) {
        super(s);
    }
}
