package ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.presentation;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.routing.NavigationDrawerRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.view.NavigationDrawerViewContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface NavigationDrawerPresenterContract extends BasePresenterContract<NavigationDrawerViewContract, NavigationDrawerRouterContract> {
    void onUserAvatarClick();

    void onMenuItemSelected();

}
