package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.data.PASettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.domain.PASettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;

/**
 * Created by Alexander Molochko on 4/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PASettingsUseCase implements PASettingsUseCaseContract {
    private PASettingsRepositoryContract mPASettingsRepository;


    public PASettingsUseCase(PASettingsRepositoryContract paSettingsRepository) {
        mPASettingsRepository = paSettingsRepository;
    }

    @Override
    public Single<PASettingsEntity> getSettings() {
        return mPASettingsRepository.getPASettings();
    }

    @Override
    public void saveSettings(PASettingsEntity paSettings) {
        mPASettingsRepository.savePASettings(paSettings);
    }


    @Override
    public Single<PASettingsEntity> execute(Void parameters) {
        return null;
    }

    @Override
    public Single<PASettingsEntity> provideSingleObservable(Void aVoid) {
        return null;
    }
}
