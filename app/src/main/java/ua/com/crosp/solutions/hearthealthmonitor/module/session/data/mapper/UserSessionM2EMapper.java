package ua.com.crosp.solutions.hearthealthmonitor.module.session.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model.UserSessionModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.ExpirationDate;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserM2EMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSessionM2EMapper extends UserSessionM2EMapperContract {
    private UserM2EMapperContract m2EMapperContract;

    public UserSessionM2EMapper(UserM2EMapperContract m2EMapperContract) {
        this.m2EMapperContract = m2EMapperContract;
    }

    @Override
    public UserSessionEntity transform(UserSessionModel inputItem) {
        UserSessionEntity sessionEntity = new UserSessionEntity();
        sessionEntity.setExpirationDate(new ExpirationDate(inputItem.getExpirationDate()));
        sessionEntity.setEntityId(new EntityId(inputItem.getSessionId()));
        sessionEntity.setUserEntity(m2EMapperContract.transform(inputItem.getUserModel()));
        return sessionEntity;
    }

}
