package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.settings;

import android.content.Context;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AndroidSettingsManager implements SettingsManager {
    private Context mContext;

    public AndroidSettingsManager(Context context) {
        mContext = context;
    }

    @Override
    public SettingsBundle getSettingsBundle(String key, int mode) {
        return new AndroidSettingsBundle(mContext.getSharedPreferences(key, mode));
    }

    @Override
    public SettingsBundle getSettingsBundle(String key) {
        return this.getSettingsBundle(key, Context.MODE_PRIVATE);
    }

    @Override
    public void saveSettingsBundle(String key, SettingsBundle bundle) {
        // Persistent is handled by the bundle in this case
    }
}
