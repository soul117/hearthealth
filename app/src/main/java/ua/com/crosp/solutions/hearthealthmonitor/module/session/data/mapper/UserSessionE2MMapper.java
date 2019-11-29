package ua.com.crosp.solutions.hearthealthmonitor.module.session.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model.UserSessionModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserE2MMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSessionE2MMapper extends UserSessionE2MMapperContract {
    private UserE2MMapperContract mUserE2MMapperContract;

    public UserSessionE2MMapper(UserE2MMapperContract userE2MMapperContract) {
        mUserE2MMapperContract = userE2MMapperContract;
    }

    @Override
    public UserSessionModel transform(UserSessionEntity inputItem) {
        UserSessionModel sessionModel = new UserSessionModel();
        sessionModel.setExpirationDate(inputItem.getExpirationDate().getDate());
        sessionModel.setUserModel(mUserE2MMapperContract.transform(inputItem.getUserEntity()));
        sessionModel.setSessionId(inputItem.getEntityId().getId());
        return sessionModel;
    }
}
