package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.data.ModesRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.usecase.ModeDescriptionUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class ModeDescriptionUseCase extends BaseSingleUseCase<Integer, ModeItem> implements ModeDescriptionUseCaseContract {
    private ModesRepositoryContract mModesRepository;

    public ModeDescriptionUseCase(ModesRepositoryContract modesRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mModesRepository = modesRepository;
    }

    @Override
    public Single<ModeItem> provideSingleObservable(Integer modeId) {
        return mModesRepository.getModeById(modeId);
    }

    @Override
    public Single<ModeItem> getModeDescriptionWithId(int id) {
        return mModesRepository.getModeById(id);
    }
}
