package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PESettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PESettingsM2EMapper extends PESettingsM2EMapperContract {

    private PESettingsM2EMapperContract.FullGameModeInfoProvider mFullGameModeInfoProvider;

    public PESettingsM2EMapper() {

    }

    @Override
    public PESettingsEntity transform(PESettingsModel inputItem) {
        return new PESettingsEntity(mFullGameModeInfoProvider.getFullGameModeInfoById(inputItem.getGameModeId()), inputItem.getGameTime());
    }

    @Override
    public void setFullInfoProvider(FullGameModeInfoProvider gameModeInfoProvider) {
        this.mFullGameModeInfoProvider = gameModeInfoProvider;
    }


}
