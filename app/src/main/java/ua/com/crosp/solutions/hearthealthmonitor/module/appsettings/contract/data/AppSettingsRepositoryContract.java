package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;

/**
 * Interface that represents a Repository for getting {@link ModeItem} related data.
 */
public interface AppSettingsRepositoryContract {

    Single<Void> saveAppSettings(AppSettingsModel appSettingsModel);

    Single<AppSettingsModel> getAppSettings();

}
