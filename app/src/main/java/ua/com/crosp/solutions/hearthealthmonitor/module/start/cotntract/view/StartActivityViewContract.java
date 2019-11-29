package ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface StartActivityViewContract extends BaseView {
    void showNoUserInfoWarning();

    void showProgressDialog();

    void hideProgressDialog();
}

