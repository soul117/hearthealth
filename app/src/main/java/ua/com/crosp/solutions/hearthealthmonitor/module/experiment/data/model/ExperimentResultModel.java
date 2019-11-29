package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentResultModel extends RealmObject {
    @PrimaryKey
    public Long id;
    public Long userId;
    public Date dateCreated;
    public String name;
    public FeelingResultModel feelingResult;
    public GameResultModel gameResultModel;
    public EcgResultModel ecgResultModel;
    public String basePath;

    public static class ModelList extends ArrayList<ExperimentResultModel> {
        public ModelList() {
        }

        public ModelList(@NonNull Collection<? extends ExperimentResultModel> c) {
            super(c);
        }
    }
}
