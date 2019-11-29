package ua.com.crosp.solutions.hearthealthmonitor.base.presentation;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface BackPressNotifier {
    public PublishSubject<Boolean> subscribeToBackPressEvents();
}