package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common;

/**
 * Created by Alexander Molochko on 2/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ApplicationNavigator {
    Class getActivityModuleClassById(int id);

    int getInitialModuleId();

    void exitApplication();
}
