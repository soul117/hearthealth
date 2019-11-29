package ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Gender;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.model.UserSettingsModel;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserModel extends RealmObject {
    @PrimaryKey
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String about;
    private String phone;
    private String email;
    private String gender;
    private UserSettingsModel userSettingsModel;

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Gender.HumanGender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserSettingsModel getUserSettingsModel() {
        return userSettingsModel;
    }

    public void setUserSettingsModel(UserSettingsModel userSettingsModel) {
        this.userSettingsModel = userSettingsModel;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class ModelList extends ArrayList<UserModel> {

        public ModelList() {
        }

        public ModelList(@NonNull Collection<? extends UserModel> c) {
            super(c);
        }

    }
}
