package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.model.UserSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.entity.UserSettingsEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSettingsM2EMapper extends UserSettingsM2EMapperContract {
    private DoctorM2EMapperContract mDoctorM2EMapper;

    public UserSettingsM2EMapper(DoctorM2EMapperContract doctorM2EMapper) {
        mDoctorM2EMapper = doctorM2EMapper;
    }


    @Override
    public UserSettingsEntity transform(UserSettingsModel inputItem) {
        UserSettingsEntity userSessionEntity = new UserSettingsEntity();
        userSessionEntity.setPersonalId(inputItem.getUserPersonalId());
        userSessionEntity.setEntityId(inputItem.getId());
        userSessionEntity.setDoctors(new DoctorEntity.List(mDoctorM2EMapper.transform(inputItem.getDoctors())));
        return userSessionEntity;
    }

}
