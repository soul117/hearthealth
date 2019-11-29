package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;

import java.util.List;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface ModesListViewContract extends BaseView {
    void displayModesList(List<ModeListItem> modes);
}
