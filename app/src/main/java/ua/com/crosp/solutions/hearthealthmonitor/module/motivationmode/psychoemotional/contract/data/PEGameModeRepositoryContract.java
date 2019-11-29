package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PEBallGameMode;

/**
 * Created by Alexander Molochko on 10/2/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface PEGameModeRepositoryContract {
    Single<PEBallGameMode.List> getAvailableGameModes();

    Single<PEBallGameMode> getGameModeById(long id);
}
