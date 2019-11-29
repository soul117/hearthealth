package ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface BaseMenuRouter {
    void navigateToInitialModule();

    void setCurrentMenuItem(int menuItemId);

    void navigateTo(int menuItemId);
}
