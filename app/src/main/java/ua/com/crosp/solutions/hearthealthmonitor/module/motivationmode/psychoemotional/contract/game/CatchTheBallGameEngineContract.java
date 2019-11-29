package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameEngineController;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;

/**
 * Created by Alexander Molochko on 8/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface CatchTheBallGameEngineContract extends GameEngineController {

    void updateGameState(long gameTime, long timeElapsed);

    void initConfiguration(GameScreen gameScreen);

    Single<GameResultEntity> stopExperimentAndGetResults();

}
