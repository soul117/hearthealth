package ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.presentation.NavigationDrawerPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.routing.NavigationDrawerRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.presentation.NavigationDrawerPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerActivity
public class NavigationDrawerModule {
    @Provides
    @PerActivity
    NavigationDrawerRouterContract provideNavigationDrawerRouterContract(Activity activity) {
        return (NavigationDrawerRouterContract) activity;
    }

    @Provides
    @PerActivity
    NavigationDrawerPresenterContract provideNavigationDrawerPresenter(UserSessionManagerContract userSessionManager, NavigationDrawerRouterContract router) {
        return new NavigationDrawerPresenter(router, userSessionManager);
    }
}
