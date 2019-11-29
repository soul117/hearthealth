package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel;

import java.util.Date;

import io.realm.annotations.PrimaryKey;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;

/**
 * Created by Alexander Molochko on 10/25/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSettingsViewModel {
    @PrimaryKey
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String about;
    private String phone;
    private String email;
    private String gender;
    private Long userPersonalId;
    private DoctorViewModel.List doctors;

    public static UserSettingsViewModel createFromUserEntity(UserEntity userEntity) {
        UserSettingsViewModel viewModel = new UserSettingsViewModel();
        viewModel.setLastName(userEntity.getFullName().getLastName());
        viewModel.setFirstName(userEntity.getFullName().getFirstName());
        viewModel.setAbout(userEntity.getAbout());
        viewModel.setDateOfBirth(userEntity.getDateOfBirth().getDate());
        viewModel.setDoctors(DoctorViewModel.List.fromDoctorEntitiesList(userEntity.getUserDoctors()));
        viewModel.setEmail(userEntity.getEmail());
        viewModel.setGender(userEntity.getGender().getGenderString());
        viewModel.setPhone(userEntity.getPhoneNumber().getPhoneNumberString());
        viewModel.setId(userEntity.getEntityId().getId());
        viewModel.setUserPersonalId(userEntity.getPersonalId().getId());
        return viewModel;
    }

    public Long getId() {
        return id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getUserPersonalId() {
        return userPersonalId;
    }

    public void setUserPersonalId(Long userPersonalId) {
        this.userPersonalId = userPersonalId;
    }

    public DoctorViewModel.List getDoctors() {
        return doctors;
    }

    public void setDoctors(DoctorViewModel.List doctors) {
        this.doctors = doctors;
    }

    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
