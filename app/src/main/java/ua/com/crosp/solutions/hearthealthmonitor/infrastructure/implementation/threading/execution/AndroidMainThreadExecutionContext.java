package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution;

import android.content.Context;
import android.os.Handler;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.threading.ThreadExecutionContext;

/**
 * Created by Alexander Molochko on 10/11/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AndroidMainThreadExecutionContext implements ThreadExecutionContext {
    private Handler mHandler;

    public AndroidMainThreadExecutionContext(Context context) {
        mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void executeInThreadContext(Runnable runnable) {
        mHandler.post(runnable);
    }
}
