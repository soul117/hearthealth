package ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface BaseRouter {
    void navigateToInitialScreen();

    void navigateBack();

    void navigateBackForce();
}
