package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface AppSettingsViewContract extends BaseView {
    void displayAppSettings(AppSettingsModel appSettings);

    void showSettingsSuccessfullySavedMessage();
}
