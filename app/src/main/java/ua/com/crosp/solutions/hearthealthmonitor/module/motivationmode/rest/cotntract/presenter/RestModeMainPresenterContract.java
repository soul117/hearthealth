package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.BaseGameViewPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.RestModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.view.RestModeViewContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface RestModeMainPresenterContract extends BasePresenterContract<RestModeViewContract, RestModeRouterContract>, BaseGameViewPresenter {

    void onTimerStart(long timeSeconds);

    void onTimerRestart(long timeSeconds);



    long provideRestTime();


    void onBackPressed();

    void onGameFinished(long currentTimeInSeconds);
}
