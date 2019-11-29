package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.domain;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.data.RestSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.domain.RestMainUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;

/**
 * Created by Alexander Molochko on 4/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestModeMainUseCase extends BaseSingleUseCase<Void, RestSettingsEntity> implements RestMainUseCaseContract {
    private RestSettingsRepositoryContract mSettingsRepository;

    public static final int DEFAULT_REST_TIME = 60;


    public RestModeMainUseCase(ExecutionThread threadExecutor, PostExecutionThread postExecutionThread, RestSettingsRepositoryContract settingsRepository) {
        super(threadExecutor, postExecutionThread);
        mSettingsRepository = settingsRepository;
    }

    @Override
    public Single<RestSettingsEntity> getRestModeSettings() {
        return mSettingsRepository.getSettings().map(new Function<RestSettingsEntity, RestSettingsEntity>() {

            @Override
            public RestSettingsEntity apply(@NonNull RestSettingsEntity restSettingsEntity) throws Exception {
                return validateSettings(restSettingsEntity);
            }
        });
    }

    private RestSettingsEntity validateSettings(RestSettingsEntity entity) {
        if (entity == null) {
            entity = new RestSettingsEntity();
            entity.setRestTime(DEFAULT_REST_TIME);
        }
        if (entity.getRestTime() <= 0) {
            entity.setRestTime(DEFAULT_REST_TIME);
        }
        return entity;
    }

    @Override
    public void startRecordingECG() {

    }

    @Override
    public Single<RestSettingsEntity> provideSingleObservable(Void aVoid) {
        return null;
    }
}
