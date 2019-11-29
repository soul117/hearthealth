package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.data.ModesRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.usecase.ModesListUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class ModesListUseCase extends BaseSingleUseCase<Void, ModeItem.List> implements ModesListUseCaseContract {
    private ModesRepositoryContract mModesRepository;

    @Inject
    public ModesListUseCase(ModesRepositoryContract modesRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mModesRepository = modesRepository;
    }

    @Override
    public Single<List<ModeItem>> getModesList() {
        return mModesRepository.getAllModes();
    }


    @Override
    public Single<ModeItem.List> execute(Void parameters) {
        return null;
    }

    @Override
    public Single<ModeItem.List> provideSingleObservable(Void aVoid) {
        return null;
    }

    public static final class Params {

        private final int userId;

        private Params(int userId) {
            this.userId = userId;
        }

        public static Params forUser(int userId) {
            return new Params(userId);
        }
    }
}
