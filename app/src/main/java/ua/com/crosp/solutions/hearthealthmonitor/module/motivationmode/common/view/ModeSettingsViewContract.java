package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;

/**
 * Created by Alexander Molochko on 8/29/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ModeSettingsViewContract extends BaseView {
    /**
     * Provide title for mode settings screen to be displayed in the toolbar
     *
     * @return the title of the settings screen
     */
    public String provideModeSettingsTitle();

    public void setToolbarTitle(String title);
}
