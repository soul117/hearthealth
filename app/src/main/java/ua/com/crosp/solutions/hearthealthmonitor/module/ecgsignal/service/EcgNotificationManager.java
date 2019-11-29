package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import javax.inject.Inject;

import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification.EcgNotificationManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification.EcgNotificationViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.data.EcgNotificationInfo;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgNotificationManager implements EcgNotificationManagerContract {
    public static final int ECG_BAR_NOTIFICATION_ID = 666;
    public static final String ECG_NOTIFICATION_CHANNEL_ID = String.valueOf(888);
    public static final String NOTIFICATION_CHANNEL_NAME = "ECG Notifications";


    // Dependencies
    @Inject
    NotificationManagerCompat mNotificationManagerCompat;
    @Inject
    NotificationManager mNotificationManager;
    @Inject
    Context mServiceContext;

    @Inject
    IntentHandler mIntentHandler;

    @Inject
    EcgNotificationViewContract mCurrentNotificationView;


    @Inject
    public EcgNotificationManager(Context serviceContext, NotificationManager notificationManager, NotificationManagerCompat notificationManagerCompat, IntentHandler intentHandler, EcgNotificationViewContract ecgNotificationViewContract) {
        mNotificationManagerCompat = notificationManagerCompat;
        mNotificationManager = notificationManager;
        mServiceContext = serviceContext;
        mIntentHandler = intentHandler;
        mCurrentNotificationView = ecgNotificationViewContract;
        this.initNotificationManager();
    }

    private void initNotificationManager() {
        mNotificationManagerCompat = NotificationManagerCompat.from(mServiceContext);
        mNotificationManagerCompat.cancelAll();
    }

    @Override
    public void showNotification(String title, String description) {

    }

    @Override
    public void showNotification(String title) {

    }

    @Override
    public void showStreamingNotification(EcgNotificationInfo ecgNotificationInfo) {
        //((Service) mServiceContext).startForeground(ECG_BAR_NOTIFICATION_ID, mCurrentNotificationView.buildNotification(ecgNotificationInfo));
        ecgNotificationInfo.id = String.valueOf(ECG_BAR_NOTIFICATION_ID);
        // Check if API requires notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(ecgNotificationInfo);
            Notification notification = mCurrentNotificationView.buildNotificationForNewApi(ecgNotificationInfo, ECG_NOTIFICATION_CHANNEL_ID);
            mNotificationManager.notify(ECG_BAR_NOTIFICATION_ID, notification);
        } else {
            Notification notification = mCurrentNotificationView.buildNotification(ecgNotificationInfo);
            mNotificationManagerCompat.notify(ECG_BAR_NOTIFICATION_ID, notification);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(EcgNotificationInfo ecgNotificationInfo) {
        NotificationChannel notificationChannel = new NotificationChannel(ECG_NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        // Configure the notification channel.
        notificationChannel.setDescription(ecgNotificationInfo.description);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setVibrationPattern(new long[]{0, 500});
        notificationChannel.enableVibration(true);
        mNotificationManager.createNotificationChannel(notificationChannel);
    }

    @Override
    public void setIntentHandler(IntentHandler intentHandler) {
        mIntentHandler = intentHandler;
    }

    @Override
    public IntentHandler getIntentHandler() {
        return mIntentHandler;
    }

    @Override
    public void hideAllNotifications() {
        mNotificationManagerCompat.cancel(ECG_BAR_NOTIFICATION_ID);
    }


}
