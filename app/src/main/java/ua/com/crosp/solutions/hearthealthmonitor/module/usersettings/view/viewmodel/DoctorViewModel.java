package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel;
;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Email;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FullName;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;

/**
 * Created by Alexander Molochko on 10/25/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorViewModel {
    private String firstName;
    private String lastName;
    private String email;
    private Long id;


    public static DoctorViewModel createFromDoctorEntity(DoctorEntity doctorEntity) {
        DoctorViewModel doctorViewModel = new DoctorViewModel();
        doctorViewModel.setEmail(doctorEntity.getEmail().getEmailString());
        doctorViewModel.setFirstName(doctorEntity.getFullName().getFirstName());
        doctorViewModel.setLastName(doctorEntity.getFullName().getLastName());
        doctorViewModel.setId(doctorEntity.getEntityId().getId());
        return doctorViewModel;
    }

    public DoctorEntity toDoctorEntity() {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setEmail(new Email(email));
        doctorEntity.setFullName(new FullName(firstName, lastName));
        doctorEntity.setEntityId(new EntityId(id));
        return doctorEntity;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", firstName, lastName, email);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static class List extends ArrayList<DoctorViewModel> {

        public List() {
        }

        public List(@NonNull Collection<? extends DoctorViewModel> c) {
            super(c);
        }

        public static List fromDoctorEntitiesList(java.util.List<DoctorEntity> doctorEntityList) {
            List doctorViewModels = new List();
            for (DoctorEntity doctorEntity : doctorEntityList) {
                doctorViewModels.add(createFromDoctorEntity(doctorEntity));
            }
            return doctorViewModels;
        }

        public String toSingleString(String separator) {
            StringBuilder sb = new StringBuilder();
            for (DoctorViewModel entity : this) {
                sb.append(entity.toString()).append(separator);
            }
            return sb.toString();
        }

        public DoctorEntity.List toDoctorEntitiesList() {
            DoctorEntity.List doctorEntities = new DoctorEntity.List();
            for (DoctorViewModel doctorViewModel : this) {
                doctorEntities.add(doctorViewModel.toDoctorEntity());
            }
            return doctorEntities;
        }

        public java.util.List<Long> toIdList() {
            java.util.List<Long> idList = new ArrayList<>();
            for (int i = 0; i < this.size(); i++) {
                idList.add(this.get(i).getId());
            }
            return idList;
        }
    }


}
