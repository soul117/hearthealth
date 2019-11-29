package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.receivers.HeadphonesPlugNotifier;

/**
 * Created by Alexander Molochko on 11/7/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface HeadphonesPlugNotifierProvider {
    public HeadphonesPlugNotifier provideHeadphonesPlugNotifier();

    void unsubscribeFromHeadphonePlugEvents();
}
