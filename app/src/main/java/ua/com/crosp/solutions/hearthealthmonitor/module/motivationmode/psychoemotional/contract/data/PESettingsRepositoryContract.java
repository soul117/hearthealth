package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

/**
 * Interface that represents a Repository for getting {@link ModeItem} related data.
 */
public interface PESettingsRepositoryContract {
    Single<PESettingsEntity> getSettings();

    PESettingsEntity getSettingsSync();

    void saveSettings(PESettingsEntity settingsEntity);

}
