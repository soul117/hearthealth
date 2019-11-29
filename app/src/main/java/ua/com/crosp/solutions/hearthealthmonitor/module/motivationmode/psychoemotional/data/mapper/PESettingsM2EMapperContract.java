package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PEBallGameMode;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PESettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

/**
 * Created by Alexander Molochko on 10/3/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public abstract class PESettingsM2EMapperContract extends BaseMapper<PESettingsModel, PESettingsEntity> {

    public abstract void setFullInfoProvider(FullGameModeInfoProvider gameModeInfoProvider);

    public interface FullGameModeInfoProvider {
        public PEBallGameMode getFullGameModeInfoById(long id);
    }
}
