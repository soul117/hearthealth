package ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.routing;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.ApplicationNavigator;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface NavigationDrawerRouterContract extends BaseRouter {
    ApplicationNavigator getApplicationNavigator();

    void switchToUserSettings();
}
