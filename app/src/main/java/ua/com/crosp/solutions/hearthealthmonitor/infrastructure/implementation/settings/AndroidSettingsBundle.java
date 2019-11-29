package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.settings;

import android.content.SharedPreferences;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AndroidSettingsBundle implements SettingsBundle {

    public static class DefaultSettingsValue {
        public static final int INTEGER = 0;
        public static final long LONG = -1L;
        public static final String STRING = "";
        public static final float FLOAT = 0.0f;
        public static final boolean BOOLEAN = false;
    }

    private SharedPreferences mSharedPreferences;

    public AndroidSettingsBundle(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;

    }

    @Override
    public void setIntSetting(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public void setBooleanSetting(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public void setLongSetting(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    @Override
    public void setFloatSetting(String key, float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
    }

    @Override
    public void setStringSetting(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public int getIntSettings(String key) {
        return mSharedPreferences.getInt(key, DefaultSettingsValue.INTEGER);
    }

    @Override
    public long getLongSetting(String key) {
        return mSharedPreferences.getLong(key, DefaultSettingsValue.LONG);
    }

    @Override
    public float getFloatSetting(String key) {
        return mSharedPreferences.getFloat(key, DefaultSettingsValue.FLOAT);
    }

    @Override
    public String getStringSetting(String key) {
        return mSharedPreferences.getString(key, DefaultSettingsValue.STRING);
    }

    @Override
    public boolean getBooleanSetting(String key) {
        return mSharedPreferences.getBoolean(key, DefaultSettingsValue.BOOLEAN);
    }

}
