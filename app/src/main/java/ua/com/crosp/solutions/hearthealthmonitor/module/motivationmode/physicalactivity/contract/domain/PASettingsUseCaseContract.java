package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContractInterface;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface PASettingsUseCaseContract extends BaseSingleUseCaseContractInterface<Void, PASettingsEntity> {
    public Single<PASettingsEntity> getSettings();

    public void saveSettings(PASettingsEntity paSettings);

}
