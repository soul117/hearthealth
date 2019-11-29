package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.model.Entity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Email;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FullName;


/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorEntity extends Entity {
    private FullName mFullName;
    private Email mEmail;


    public FullName getFullName() {
        return mFullName;
    }

    public void setFullName(FullName fullName) {
        mFullName = fullName;
    }

    public Email getEmail() {
        return mEmail;
    }

    public void setEmail(Email email) {
        mEmail = email;
    }

    @Override
    public String toString() {
        return mFullName.toString() + " - " + mEmail.toString();
    }

    public void setFullName(String firstName, String lastName) {
        mFullName = new FullName(firstName, lastName);
    }

    public void setEmailString(String emailString) {
        mEmail.setEmailString(emailString);
    }

    public static class List extends ArrayList<DoctorEntity> {
        public List() {
            super();
        }

        public static List from(@NonNull Collection<? extends DoctorEntity> c) {
            return new List(c);
        }

        public List(@NonNull Collection<? extends DoctorEntity> c) {
            super(c);
        }

        public Integer[] getSelectedIndexesFromArray(List allDoctors) {
            java.util.List<Integer> selectedIndices = new ArrayList<>();
            for (int i = 0; i < allDoctors.size(); i++) {
                if (this.contains(allDoctors.get(i))) {
                    selectedIndices.add(i);
                }
            }
            return selectedIndices.toArray(new Integer[]{});
        }

        public java.util.List<Long> toIdList() {
            java.util.List<Long> idList = new ArrayList<>();
            for (int i = 0; i < this.size(); i++) {
                idList.add(this.get(i).getEntityId().getId());
            }
            return idList;
        }

        public String toSingleString(String separator) {
            StringBuilder sb = new StringBuilder();
            for (DoctorEntity entity : this) {
                sb.append(entity.toString()).append(separator);
            }
            return sb.toString();
        }
    }
}
