package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.router;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface UserSettingsRouterContract extends BaseRouter {

    public void switchToSettings();

    public void hideNavigationBar();

    public void showNavigationBar();

    public void switchToMainAppScreen();

}
