package ua.com.crosp.solutions.hearthealthmonitor.common.model;

import android.app.Service;
import android.os.Bundle;

/**
 * Created by Alexander Molochko on 5/25/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class StartServiceBundle {
    public Class<? extends Service> serviceClass;
    public Bundle serviceArguments;

    public StartServiceBundle(Class<? extends Service> serviceClass) {
        this(serviceClass, Bundle.EMPTY);
    }

    public StartServiceBundle(Class<? extends Service> serviceClass, Bundle serviceArguments) {
        this.serviceClass = serviceClass;
        this.serviceArguments = serviceArguments;
    }
}
