package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface EcgRouterContract extends BaseRouter {

    void switchToMainEcgScreen();

    void switchToResultScreen();

    void switchToRealtimeRecordingScreen(long estimatedTime);
}
