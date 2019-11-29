package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data;

import android.content.res.Resources;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PEGameModeRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PESettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.mapper.PESettingsM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PsychoEmotionalSettingsRepository implements PESettingsRepositoryContract, PEGameModeRepositoryContract, PESettingsM2EMapperContract.FullGameModeInfoProvider {
    // Constants
    private static final String PE_SETTINGS_KEY = "experiments_settings.psycho_emotional";
    private final Resources mResources;
    // Variables
    private SettingsManager mSettingsManager;
    private PESettingsM2EMapperContract mSettingsMapper;
    private BaseMapper<PESettingsEntity, PESettingsModel> mSettingsMapperReverse;

    public PsychoEmotionalSettingsRepository(Resources resources, SettingsManager settingsManager,
                                             PESettingsM2EMapperContract settingsMapper,
                                             BaseMapper<PESettingsEntity, PESettingsModel> reverseMapper) {
        mSettingsManager = settingsManager;
        mSettingsMapper = settingsMapper;
        mSettingsMapper.setFullInfoProvider(this);
        mResources = resources;
        mSettingsMapperReverse = reverseMapper;
        initStaticData();
    }

    private void initStaticData() {
        PEBallGameMode.initDefaultGameModes(mResources);
    }

    @Override
    public Single<PESettingsEntity> getSettings() {
        SettingsBundle experimentsSettingsBundle = mSettingsManager.getSettingsBundle(PE_SETTINGS_KEY);
        PESettingsModel settingsModel = new PESettingsModel(experimentsSettingsBundle);
        if (settingsModel.defaultValuesAreSet(experimentsSettingsBundle)) {
            mSettingsManager.saveSettingsBundle(PE_SETTINGS_KEY, experimentsSettingsBundle);
        }
        return Single.just(settingsModel).map(mSettingsMapper);
    }

    @Override
    public PESettingsEntity getSettingsSync() {
        SettingsBundle experimentsSettingsBundle = mSettingsManager.getSettingsBundle(PE_SETTINGS_KEY);
        PESettingsModel settingsModel = new PESettingsModel(experimentsSettingsBundle);
        if (settingsModel.defaultValuesAreSet(experimentsSettingsBundle)) {
            mSettingsManager.saveSettingsBundle(PE_SETTINGS_KEY, experimentsSettingsBundle);
        }
        return mSettingsMapper.transform(settingsModel);
    }

    @Override
    public void saveSettings(PESettingsEntity settingsEntity) {
        SettingsBundle experimentsSettingsBundle = mSettingsManager.getSettingsBundle(PE_SETTINGS_KEY);
        PESettingsModel settingsModel = mSettingsMapperReverse.transform(settingsEntity);
        settingsModel.saveStateToBundle(experimentsSettingsBundle);
        mSettingsManager.saveSettingsBundle(PE_SETTINGS_KEY, experimentsSettingsBundle);
    }

    @Override
    public Single<PEBallGameMode.List> getAvailableGameModes() {
        return Single.just(PEBallGameMode.getDefaultGameModes());
    }

    @Override
    public Single<PEBallGameMode> getGameModeById(final long id) {
        return getAvailableGameModes()
                .flatMap(new Function<PEBallGameMode.List, SingleSource<? extends PEBallGameMode>>() {
                    @Override
                    public SingleSource<? extends PEBallGameMode> apply(@NonNull PEBallGameMode.List peBallGameModes) throws Exception {
                        return Single.just(peBallGameModes.getModeById(id));
                    }
                });
    }

    private PEBallGameMode.List getAvailableGameModesSync() {
        return PEBallGameMode.getDefaultGameModes();
    }

    @Override
    public PEBallGameMode getFullGameModeInfoById(long id) {
        return getAvailableGameModesSync()
                .getModeById(id);
    }
}
