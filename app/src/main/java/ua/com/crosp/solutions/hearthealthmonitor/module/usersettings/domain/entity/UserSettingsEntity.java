package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.entity;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.model.Entity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSettingsEntity extends Entity {
    public static final UserSettingsEntity INVALID = createNullableEntity();
    private EntityId mPersonalId;
    private DoctorEntity.List mDoctors;

    public void setEntityId(Long id) {
        setEntityId(new EntityId(id));
    }

    public EntityId getPersonalId() {
        return mPersonalId;
    }

    public void setPersonalId(EntityId personalId) {
        mPersonalId = personalId;
    }

    public void setPersonalId(Long personalId) {
        mPersonalId = new EntityId(personalId);
    }

    public DoctorEntity.List getDoctors() {
        return mDoctors;
    }

    public void setDoctors(DoctorEntity.List doctors) {
        mDoctors = doctors;
    }


    public static UserSettingsEntity createNullableEntity() {
        UserSettingsEntity settingsEntity = new UserSettingsEntity();
        settingsEntity.setEntityId(EntityId.INVALID_ENTITY_ID);
        settingsEntity.setPersonalId(EntityId.INVALID_ENTITY_ID);
        settingsEntity.setDoctors(new DoctorEntity.List());
        return settingsEntity;
    }


}
