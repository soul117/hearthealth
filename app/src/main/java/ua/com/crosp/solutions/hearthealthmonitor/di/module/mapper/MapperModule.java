package ua.com.crosp.solutions.hearthealthmonitor.di.module.mapper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.mapper.MapperConstants;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.mapper.DoctorE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.mapper.DoctorM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper.EcgResultE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper.EcgResultM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper.ExperimentResultE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper.ExperimentResultM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper.FeelingResultE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper.FeelingResultM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper.GameResultE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper.GameResultM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.EcgResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.EcgResultM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.ExperimentResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.ExperimentResultM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.FeelingResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.FeelingResultM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.GameResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.GameResultM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.mappers.ModeItemMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.mapper.PASettingsE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.mapper.PASettingsM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PESettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.mapper.PESettingsE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.mapper.PESettingsM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.mapper.PESettingsM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.mapper.RestSettingsE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.mapper.RestSettingsM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.mapper.UserSessionE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.mapper.UserSessionM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.data.mapper.UserE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.data.mapper.UserM2EMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.mapper.UserSettingsE2MMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.mapper.UserSettingsM2EMapper;

/**
 * Created by Alexander Molochko on 10/16/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerApplication
@Module
public class MapperModule {
    @Provides
    @PerApplication
    @Named(MapperConstants.PE_SETTINGS_E2M)
    BaseMapper<PESettingsEntity, PESettingsModel> provideSettingsE2MMapper() {
        return new PESettingsE2MMapper();
    }

    @Provides
    @PerApplication
    @Named(MapperConstants.EXPERIMENT_MODE)
    BaseMapper<ModeItem, ModeListItem> provideModeMapper() {
        return new ModeItemMapper();
    }

    @Provides
    @PerApplication
    @Named(MapperConstants.PA_SETTINGS_E2M)
    BaseMapper<PASettingsEntity, PASettingsModel> providePASettingsE2MMapper() {
        return new PASettingsE2MMapper();
    }


    @Provides
    @PerApplication
    UserSettingsE2MMapperContract provideUserSettingsE2MMapper(DoctorE2MMapperContract e2MMapperContract) {
        return new UserSettingsE2MMapper(e2MMapperContract);
    }

    @Provides
    @PerApplication
    UserSettingsM2EMapperContract provideUserSettingsM2EMapper(DoctorM2EMapperContract doctorM2EMapper) {
        return new UserSettingsM2EMapper(doctorM2EMapper);
    }

    @Provides
    @PerApplication
    @Named(MapperConstants.REST_SETTINGS_E2M)
    BaseMapper<RestSettingsEntity, RestSettingsModel> provideRestSettingsE2MMapper() {
        return new RestSettingsE2MMapper();
    }

    @Provides
    @PerApplication
    @Named(MapperConstants.REST_SETTINGS_M2E)
    BaseMapper<RestSettingsModel, RestSettingsEntity> provideRestSettingsM2EMapper() {
        return new RestSettingsM2EMapper();
    }

    @Provides
    @PerApplication
    UserSessionE2MMapperContract provideUserSessionE2MMapper(UserE2MMapperContract userE2MMapperContract) {
        return new UserSessionE2MMapper(userE2MMapperContract);
    }

    @Provides
    @PerApplication
    UserE2MMapperContract provideUserE2MMapper(UserSettingsE2MMapperContract userSettingsMapper) {
        return new UserE2MMapper(userSettingsMapper);
    }

    @Provides
    @PerApplication
    DoctorM2EMapperContract provideDoctorM2EMapper() {
        return new DoctorM2EMapper();
    }


    @Provides
    @PerApplication
    UserM2EMapperContract provideUserM2EMapper(UserSettingsM2EMapperContract settingsMapper) {
        return new UserM2EMapper(settingsMapper);
    }

    @Provides
    @PerApplication
    DoctorE2MMapperContract provideDoctorE2MMapper() {
        return new DoctorE2MMapper();
    }


    @Provides
    @PerApplication
    UserSessionM2EMapperContract provideUserSessionM2EMapper(UserM2EMapperContract m2EMapperContract) {
        return new UserSessionM2EMapper(m2EMapperContract);
    }

    @Provides
    @PerApplication
    EcgResultM2EMapperContract provideEcgResultM2EMapper() {
        return new EcgResultM2EMapper();
    }


    @Provides
    @PerApplication
    EcgResultE2MMapperContract provideEcgResultE2MMapper() {
        return new EcgResultE2MMapper();
    }

    @Provides
    @PerApplication
    GameResultE2MMapperContract provideGameResultE2MMapper() {
        return new GameResultE2MMapper();
    }

    @Provides
    @PerApplication
    GameResultM2EMapperContract provideGameResultM2EMapper() {
        return new GameResultM2EMapper();
    }

    @Provides
    @PerApplication
    FeelingResultM2EMapperContract providFeelingResultM2EMapper() {
        return new FeelingResultM2EMapper();
    }

    @Provides
    @PerApplication
    FeelingResultE2MMapperContract provideFeelingResultE2MMapper() {
        return new FeelingResultE2MMapper();
    }


    @Provides
    @PerApplication
    ExperimentResultE2MMapperContract provideExperimentResultE2MMapper(GameResultE2MMapperContract gameMapper, FeelingResultE2MMapperContract feelingMapper, EcgResultE2MMapperContract ecgMapper) {
        return new ExperimentResultE2MMapper(gameMapper, feelingMapper, ecgMapper);
    }

    @Provides
    @PerApplication
    ExperimentResultM2EMapperContract provideExperimentResultM2EMapper(EcgResultM2EMapperContract ecgMapper, FeelingResultM2EMapperContract feelingMapper, GameResultM2EMapperContract gameMapper) {
        return new ExperimentResultM2EMapper(gameMapper, feelingMapper, ecgMapper);
    }

    @Provides
    @PerApplication
    @Named(MapperConstants.PA_SETTINGS_M2E)
    BaseMapper<PASettingsModel, PASettingsEntity> providePASettingsM2EMapper() {
        return new PASettingsM2EMapper();
    }

    @Provides
    @PerApplication
    @Named(MapperConstants.PE_SETTINGS_M2E)
    PESettingsM2EMapperContract provideSettingsM2EMapper() {
        return new PESettingsM2EMapper();
    }
}
