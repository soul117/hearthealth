package ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.model.Entity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.DateOfBirth;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Email;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FullName;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Gender;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.PhoneNumber;
import ua.com.crosp.solutions.hearthealthmonitor.common.exception.domain.ShouldHaveAtLeastOneDoctorException;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.entity.UserSettingsEntity;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserEntity extends Entity {
    public static final UserEntity INVALID = createNullableEntity();
    private FullName mFullName = new FullName();
    private Gender mGender = new Gender();
    private String mAbout = "";
    private Email mEmail = new Email();
    private PhoneNumber mPhoneNumber = new PhoneNumber();
    private DateOfBirth mDateOfBirth = new DateOfBirth();
    private UserSettingsEntity mUserSettingsEntity;

    public Gender getGender() {
        return mGender;
    }

    public void setGender(@Gender.HumanGender String gender) {
        mGender.setGenderString(gender);
    }

    public DateOfBirth getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth.setDate(dateOfBirth);
    }

    public UserSettingsEntity getUserSettingsEntity() {
        return mUserSettingsEntity;
    }

    public void setUserSettingsEntity(UserSettingsEntity userSettingsEntity) {
        mUserSettingsEntity = userSettingsEntity;
    }

    public FullName getFullName() {
        return mFullName;
    }

    public void setFullName(FullName fullName) {
        mFullName = fullName;
    }

    public void setFullName(String firstName, String lastName) {
        mFullName.setFirstName(firstName);
        mFullName.setLastName(lastName);
    }


    public void setFirstName(String firstName) {
        mFullName.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        mFullName.setLastName(lastName);
    }

    public EntityId getPersonalId() {
        return mUserSettingsEntity.getPersonalId();
    }

    public DoctorEntity.List getUserDoctors() {
        return mUserSettingsEntity.getDoctors();
    }

    public void setDoctors(DoctorEntity.List doctorEntities) {
        if (null == doctorEntities) {
            throw new ShouldHaveAtLeastOneDoctorException("A User should have at least one associated doctor");
        }
        mUserSettingsEntity.setDoctors(doctorEntities);
    }

    public void setEmail(String email) {
        mEmail.setEmailString(email);
    }

    public String getEmail() {
        return mEmail.getEmailString();
    }

    public String getAbout() {
        return mAbout;
    }

    public void setAbout(String about) {
        mAbout = about;
    }

    public static final UserEntity createNullableEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEntityId(EntityId.INVALID_ENTITY_ID);
        userEntity.setFullName(FullName.ANONYMOUS);
        userEntity.setPhoneNumber(PhoneNumber.createDefault());
        userEntity.setUserSettingsEntity(UserSettingsEntity.INVALID);
        return userEntity;
    }

    private void setPhoneNumber(PhoneNumber phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void setPersonalId(Long personalId) {
        mUserSettingsEntity.setPersonalId(personalId);
    }

    public void setGenderFromString(String genderFromString) {
        if (genderFromString.equalsIgnoreCase(Gender.GENDER_FEMALE)) {
            setGender(Gender.GENDER_FEMALE);
        } else if (genderFromString.equalsIgnoreCase(Gender.GENDER_MALE)) {
            setGender(Gender.GENDER_MALE);
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber.setPhoneNumber(phoneNumber);
    }

    public PhoneNumber getPhoneNumber() {
        return mPhoneNumber;
    }

    public static class List extends ArrayList<UserEntity> {
        public List() {
        }

        public List(@NonNull Collection<? extends UserEntity> c) {
            super(c);
        }
    }
}
