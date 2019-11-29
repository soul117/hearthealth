package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PESettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase.PESettingsUseCaseContract;

/**
 * Created by Alexander Molochko on 4/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PESettingsUseCase extends BaseSingleUseCase<Void, PESettingsEntity> implements PESettingsUseCaseContract {
    private PESettingsRepositoryContract mSettingsRepository;

    public PESettingsUseCase(ExecutionThread threadExecutor, PostExecutionThread postExecutionThread, PESettingsRepositoryContract settingsRepository) {
        super(threadExecutor, postExecutionThread);
        mSettingsRepository = settingsRepository;
    }

    @Override
    public Single<PESettingsEntity> getSettings() {
        return mSettingsRepository.getSettings();
    }

    @Override
    public void saveSettings(PESettingsEntity paSettings) {
        mSettingsRepository.saveSettings(paSettings);
    }

    @Override
    public Single<PESettingsEntity> provideSingleObservable(Void aVoid) {
        return null;
    }
}

