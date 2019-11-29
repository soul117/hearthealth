package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.model.DoctorModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorE2MMapper extends DoctorE2MMapperContract {

    @Override
    public DoctorModel transform(DoctorEntity inputItem) {
        DoctorModel doctorModel = new DoctorModel();
        doctorModel.email = inputItem.getEmail().getEmailString();
        doctorModel.firstName = inputItem.getFullName().getFirstName();
        doctorModel.lastName = inputItem.getFullName().getLastName();
        doctorModel.id = inputItem.getEntityId().getId();
        return doctorModel;
    }

}
