package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.module;

import android.app.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.mapper.MapperConstants;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.RestModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.data.RestSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.mapper.RestSettingsE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.mapper.RestSettingsM2EMapper;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class RestModeCommonModule {
    public RestModeCommonModule() {
    }

    @Provides
    @PerModule
    RestModeRouterContract provideRestModeRouter(Activity activity) {
        return (RestModeRouterContract) activity;
    }

    @Provides
    @PerModule
    BackPressNotifier provideBackPressNotifier(Activity activity) {
        return (BackPressNotifier) activity;
    }


}
