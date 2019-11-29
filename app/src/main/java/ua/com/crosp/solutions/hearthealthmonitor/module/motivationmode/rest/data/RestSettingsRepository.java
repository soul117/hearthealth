package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.data.RestSettingsRepositoryContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestSettingsRepository implements RestSettingsRepositoryContract {
    // Constants
    private static final String REST_SETTINGS_KEY = "experiments_settings.rest";
    // Variables
    private SettingsManager mSettingsManager;
    private BaseMapper<RestSettingsModel, RestSettingsEntity> mSettingsMapper;
    private BaseMapper<RestSettingsEntity, RestSettingsModel> mSettingsMapperReverse;

    public RestSettingsRepository(SettingsManager settingsManager, BaseMapper<RestSettingsModel, RestSettingsEntity> settingsMapper,
                                  BaseMapper<RestSettingsEntity, RestSettingsModel> reverseMapper) {
        mSettingsManager = settingsManager;
        mSettingsMapper = settingsMapper;
        mSettingsMapperReverse = reverseMapper;
    }

    @Override
    public Single<RestSettingsEntity> getSettings() {
        SettingsBundle experimentsSettingsBundle = mSettingsManager.getSettingsBundle(REST_SETTINGS_KEY);
        RestSettingsModel settingsModel = new RestSettingsModel(experimentsSettingsBundle);
        return Single.just(settingsModel).map(mSettingsMapper);
    }

    @Override
    public void saveSettings(RestSettingsEntity settingsEntity) {
        SettingsBundle experimentsSettingsBundle = mSettingsManager.getSettingsBundle(REST_SETTINGS_KEY);
        RestSettingsModel settingsModel = mSettingsMapperReverse.transform(settingsEntity);
        settingsModel.saveStateToBundle(experimentsSettingsBundle);
        mSettingsManager.saveSettingsBundle(REST_SETTINGS_KEY, experimentsSettingsBundle);
    }

}
