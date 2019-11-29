package ua.com.crosp.solutions.hearthealthmonitor.base.view;

import android.widget.RemoteViews;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface BaseNotificationView {

    public RemoteViews getSmallContentView();

    public RemoteViews getBigContentView();


}
