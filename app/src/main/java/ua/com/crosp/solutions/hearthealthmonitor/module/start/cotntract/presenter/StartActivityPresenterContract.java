package ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.StartRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.view.StartActivityViewContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface StartActivityPresenterContract extends BasePresenterContract<StartActivityViewContract, StartRouterContract> {

    void onUserInfoEnterAccept();

    void onUserInfoEnterDecline();
}
