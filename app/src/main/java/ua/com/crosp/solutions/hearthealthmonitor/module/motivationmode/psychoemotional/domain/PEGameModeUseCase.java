package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PEGameModeRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase.PEGameModeUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PEBallGameMode;

/**
 * Created by Alexander Molochko on 4/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PEGameModeUseCase implements PEGameModeUseCaseContract {
    private PEGameModeRepositoryContract mSettingsRepository;


    public PEGameModeUseCase(PEGameModeRepositoryContract settingsRepository) {
        mSettingsRepository = settingsRepository;
    }

    @Override
    public Single<PEBallGameMode.List> getAvailableGameModes() {
        return mSettingsRepository.getAvailableGameModes();
    }

}

