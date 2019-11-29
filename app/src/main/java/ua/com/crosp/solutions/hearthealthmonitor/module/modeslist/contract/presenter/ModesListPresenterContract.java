package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.ModesRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.view.ModesListViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface ModesListPresenterContract extends BasePresenterContract<ModesListViewContract, ModesRouterContract> {
    void onModeItemClick(ModeListItem modeListItem);
}
