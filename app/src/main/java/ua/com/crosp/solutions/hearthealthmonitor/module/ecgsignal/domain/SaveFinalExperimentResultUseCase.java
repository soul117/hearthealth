package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.data.AppSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.EcgResultEntity;

/**
 * Created by Alexander Molochko on 4/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SaveFinalExperimentResultUseCase extends BaseSingleUseCase<EcgResultEntity, Boolean> {
    private AppSettingsRepositoryContract mRepository;


    public SaveFinalExperimentResultUseCase(AppSettingsRepositoryContract settingsRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mRepository = settingsRepository;
    }

    @Override
    public Single<Boolean> provideSingleObservable(EcgResultEntity ecgResultEntity) {

        return null;
    }
}
