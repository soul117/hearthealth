package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.GameResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.GameResultM2EMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GameResultM2EMapper extends GameResultM2EMapperContract {


    @Override
    public GameResultEntity transform(GameResultModel inputItem) {
        GameResultEntity gameResultEntity = new GameResultEntity();
        gameResultEntity.setEntityId(inputItem.id);
        gameResultEntity.setGameName(inputItem.gameName);
        gameResultEntity.setGameType(inputItem.gameType);
        gameResultEntity.setGameExperimentId(inputItem.gameExperimentId);
        gameResultEntity.setWasInterrupted(inputItem.wasInterrupted);
        gameResultEntity.setCustomJsonObject(inputItem.customJsonObject);
        gameResultEntity.setGameActualTime(inputItem.gameActualTime);
        gameResultEntity.setGameEstimatedTime(inputItem.gameEstimatedTime);
        return gameResultEntity;
    }
}
