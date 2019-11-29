package ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module;

import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerService;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerService
@Module
public class NotificationModule {
    @Provides
    @PerService
    NotificationManagerCompat provideEcgNotificationManagerCompat(@Named(APPLICATION_CONTEXT) Context context) {
        return NotificationManagerCompat.from(context);
    }

    @Provides
    @PerService
    NotificationManager provideEcgNotificationManager(@Named(APPLICATION_CONTEXT) Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
