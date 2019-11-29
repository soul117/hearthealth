package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface NotificationManager {
    public void showNotification(String title, String description);

    public void showNotification(String title);

}
