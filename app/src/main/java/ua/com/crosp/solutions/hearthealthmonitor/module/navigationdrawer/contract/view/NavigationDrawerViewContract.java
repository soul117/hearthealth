package ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.view.model.UserDrawerInfo;

/**
 * Created by Alexander Molochko on 10/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface NavigationDrawerViewContract extends BaseView {
    void displayCurrentUserInfo(UserDrawerInfo userDrawerInfo);

}
