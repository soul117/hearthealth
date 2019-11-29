package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsModel;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestSettingsM2EMapper extends BaseMapper<RestSettingsModel, RestSettingsEntity> {

    public RestSettingsM2EMapper() {

    }

    @Override
    public RestSettingsEntity transform(RestSettingsModel inputItem) {
        return new RestSettingsEntity(inputItem.getRestTime());
    }

}
