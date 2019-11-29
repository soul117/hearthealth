package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;

/**
 * Interface that represents a Repository for getting {@link ModeItem} related data.
 */
public interface PASettingsRepositoryContract {
    Single<PASettingsEntity> getPASettings();

    void savePASettings(PASettingsEntity settingsModel);

    PASettingsEntity getSettingsSync();
}
