package ua.com.crosp.solutions.hearthealthmonitor.module.userlist.data.mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model.UserModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.data.UserSettingsM2EMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserM2EMapper extends UserM2EMapperContract {

    private UserSettingsM2EMapperContract mE2MMapperContract;

    public UserM2EMapper(UserSettingsM2EMapperContract e2MMapperContract) {
        mE2MMapperContract = e2MMapperContract;
    }

    @Override
    public UserEntity transform(UserModel inputItem) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(inputItem.getFirstName(), inputItem.getLastName());
        userEntity.setEmail(inputItem.getEmail());
        userEntity.setEntityId(new EntityId(inputItem.getId()));
        userEntity.setGender(inputItem.getGender());
        userEntity.setAbout(inputItem.getAbout());
        userEntity.setPhoneNumber(inputItem.getPhone());
        userEntity.setDateOfBirth(inputItem.getDateOfBirth());
        userEntity.setUserSettingsEntity(mE2MMapperContract.transform(inputItem.getUserSettingsModel()));
        return userEntity;
    }

    @Override
    public List<UserEntity> transform(List<UserModel> inputCollection) {
        List<UserEntity> transformedItems = new ArrayList<>();
        for (int i = 0; i < inputCollection.size(); i++) {
            transformedItems.add(this.transform(inputCollection.get(i)));
        }
        return transformedItems;
    }

    @Override
    public UserEntity apply(@NonNull UserModel userModel) throws Exception {
        return transform(userModel);
    }
}
