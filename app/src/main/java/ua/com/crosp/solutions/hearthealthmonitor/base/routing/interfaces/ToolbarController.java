package ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces;

import androidx.appcompat.widget.Toolbar;

/**
 * Created by Alexander Molochko on 1/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ToolbarController {
    Toolbar getToolbar();

    String getToolbarTitle();

    void showToolbar();

    void hideToolbar();

    void setToolbarTitle(String title);
}
