package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface SettingsManager {
    public SettingsBundle getSettingsBundle(String key, int mode);

    public void saveSettingsBundle(String key, SettingsBundle bundle);

    public SettingsBundle getSettingsBundle(String key);
}
