package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContractInterface;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface RestSettingsUseCaseContract extends BaseSingleUseCaseContractInterface<Void, RestSettingsEntity> {
    public Single<RestSettingsEntity> getRestModeSettings();

    public void saveRestModeSettings(RestSettingsEntity settingsEntity);

}
