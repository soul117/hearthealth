package ua.com.crosp.solutions.hearthealthmonitor.di.contract;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

/**
 * The component provide class for DI
 *
 * @param <C> required component class
 */
public interface ProvidesBaseComponent<C> {
    C getBaseActivityComponent();
}
