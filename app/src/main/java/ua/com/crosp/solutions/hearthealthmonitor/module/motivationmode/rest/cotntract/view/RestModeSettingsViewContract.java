package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.view;

import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.view.ModeSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface RestModeSettingsViewContract extends ModeSettingsViewContract {
    void displayRestModeSettings(RestSettingsEntity settingsEntity);
}
