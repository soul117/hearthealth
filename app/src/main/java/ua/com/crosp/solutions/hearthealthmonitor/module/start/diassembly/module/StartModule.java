package ua.com.crosp.solutions.hearthealthmonitor.module.start.diassembly.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.StartRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.presenter.StartActivityPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.presentation.StartActivityPresenter;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class StartModule {


    @Provides
    @PerModule
    StartActivityPresenterContract provideStartActivityPresenter(StartRouterContract router, UserSessionManagerContract sessionManagerContract) {
        return new StartActivityPresenter(router, sessionManagerContract);
    }


    @Provides
    @PerModule
    StartRouterContract provideRouter(Activity activity) {
        return (StartRouterContract) activity;
    }

}
