package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.data.PASettingsRepositoryContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PASettingsRepository implements PASettingsRepositoryContract {
    // Constants
    private static final String PHYSICAL_ACTIVITY_SETTINGS_KEY = "experiments_settings.physical_activity";
    // Variables
    private SettingsManager mSettingsManager;
    private BaseMapper<PASettingsModel, PASettingsEntity> mSettingsMapper;
    private BaseMapper<PASettingsEntity, PASettingsModel> mSettingsMapperReverse;

    public PASettingsRepository(SettingsManager settingsManager, BaseMapper<PASettingsModel, PASettingsEntity> settingsMapper,
                                BaseMapper<PASettingsEntity, PASettingsModel> reverseMapper) {
        mSettingsManager = settingsManager;
        mSettingsMapper = settingsMapper;
        mSettingsMapperReverse = reverseMapper;
    }

    @Override
    public Single<PASettingsEntity> getPASettings() {
        SettingsBundle experimentsSettingsBundle = mSettingsManager.getSettingsBundle(PHYSICAL_ACTIVITY_SETTINGS_KEY);
        PASettingsModel settingsModel = new PASettingsModel(experimentsSettingsBundle);
        if (settingsModel.defaultValuesAreSet(experimentsSettingsBundle)) {
            mSettingsManager.saveSettingsBundle(PHYSICAL_ACTIVITY_SETTINGS_KEY, experimentsSettingsBundle);
        }
        return Single.just(settingsModel).map(mSettingsMapper);
    }

    @Override
    public void savePASettings(PASettingsEntity settingsEntity) {
        SettingsBundle experimentsSettingsBundle = mSettingsManager.getSettingsBundle(PHYSICAL_ACTIVITY_SETTINGS_KEY);
        PASettingsModel settingsModel = mSettingsMapperReverse.transform(settingsEntity);
        settingsModel.saveStateToBundle(experimentsSettingsBundle);
        mSettingsManager.saveSettingsBundle(PHYSICAL_ACTIVITY_SETTINGS_KEY, experimentsSettingsBundle);

    }

    @Override
    public PASettingsEntity getSettingsSync() {
        SettingsBundle experimentsSettingsBundle = mSettingsManager.getSettingsBundle(PHYSICAL_ACTIVITY_SETTINGS_KEY);
        PASettingsModel settingsModel = new PASettingsModel(experimentsSettingsBundle);
        if (settingsModel.defaultValuesAreSet(experimentsSettingsBundle)) {
            mSettingsManager.saveSettingsBundle(PHYSICAL_ACTIVITY_SETTINGS_KEY, experimentsSettingsBundle);
        }
        return mSettingsMapper.transform(settingsModel);
    }

}
