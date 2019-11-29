package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.presenter;


import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.routing.AppSettingsRouter;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.view.AppSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface AppSettingsPresenterContract extends BasePresenterContract<AppSettingsViewContract, AppSettingsRouter> {
    void onBackButtonPress();

    void onSaveAppSettingsRequest(AppSettingsModel settings);

}
