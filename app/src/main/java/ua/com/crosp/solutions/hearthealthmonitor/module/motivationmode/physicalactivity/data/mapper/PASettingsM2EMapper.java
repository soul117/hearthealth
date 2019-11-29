package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsModel;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PASettingsM2EMapper extends BaseMapper<PASettingsModel, PASettingsEntity> {

    public PASettingsM2EMapper() {

    }

    @Override
    public PASettingsEntity transform(PASettingsModel inputItem) {
        return new PASettingsEntity(inputItem.getExperimentTime(), inputItem.getSquatCount(), inputItem.isSoundEnabled());
    }

}
