package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.data.AppSettingsRepositoryContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AppSettingsRepository implements AppSettingsRepositoryContract {
    // Constants
    private static final String APP_SETTINGS_KEY = "experiments_settings.physical_activity";
    // Variables
    private SettingsManager mSettingsManager;

    public AppSettingsRepository(SettingsManager settingsManager) {
        mSettingsManager = settingsManager;
    }

    @Override
    public Single<Void> saveAppSettings(final AppSettingsModel appSettingsModel) {
        final SettingsBundle settingsBundle = mSettingsManager.getSettingsBundle(APP_SETTINGS_KEY);
        return new Single<Void>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Void> observer) {
                mSettingsManager.saveSettingsBundle(APP_SETTINGS_KEY, saveSettingsToSettingsBundle(appSettingsModel, settingsBundle));
                observer.onSuccess(null);
            }
        };
    }

    @Override
    public Single<AppSettingsModel> getAppSettings() {
        final SettingsBundle settingsBundle = mSettingsManager.getSettingsBundle(APP_SETTINGS_KEY);
        return new Single<AppSettingsModel>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super AppSettingsModel> observer) {
                observer.onSuccess(createAppSettingsFromSettingsBundle(settingsBundle));
            }
        };
    }

    private SettingsBundle saveSettingsToSettingsBundle(AppSettingsModel appSettingsModel, SettingsBundle settingsBundle) {
        settingsBundle.setBooleanSetting(AppSettingsModel.KEY_SHOW_COUNTDOWN_TIMER, appSettingsModel.showCountdownTimer());
        settingsBundle.setLongSetting(AppSettingsModel.KEY_VOLTAGE_SCALE, appSettingsModel.getVoltageScale());
        settingsBundle.setLongSetting(AppSettingsModel.KEY_RECORDING_DURATION, appSettingsModel.getRecordingTime());
        return settingsBundle;
    }

    private AppSettingsModel createAppSettingsFromSettingsBundle(SettingsBundle settingsBundle) {
        AppSettingsModel appSettingsModel = new AppSettingsModel();
        appSettingsModel.setRecordingTime(settingsBundle.getLongSetting(AppSettingsModel.KEY_RECORDING_DURATION));
        appSettingsModel.setShowCountdownTimer(settingsBundle.getBooleanSetting(AppSettingsModel.KEY_SHOW_COUNTDOWN_TIMER));
        appSettingsModel.setVoltageScale(settingsBundle.getLongSetting(AppSettingsModel.KEY_VOLTAGE_SCALE));
        appSettingsModel.initDefaultValuesIfRequired();
        return appSettingsModel;
    }
}
