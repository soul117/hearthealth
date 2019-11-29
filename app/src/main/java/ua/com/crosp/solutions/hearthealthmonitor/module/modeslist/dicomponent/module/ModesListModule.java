package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent.module;

import android.app.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.mapper.MapperConstants;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.ModesRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.data.ModesRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.presenter.ModesListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.usecase.ModesListUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.domain.usecase.ModesListUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.ModesListPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class ModesListModule {

    //================================================================================
    // Modes ModelList GameScreen
    //================================================================================

    @Provides
    @PerModule
    ModesListPresenterContract provideModesListPresenter(ModesRouterContract router,
                                                         ModesListUseCaseContract modesListUseCaseContract,
                                                         @Named(MapperConstants.EXPERIMENT_MODE) BaseMapper<ModeItem, ModeListItem> modeMapper) {
        return new ModesListPresenter(router, modesListUseCaseContract, modeMapper);
    }

    @Provides
    @PerModule
    ModesRouterContract provideModesListRouter(Activity activity) {
        return (ModesRouterContract) activity;
    }

    @Provides
    @PerModule
    ModesListUseCaseContract provideModesListUseCase(ModesRepositoryContract modesListRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new ModesListUseCase(modesListRepository, executionThread, postExecutionThread);
    }


}
