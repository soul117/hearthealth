package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsModel;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestSettingsE2MMapper extends BaseMapper<RestSettingsEntity, RestSettingsModel> {

    public RestSettingsE2MMapper() {
    }

    @Override
    public RestSettingsModel transform(RestSettingsEntity inputItem) {
        return new RestSettingsModel(inputItem.getRestTime());
    }
}
