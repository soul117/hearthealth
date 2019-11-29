package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FeelingResultModel extends RealmObject {
    @PrimaryKey
    public Long id;
    public String feelingType;
    public String weatherType;
    public String comment;

}
