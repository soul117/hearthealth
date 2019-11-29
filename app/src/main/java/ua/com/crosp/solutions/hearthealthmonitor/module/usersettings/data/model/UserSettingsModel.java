package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.data.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.model.DoctorModel;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSettingsModel extends RealmObject {
    @PrimaryKey
    private Long id;
    private Long userPersonalId;
    private RealmList<DoctorModel> doctors;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserPersonalId() {
        return userPersonalId;
    }

    public void setUserPersonalId(Long userPersonalId) {
        this.userPersonalId = userPersonalId;
    }

    public void setDoctors(RealmList<DoctorModel> doctors) {
        this.doctors = doctors;
    }

    public List<DoctorModel> getDoctors() {
        List<DoctorModel> doctorModels = new ArrayList<>();
        for (DoctorModel doctorModel : doctors) {
            doctorModels.add(doctorModel);
        }
        return doctorModels;
    }

    public void setDoctors(List<DoctorModel> doctors) {
        RealmList<DoctorModel> doctorModels = new RealmList<>();
        doctorModels.addAll(doctors);
        this.doctors = doctorModels;
    }
}
