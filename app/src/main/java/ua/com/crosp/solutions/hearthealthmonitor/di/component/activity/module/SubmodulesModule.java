package ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseModuleCommunicationManager;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.ModuleCommunicationManager;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerActivity
public class SubmodulesModule {
    /**
     * Provide the BaseModuleCommunicationManager to dependents in the graph.
     */
    @Provides
    @PerActivity
    BaseModuleCommunicationManager provideModuleCommunicationManager() {
        return new ModuleCommunicationManager();
    }
}
