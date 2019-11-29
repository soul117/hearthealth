package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.routing;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface PARouterContract extends BaseRouter {
    void startGame();

    void switchToSettings();

    void navigateToEcgRecordingActivity(Long experimentResultId);
}
