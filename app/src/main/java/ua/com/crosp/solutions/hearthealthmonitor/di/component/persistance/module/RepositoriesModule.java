package ua.com.crosp.solutions.hearthealthmonitor.di.component.persistance.module;

import android.content.res.Resources;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.mapper.MapperConstants;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.data.AppSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.repository.DoctorRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.ExperimentResultsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.ExperimentResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.ExperimentResultM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.data.ExperimentResultsRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.data.ModesRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.ModesRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.data.PASettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PEGameModeRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PESettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PESettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PsychoEmotionalSettingsRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.mapper.PESettingsM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.data.RestSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.mapper.RestSettingsE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.mapper.RestSettingsM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.repository.UserSessionRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.repository.UserSessionSettingsRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.data.repository.UserRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.repository.UserSettingsRepository;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerApplication
@Module
public class RepositoriesModule {

    @Provides
    @PerApplication
    ModesRepositoryContract provideModesRepository(Resources resources) {
        return new ModesRepository(resources);
    }

    @Provides
    @PerApplication
    PEGameModeRepositoryContract provideGameModeRepository(Resources resources, SettingsManager settingsManager,
                                                           @Named(MapperConstants.PE_SETTINGS_M2E) PESettingsM2EMapperContract settingsMapper,
                                                           @Named(MapperConstants.PE_SETTINGS_E2M) BaseMapper<PESettingsEntity, PESettingsModel> reverseMapper) {
        return (PEGameModeRepositoryContract) provideSettingsRepository(settingsManager, resources, settingsMapper, reverseMapper);
    }


    @Provides
    @PerApplication
    PESettingsRepositoryContract provideSettingsRepository(SettingsManager settingsManager, Resources resources,
                                                           @Named(MapperConstants.PE_SETTINGS_M2E) PESettingsM2EMapperContract settingsMapper,
                                                           @Named(MapperConstants.PE_SETTINGS_E2M) BaseMapper<PESettingsEntity, PESettingsModel> reverseMapper) {
        return new PsychoEmotionalSettingsRepository(resources, settingsManager, settingsMapper, reverseMapper);
    }

    @Provides
    @PerApplication
    PASettingsRepositoryContract providePASettingsRepository(SettingsManager settingsManager,
                                                             @Named(MapperConstants.PA_SETTINGS_M2E) BaseMapper<PASettingsModel, PASettingsEntity> settingsMapper,
                                                             @Named(MapperConstants.PA_SETTINGS_E2M) BaseMapper<PASettingsEntity, PASettingsModel> reverseMapper) {
        return new PASettingsRepository(settingsManager, settingsMapper, reverseMapper);
    }

    @Provides
    @PerApplication
    RestSettingsRepositoryContract provideRestRepository(SettingsManager settingsManager,
                                                         @Named(MapperConstants.REST_SETTINGS_M2E) BaseMapper<RestSettingsModel, RestSettingsEntity> settingsMapper,
                                                         @Named(MapperConstants.REST_SETTINGS_E2M) BaseMapper<RestSettingsEntity, RestSettingsModel> reverseMapper) {
        return new RestSettingsRepository(settingsManager, settingsMapper, reverseMapper);
    }


    @Provides
    @PerApplication
    DoctorsRepositoryContract provideDoctorsRepository(Realm realm,
                                                       DoctorE2MMapperContract e2mMapper, DoctorM2EMapperContract m2eMapper, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread) {
        return new DoctorRepository(realm, e2mMapper, m2eMapper, realmThread);
    }

    @Provides
    @PerApplication
    UserRepositoryContract provideUserRepositoryContract(Realm realmDb, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread, UserM2EMapperContract m2eMapper, UserE2MMapperContract e2mMapper) {
        return new UserRepository(realmDb, realmThread, e2mMapper, m2eMapper);
    }

    @Provides
    @PerApplication
    UserSessionSettingsRepositoryContract provideUserSessionSettingsRepositoryContract(SettingsManager settingsManager) {
        return new UserSessionSettingsRepository(settingsManager);
    }

    @Provides
    @PerApplication
    UserSessionRepositoryContract provideUserSessionRepository(Realm realmDb, UserSessionM2EMapperContract sessionM2EMapperContract, UserSessionE2MMapperContract sessionE2MMapperContract, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread) {
        return new UserSessionRepository(realmDb, sessionE2MMapperContract, sessionM2EMapperContract, realmThread);
    }

    @Provides
    @PerApplication
    AppSettingsRepositoryContract provAppSettingsRepository(SettingsManager settingsManager) {
        return new AppSettingsRepository(settingsManager);
    }

    @Provides
    @PerApplication
    UserSettingsRepositoryContract provideUserSettingsRepository(Realm realmDb, UserSettingsE2MMapperContract e2mMapper, UserSettingsM2EMapperContract m2eMapper, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread) {
        return new UserSettingsRepository(realmDb, e2mMapper, m2eMapper, realmThread);
    }

    @Provides
    @PerApplication
    ExperimentResultsRepositoryContract provideExperimentResultsRepository(@Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread, Realm realmDb, ExperimentResultE2MMapperContract e2mMapper, ExperimentResultM2EMapperContract m2eMapper) {
        return new ExperimentResultsRepository(realmThread, m2eMapper, e2mMapper, realmDb);
    }
}
