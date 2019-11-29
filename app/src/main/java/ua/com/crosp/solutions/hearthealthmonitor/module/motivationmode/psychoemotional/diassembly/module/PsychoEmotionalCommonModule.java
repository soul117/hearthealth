package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.routing.PERouterContract;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class PsychoEmotionalCommonModule {
    public PsychoEmotionalCommonModule() {
    }

    @Provides
    @PerModule
    PERouterContract provideModeRouter(Activity activity) {
        return (PERouterContract) activity;
    }


    @Provides
    @PerModule
    BackPressNotifier provideBackPressNotifier(Activity activity) {
        return (BackPressNotifier) activity;
    }


}
