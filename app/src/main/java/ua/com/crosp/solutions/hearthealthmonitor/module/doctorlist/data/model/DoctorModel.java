package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.model;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Email;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FullName;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorModel extends RealmObject {
    @PrimaryKey
    public Long id;
    public String firstName;
    public String lastName;
    public String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctorName(FullName userName) {
        firstName = userName.getFirstName();
        lastName = userName.getFirstName();
    }

    public void setEmail(Email email) {
        this.email = email.getEmailString();
    }


    public static class ModelList extends ArrayList<DoctorModel> {
        public ModelList(RealmResults<DoctorModel> doctorModels) {
            super(doctorModels);
        }
    }
}
