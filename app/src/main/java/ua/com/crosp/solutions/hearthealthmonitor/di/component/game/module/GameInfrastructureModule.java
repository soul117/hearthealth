package ua.com.crosp.solutions.hearthealthmonitor.di.component.game.module;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.AndroidGraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;

import static ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule.CONTEXT_ACTIVITY;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class GameInfrastructureModule {
    @Provides
    @PerModule
    GraphicsRenderer provideGraphicsRenderer(@Named(CONTEXT_ACTIVITY) Context context) {
        return new AndroidGraphicsRenderer(context);
    }
}
