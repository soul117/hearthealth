package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.presenter;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.routing.PERouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.PESettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PEBallGameMode;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface PESettingsPresenterContract extends BasePresenterContract<PESettingsViewContract, PERouterContract> {

    void onBackButtonPress();

    void startExperiment();

    void saveSettings(PESettingsEntity settings);

    Single<PEBallGameMode.List> getAvailableGameModes();
}
