package ua.com.crosp.solutions.hearthealthmonitor.di.contract;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ProvidesMultipleComponents {

    <C> C getComponentByType(Class<C> componentType);

}
