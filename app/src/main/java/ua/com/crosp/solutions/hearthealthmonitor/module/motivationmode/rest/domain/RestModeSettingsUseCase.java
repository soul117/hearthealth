package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.data.RestSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.domain.RestSettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;

/**
 * Created by Alexander Molochko on 4/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestModeSettingsUseCase extends BaseSingleUseCase<Void, RestSettingsEntity> implements RestSettingsUseCaseContract {
    private RestSettingsRepositoryContract mSettingsRepository;

    public RestModeSettingsUseCase(RestSettingsRepositoryContract settingsRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mSettingsRepository = settingsRepository;
    }

    @Override
    public Single<RestSettingsEntity> getRestModeSettings() {
        return mSettingsRepository.getSettings();
    }

    @Override
    public void saveRestModeSettings(RestSettingsEntity settingsEntity) {
        mSettingsRepository.saveSettings(settingsEntity);
    }

    @Override
    public Single<RestSettingsEntity> provideSingleObservable(Void aVoid) {
        return null;
    }
}
