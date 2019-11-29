package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface ModesRouterContract extends BaseRouter {
    void openModeDescription(int modeId);

    void startMotivationalMode(int modeId);

    void openMotivationalModeSettings(int modeId);

    void hideNavigationBar();

    void showNavigationBar();

}
