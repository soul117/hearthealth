package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.repository;

import javax.inject.Named;

import io.reactivex.Single;
import io.realm.Realm;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.database.BaseRealmRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.entity.UserSettingsEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSettingsRepository extends BaseRealmRepository implements UserSettingsRepositoryContract {
    private final Realm mRealmDb;
    // Variables
    private UserSettingsE2MMapperContract mE2MMapperContract;
    private UserSettingsM2EMapperContract mM2EMapperContract;

    public UserSettingsRepository(Realm realm, UserSettingsE2MMapperContract e2MMapperContract, UserSettingsM2EMapperContract m2EMapperContract, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread) {
        super(realmThread);
        mE2MMapperContract = e2MMapperContract;
        mRealmDb = realm;
        mM2EMapperContract = m2EMapperContract;
    }

    @Override
    public Single<UserSettingsEntity> getUserSettings(long userId) {
        return null;
    }

    @Override
    public void saveSettings(UserSettingsEntity userSettingsEntity) {

    }


}
