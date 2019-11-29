package ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;

/**
 * Created by Alexander Molochko on 10/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BaseUseCase {
    protected final ExecutionThread mThreadExecutor;
    protected final PostExecutionThread mPostExecutionThread;

    public BaseUseCase(ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        this.mThreadExecutor = threadExecutor;
        this.mPostExecutionThread = postExecutionThread;
    }
}
