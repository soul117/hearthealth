package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent.module;

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
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.presenter.ModeDescriptionPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.usecase.ModeDescriptionUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.domain.usecase.ModeDescriptionUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.ModeDescriptionPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class ModeDescriptionModule {

    //================================================================================
    // Mode Description GameScreen
    //================================================================================
    @Provides
    @PerModule
    ModeDescriptionPresenterContract provideModesListPresenter(ModesRouterContract router,
                                                               ModeDescriptionUseCaseContract useCase,
                                                               @Named(MapperConstants.EXPERIMENT_MODE) BaseMapper<ModeItem, ModeListItem> modeMapper)


    {
        return new ModeDescriptionPresenter(router, useCase, modeMapper);
    }

    @Provides
    @PerModule
    ModeDescriptionUseCaseContract provideModeDescriptionUseCase(ModesRepositoryContract modesListRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        return new ModeDescriptionUseCase(modesListRepository, threadExecutor, postExecutionThread);
    }


}
