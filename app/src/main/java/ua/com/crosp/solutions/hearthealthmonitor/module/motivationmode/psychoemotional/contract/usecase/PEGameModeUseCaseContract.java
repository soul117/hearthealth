package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PEBallGameMode;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface PEGameModeUseCaseContract {
    public Single<PEBallGameMode.List> getAvailableGameModes();
}
