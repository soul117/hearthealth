package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;

/**
 * Created by Alexander Molochko on 10/31/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentGameResultViewModel {
    private static final long MILLIS_IN_SECOND = 1000L;
    public String gameName;
    public long gameEstimatedTime, gameActualTime;
    public String customJsonInfo;
    public String gameType;
    public boolean wasInterrupted;

    public ExperimentGameResultViewModel() {
    }

    public static ExperimentGameResultViewModel fromEntity(ExperimentResultEntity experimentResultEntity) {
        ExperimentGameResultViewModel experimentResultViewModel = new ExperimentGameResultViewModel();
        GameResultEntity gameResultEntity = experimentResultEntity.getGameResultEntity();
        experimentResultViewModel.gameName = gameResultEntity.getGameName();
        experimentResultViewModel.gameActualTime = gameResultEntity.getGameActualTime();
        experimentResultViewModel.gameEstimatedTime = gameResultEntity.getGameEstimatedTime();
        experimentResultViewModel.gameType = gameResultEntity.getGameType();
        experimentResultViewModel.customJsonInfo = gameResultEntity.getCustomJsonObject();

        return experimentResultViewModel;
    }

}
