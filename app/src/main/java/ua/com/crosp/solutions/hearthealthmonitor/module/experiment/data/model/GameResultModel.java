package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GameResultModel extends RealmObject {
    @PrimaryKey
    public Long id;
    public long gameActualTime;
    public long gameEstimatedTime;
    public long gameExperimentId;
    public String customJsonObject;
    public boolean wasInterrupted;

    public String gameName;
    public String gameType;
}
