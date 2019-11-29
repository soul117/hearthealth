package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PESettingsModel {
    // Constants
    public static final String KEY_GAME_TIME = "game_time";
    public static final String KEY_GAME_MODE = "game_mode";
    private static final long DEFAULT_GAME_TIME = 60;
    // Variables
    private long mGameTime;
    private long mGameModeId;

    public PESettingsModel(SettingsBundle settingsBundle) {
        mGameModeId = settingsBundle.getLongSetting(KEY_GAME_MODE);
        mGameTime = settingsBundle.getLongSetting(KEY_GAME_TIME);
        defaultValuesAreSet(settingsBundle);
    }

    public boolean defaultValuesAreSet(SettingsBundle settingsBundle) {
        boolean anyChanges = false;
        if (mGameTime <= 0) {
            mGameTime = DEFAULT_GAME_TIME;
            settingsBundle.setLongSetting(KEY_GAME_TIME, getGameTime());
            anyChanges = true;
        }
        return anyChanges;
    }

    public PESettingsModel(long gameModeId, long gameTime) {
        mGameTime = gameTime;
        mGameModeId = gameModeId;
    }

    public PESettingsModel() {

    }

    public void saveStateToBundle(SettingsBundle bundle) {
        bundle.setLongSetting(KEY_GAME_MODE, getGameModeId());
        bundle.setLongSetting(KEY_GAME_TIME, getGameTime());
    }


    public long getGameModeId() {
        return mGameModeId;
    }

    public void setGameModeId(long gameModeId) {
        mGameModeId = gameModeId;
    }

    public long getGameTime() {
        return mGameTime;
    }

    public void setGameTime(long gameTime) {
        mGameTime = gameTime;
    }
}
