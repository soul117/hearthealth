package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface RestModeRouterContract extends BaseRouter {
    public void startRestModeExperiment();

    public void switchToSettings();


    void navigateToEcgRecordingActivity();
}
