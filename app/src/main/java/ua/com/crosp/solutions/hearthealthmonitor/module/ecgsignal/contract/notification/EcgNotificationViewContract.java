package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification;

import android.app.Notification;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseNotificationView;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.data.EcgNotificationInfo;

/**
 * Created by Alexander Molochko on 5/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface EcgNotificationViewContract extends BaseNotificationView {
    public Notification buildNotification(EcgNotificationInfo ecgNotificationInfo);

    public Notification updateNotification(EcgNotificationInfo ecgNotificationInfo);

    Notification buildNotificationForNewApi(EcgNotificationInfo ecgNotificationInfo, String notificationChannelId);
}
