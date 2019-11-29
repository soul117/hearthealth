package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.infrastructure;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.settings.AndroidSettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.data.AppSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view.CatchTheBallGameFragment;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerApplication
@Module
public class SettingsModule {
    @Provides
    @PerApplication
    SettingsManager provideSettingsManager(@Named(APPLICATION_CONTEXT) Context context) {
        return new AndroidSettingsManager(context);
    }

    @Named(CatchTheBallGameFragment.DEFAULT_BACKGROUND_APP_COLOR)
    @Provides
    @PerApplication
    public Integer provideDefaultAppBackgroundColor(Resources resources) {
        return resources.getColor(R.color.app_primary_dark);
    }


}
