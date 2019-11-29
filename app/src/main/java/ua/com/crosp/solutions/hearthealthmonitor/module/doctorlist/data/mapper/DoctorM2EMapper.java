package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Email;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.model.DoctorModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorM2EMapper extends DoctorM2EMapperContract {

    @Override
    public DoctorEntity transform(DoctorModel inputItem) {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFullName(inputItem.firstName, inputItem.lastName);
        doctorEntity.setEntityId(new EntityId(inputItem.id));
        doctorEntity.setEmail(new Email(inputItem.email));
        return doctorEntity;
    }

}
