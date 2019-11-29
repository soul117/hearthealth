package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface ExperimentResultRouterContract extends BaseRouter {
    void openExperimentDetailsScreen(Long modeId);

    void hideNavigationBar();

    void showNavigationBar();

    void switchToEcgChartScreen(Long expId);
}
