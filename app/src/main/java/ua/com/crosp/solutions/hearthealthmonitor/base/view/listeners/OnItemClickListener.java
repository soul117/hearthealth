package ua.com.crosp.solutions.hearthealthmonitor.base.view.listeners;

import android.view.View;

/**
 * Created by Alexander Molochko on 1/16/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface OnItemClickListener<T> {
    void onItemClick(T item, View inView, int onPosition);
    void onItemLongClick(T item, View inView, int onPosition);
}
