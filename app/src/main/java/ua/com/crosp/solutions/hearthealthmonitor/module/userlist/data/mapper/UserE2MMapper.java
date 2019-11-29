package ua.com.crosp.solutions.hearthealthmonitor.module.userlist.data.mapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model.UserModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsE2MMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserE2MMapper extends UserE2MMapperContract {

    private UserSettingsE2MMapperContract mE2MMapperContract;

    public UserE2MMapper(UserSettingsE2MMapperContract e2MMapperContract) {
        mE2MMapperContract = e2MMapperContract;
    }

    @Override
    public UserModel transform(UserEntity inputItem) {
        UserModel userModel = new UserModel();
        userModel.setDateOfBirth(inputItem.getDateOfBirth().getDate());
        userModel.setFirstName(inputItem.getFullName().getFirstName());
        userModel.setLastName(inputItem.getFullName().getLastName());
        userModel.setAbout(inputItem.getAbout());
        userModel.setId(inputItem.getEntityId().getId());
        userModel.setEmail(inputItem.getEmail());
        userModel.setPhone(inputItem.getPhoneNumber().getPhoneNumberString());
        userModel.setGender(inputItem.getGender().getGenderString());
        userModel.setUserSettingsModel(mE2MMapperContract.transform(inputItem.getUserSettingsEntity()));
        return userModel;
    }

    @Override
    public List<UserModel> transform(List<UserEntity> inputCollection) {
        return null;
    }

    @Override
    public UserModel apply(@NonNull UserEntity userEntity) throws Exception {
        return null;
    }
}
