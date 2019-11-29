package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.BaseGameViewPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.routing.PARouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.SquatGameViewContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface SquatGamePresenterContract extends BasePresenterContract<SquatGameViewContract, PARouterContract>, BaseGameViewPresenter {

    void onBackPressed();

}
