package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgResultModel extends RealmObject {
    @PrimaryKey
    public Long id;
    public String rawRecordingPath;
    public String pdfEcgRecordingPath;
    public String xmlRecordingPath;
    public Date dateRecordingStart;
    public Date dateRecordingStop;
    public long estimatedRecordingTimeInSeconds;
    public boolean wasInterrupted;
    public RealmList<Long> rrIntervalValues;
}
