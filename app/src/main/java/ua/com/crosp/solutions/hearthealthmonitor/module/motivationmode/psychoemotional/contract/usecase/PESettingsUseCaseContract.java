package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContractInterface;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface PESettingsUseCaseContract extends BaseSingleUseCaseContractInterface<Void, PESettingsEntity> {
    public Single<PESettingsEntity> getSettings();

    public void saveSettings(PESettingsEntity paSettings);
}
