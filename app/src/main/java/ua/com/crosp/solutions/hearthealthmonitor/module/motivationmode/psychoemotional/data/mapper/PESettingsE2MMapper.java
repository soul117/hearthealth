package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PESettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PESettingsE2MMapper extends BaseMapper<PESettingsEntity, PESettingsModel> {

    public PESettingsE2MMapper() {
    }

    @Override
    public PESettingsModel transform(PESettingsEntity inputItem) {
        return new PESettingsModel(inputItem.getGameModeId(), inputItem.getExperimentTime());
    }

}
