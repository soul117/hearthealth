package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.GameResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.GameResultE2MMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GameResultE2MMapper extends GameResultE2MMapperContract {

    @Override
    public GameResultModel transform(GameResultEntity inputItem) {
        GameResultModel gameResultModel = new GameResultModel();
        gameResultModel.customJsonObject = inputItem.getCustomJsonObject();
        gameResultModel.gameActualTime = inputItem.getGameActualTime();
        gameResultModel.gameEstimatedTime = inputItem.getGameEstimatedTime();
        gameResultModel.gameExperimentId = inputItem.getGameExperimentId();
        gameResultModel.wasInterrupted = inputItem.isWasInterrupted();
        gameResultModel.gameName = inputItem.getGameName();
        gameResultModel.gameType = inputItem.getGameType();
        gameResultModel.id = inputItem.getEntityId().getId();
        return gameResultModel;
    }
}
