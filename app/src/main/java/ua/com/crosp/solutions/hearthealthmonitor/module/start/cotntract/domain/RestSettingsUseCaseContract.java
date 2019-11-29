package ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContractInterface;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.entity.UserSettingsEntity;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface RestSettingsUseCaseContract extends BaseSingleUseCaseContractInterface<Void, UserSettingsEntity> {
    public Single<UserSettingsEntity> getRestModeSettings();

    public void saveRestModeSettings(UserSettingsEntity settingsEntity);

}
