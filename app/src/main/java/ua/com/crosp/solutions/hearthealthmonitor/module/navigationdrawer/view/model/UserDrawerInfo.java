package ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.view.model;

import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;

/**
 * Created by Alexander Molochko on 10/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserDrawerInfo {
    public String firstName;
    public String lastName;
    public Integer age;
    public Long personalId;
    public String gender;

    public UserDrawerInfo() {
        firstName = "";
        lastName = "";
        age = 20;
        personalId = (long) -1;
        gender = "female";
    }

    public static UserDrawerInfo fromEntity(UserEntity userEntity) {
        try {
            UserDrawerInfo userDrawerInfo = new UserDrawerInfo();
            userDrawerInfo.gender = userEntity.getGender().getGenderString();
            userDrawerInfo.firstName = userEntity.getFullName().getFirstName();
            userDrawerInfo.lastName = userEntity.getFullName().getLastName();
            userDrawerInfo.personalId = userEntity.getPersonalId().getId();
            userDrawerInfo.age = userEntity.getDateOfBirth().getAge();
            return userDrawerInfo;
        } catch (Exception ex) {
            return new UserDrawerInfo();
        }
    }
}
