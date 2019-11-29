package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.data.PASettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.domain.PASettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.presenter.PASettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.routing.PARouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.PASettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.presentation.PASettingsPresenter;


/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class PASettingsModule {

    public PASettingsModule() {
    }


    @Provides
    @PerModule
    PASettingsPresenterContract providePASettingsPresenter(PARouterContract router,
                                                           PASettingsUseCaseContract useCase) {
        return new PASettingsPresenter(router, useCase);
    }

    @Provides
    @PerModule
    PASettingsUseCaseContract providePASettingsUseCase(PASettingsRepositoryContract settingsRepository) {
        return new PASettingsUseCase(settingsRepository);
    }

}
