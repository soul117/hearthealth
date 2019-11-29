package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler;

import com.scichart.core.framework.ISuspendable;
import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

import ua.com.crosp.solutions.hearthealthmonitor.common.handler.LifecycleObserver;

/**
 * Created by Alexander Molochko on 12/2/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface EcgDataRendererConsumer extends LifecycleObserver {

    ISuspendable getSuspendable();

    boolean isStillRunning();

    long getTimeUpdateIntervalInMs();
}
