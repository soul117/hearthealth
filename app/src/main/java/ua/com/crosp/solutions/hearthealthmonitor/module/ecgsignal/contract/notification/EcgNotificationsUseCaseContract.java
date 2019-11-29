package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification;

import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.data.EcgNotificationInfo;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface EcgNotificationsUseCaseContract {
    public void showNotification(String title, String description);

    public void showNotification(String title);

    public void showStreamingNotification(EcgNotificationInfo ecgNotificationInfo);
}
