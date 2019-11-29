package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.routing.PARouterContract;

/**
 * Created by Alexander Molochko on 4/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

@Module
@PerModule
public class PACommonModule {
    @Provides
    @PerModule
    PARouterContract providePARouter(Activity activity) {
        return (PARouterContract) activity;
    }

    @Provides
    @PerModule
    BackPressNotifier provideBackPressNotifier(Activity activity) {
        return (BackPressNotifier) activity;
    }

}
