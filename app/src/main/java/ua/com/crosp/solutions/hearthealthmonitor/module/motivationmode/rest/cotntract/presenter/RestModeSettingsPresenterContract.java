package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.RestModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.view.RestModeSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface RestModeSettingsPresenterContract extends BasePresenterContract<RestModeSettingsViewContract, RestModeRouterContract> {
    void onBackButtonPress();

    void startExperiment();

    void saveSettings(RestSettingsEntity settings);
}
