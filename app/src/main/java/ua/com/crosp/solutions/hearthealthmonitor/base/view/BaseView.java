package ua.com.crosp.solutions.hearthealthmonitor.base.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface BaseView<P extends BaseAppPresenter> {
    String getStringByResourceId(int stringId);

    void showErrorMessage(int messageStringId);

    void showSuccessMessage(int messageStringId);

    long getIntegerById(int id);
}
