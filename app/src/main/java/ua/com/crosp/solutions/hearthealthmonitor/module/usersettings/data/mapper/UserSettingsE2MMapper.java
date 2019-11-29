package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.model.UserSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.entity.UserSettingsEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSettingsE2MMapper extends UserSettingsE2MMapperContract {
    private DoctorE2MMapperContract mE2MMapperContract;

    public UserSettingsE2MMapper(DoctorE2MMapperContract e2MMapperContract) {
        mE2MMapperContract = e2MMapperContract;
    }

    @Override
    public UserSettingsModel transform(UserSettingsEntity inputItem) {
        UserSettingsModel userSettingsModel = new UserSettingsModel();
        userSettingsModel.setDoctors(mE2MMapperContract.transform(inputItem.getDoctors()));
        userSettingsModel.setId(inputItem.getEntityId().getId());
        userSettingsModel.setUserPersonalId(inputItem.getPersonalId().getId());
        return userSettingsModel;
    }

}
