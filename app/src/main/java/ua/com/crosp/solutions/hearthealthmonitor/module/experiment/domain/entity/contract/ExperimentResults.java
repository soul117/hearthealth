package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.contract;

/**
 * Created by Alexander Molochko on 10/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ExperimentResults {
    public long getEstimatedExperimentTime();

    public long getActualExperimentTime();

    public String getCustomPayload();

    public boolean wasInterrupted();
}
