package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.GetAllExperimentResultsForUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.RemoveExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.ExperimentResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.ExperimentResultListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.presenter.ExperimentResultListPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class ExperimentListModule {

    @Provides
    @PerModule
    ExperimentResultRouterContract provideModesListRouter(Activity activity) {
        return (ExperimentResultRouterContract) activity;
    }

    @Provides
    @PerModule
    ExperimentResultListPresenterContract provideExperimentResultListPresenter(ExperimentResultRouterContract router, GetAllExperimentResultsForUserUseCase useCase, UserSessionManagerContract sessionManager, RemoveExperimentResultByIdUseCase removeUseCase) {
        return new ExperimentResultListPresenter(removeUseCase,router, useCase, sessionManager);
    }

}
