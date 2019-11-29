package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification;

import android.app.PendingIntent;
import android.content.Intent;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.service.NotificationIntentModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.data.EcgNotificationInfo;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface EcgNotificationManagerContract extends NotificationManager {

    public void showStreamingNotification(EcgNotificationInfo ecgNotificationInfo);

    public void setIntentHandler(IntentHandler intentHandler);

    public IntentHandler getIntentHandler();
    
    public void hideAllNotifications();

    public interface IntentHandler {
        public void handleIntent(Intent intent, int flags, int startId);

        public NotificationIntentModel providePlayPauseIntentHandler();

        public NotificationIntentModel provideStopIntentHandler();

        public NotificationIntentModel provideCloseIntentHandler();

        public PendingIntent createContentIntent(EcgNotificationInfo ecgNotificationInfo);

    }
}
