package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.presenter;


import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.routing.PARouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.view.PASettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface PASettingsPresenterContract extends BasePresenterContract<PASettingsViewContract, PARouterContract> {
    void onBackButtonPress();
    void startExperiment();

    void saveSettings(PASettingsEntity settings);

}
