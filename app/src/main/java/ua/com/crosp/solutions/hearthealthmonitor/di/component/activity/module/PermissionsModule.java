package ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.permissions.PermissionsHandlerContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.permissions.AndroidPermissionsHandler;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerActivity
@Module
public class PermissionsModule {

    @PerActivity
    @Provides
    PermissionsHandlerContract proividePermissionsHandler(Activity activity) {
        return new AndroidPermissionsHandler(activity);
    }

}
