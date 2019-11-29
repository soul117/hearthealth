package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface SettingsBundle {
    public void setIntSetting(String key, int value);

    public void setBooleanSetting(String key, boolean value);

    public void setLongSetting(String key, long value);

    public void setFloatSetting(String key, float value);

    public void setStringSetting(String key, String value);

    public int getIntSettings(String key);

    public long getLongSetting(String key);

    public float getFloatSetting(String key);

    public String getStringSetting(String key);

    public boolean getBooleanSetting(String key);

}
