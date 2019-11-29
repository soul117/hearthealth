package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.threading;

/**
 * Created by Alexander Molochko on 10/11/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ThreadExecutionContext {
    void executeInThreadContext(Runnable runnable);
}
