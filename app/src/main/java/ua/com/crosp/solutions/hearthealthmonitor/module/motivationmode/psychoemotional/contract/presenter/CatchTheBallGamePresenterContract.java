package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.BaseGameViewPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.routing.PERouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.CatchTheBallViewContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface CatchTheBallGamePresenterContract extends BasePresenterContract<CatchTheBallViewContract, PERouterContract>, BaseGameViewPresenter {

    void onBackPressed();

}
