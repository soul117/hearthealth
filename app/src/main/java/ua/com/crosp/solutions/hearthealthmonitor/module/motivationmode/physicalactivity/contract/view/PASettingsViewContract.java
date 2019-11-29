package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.view.ModeSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface PASettingsViewContract extends ModeSettingsViewContract {
    void displayModeSettings(PASettingsEntity modeSettings);
}
