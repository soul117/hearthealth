package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import android.widget.RemoteViews;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.service.NotificationIntentModel;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ResourceHelper;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification.EcgNotificationManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification.EcgNotificationViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.data.EcgNotificationInfo;

/**
 * Created by Alexander Molochko on 5/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgNotificationView implements EcgNotificationViewContract {

    private final EcgNotificationManagerContract.IntentHandler mIntentHandler;
    private Context mContext;
    private EcgNotificationInfo mCurrentEcgNotificationInfo;

    // Style variables

    private int mNotificationColor;
    private RemoteViews mContentViewBig;
    private RemoteViews mContentViewSmall;


    public EcgNotificationView(Context context, EcgNotificationManagerContract.IntentHandler intentHandler) {
        mContext = context;
        mIntentHandler = intentHandler;
        setupBaseResources();
    }

    private void setupBaseResources() {
        mNotificationColor = ResourceHelper.getColor(mContext, R.color.app_notification_background);
    }

    @Override
    public RemoteViews getSmallContentView() {
        if (mContentViewSmall == null) {
            mContentViewSmall = new RemoteViews(mContext.getPackageName(), R.layout.notification_remote_view_ecg_small);
            setUpRemoteView(mContentViewSmall);
        }
        updateRemoteViews(mContentViewSmall);
        return mContentViewSmall;
    }


    @Override
    public RemoteViews getBigContentView() {
        if (mContentViewBig == null) {
            mContentViewBig = new RemoteViews(mContext.getPackageName(), R.layout.notification_remote_view_ecg);
            setUpRemoteView(mContentViewBig);
        }
        updateRemoteViews(mContentViewBig);
        return mContentViewBig;
    }

    @Override
    public Notification buildNotification(EcgNotificationInfo ecgNotificationInfo) {
        mCurrentEcgNotificationInfo = ecgNotificationInfo;
        return this.buildNotificationFromInfo(ecgNotificationInfo).build();
    }

    @Override
    public Notification updateNotification(EcgNotificationInfo ecgNotificationInfo) {
        return buildNotification(ecgNotificationInfo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Notification buildNotificationForNewApi(EcgNotificationInfo ecgNotificationInfo, String notificationChannelId) {
        Logger.debug("updateNotificationMetadata. mMetadata=buildNotificationForNewApi");
        mCurrentEcgNotificationInfo = ecgNotificationInfo;
        return buildNotificationFromInfo(ecgNotificationInfo)
                .setChannelId(notificationChannelId)
                .build();
    }


    private NotificationCompat.Builder buildNotificationFromInfo(EcgNotificationInfo ecgNotificationInfo) {
        Logger.debug("updateNotificationMetadata. mMetadata=buildNotificationFromInfo");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, ecgNotificationInfo.id);

        // addEcgNotificationActions(notificationBuilder);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_notification_heart)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(mIntentHandler.createContentIntent(ecgNotificationInfo))
                //     .setUsesChronometer(true)
                .setCustomContentView(getSmallContentView())
                .setCustomBigContentView(getBigContentView())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true);
        return notificationBuilder;
    }


    private void updateRemoteViews(RemoteViews remoteView) {
        if (remoteView != null) {
            remoteView.setTextViewText(R.id.text_view_name, mCurrentEcgNotificationInfo.title);
            remoteView.setTextViewText(R.id.text_view_description, mCurrentEcgNotificationInfo.description);
            int iconPlayPause = mCurrentEcgNotificationInfo.state == EcgNotificationInfo.STATE_RECORDING ? R.drawable.ic_notification_pause : R.drawable.ic_notification_start;
            remoteView.setImageViewResource(R.id.image_view_play_start, iconPlayPause);
            remoteView.setImageViewResource(R.id.image_view_logo, R.drawable.ic_v_launcher);
        }
    }

    public void setUpRemoteView(RemoteViews remoteView) {
        // Play pause intent
        NotificationIntentModel playPauseIntentModel = mIntentHandler.providePlayPauseIntentHandler();
        remoteView.setImageViewResource(playPauseIntentModel.imageViewId, playPauseIntentModel.drawableResourceId);
        remoteView.setOnClickPendingIntent(playPauseIntentModel.clickableButtonId, playPauseIntentModel.pendingIntent);
        // Play pause intent
        NotificationIntentModel stopIntentModel = mIntentHandler.provideStopIntentHandler();
        remoteView.setImageViewResource(stopIntentModel.imageViewId, stopIntentModel.drawableResourceId);
        remoteView.setOnClickPendingIntent(stopIntentModel.clickableButtonId, stopIntentModel.pendingIntent);
        // Play pause intent
        NotificationIntentModel closeIntentModel = mIntentHandler.provideCloseIntentHandler();
        remoteView.setImageViewResource(closeIntentModel.imageViewId, closeIntentModel.drawableResourceId);
        remoteView.setOnClickPendingIntent(closeIntentModel.clickableButtonId, closeIntentModel.pendingIntent);


    }
}
