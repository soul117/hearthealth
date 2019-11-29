package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.common.router.HeartMonitorModeNavigator;
import ua.com.crosp.solutions.hearthealthmonitor.common.router.HeartMonitorNavigator;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.ApplicationNavigator;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.MotivationalModeNavigator;

/**
 * Created by Alexander Molochko on 2/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerApplication
public class NavigationModule {
    public NavigationModule() {
    }

    @Provides
    @PerApplication
    public ApplicationNavigator provideApplicationNavigator() {
        return new HeartMonitorNavigator();
    }

    @Provides
    @PerApplication
    public MotivationalModeNavigator provideMotivationalModeNavigator() {
        return new HeartMonitorModeNavigator();
    }
}
