package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PESettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase.GetPESettingsUseCaseContract;

/**
 * Created by Alexander Molochko on 4/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GetPESettingsUseCase extends BaseSingleUseCase<Void, PESettingsEntity> implements GetPESettingsUseCaseContract {
    private PESettingsRepositoryContract mSettingsRepository;

    public GetPESettingsUseCase(PESettingsRepositoryContract settingsRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mSettingsRepository = settingsRepository;
    }

    @Override
    public Single<PESettingsEntity> provideSingleObservable(Void aVoid) {
        return mSettingsRepository.getSettings();
    }
}

