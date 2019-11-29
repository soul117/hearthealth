package ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.model.Entity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.ExpirationDate;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSessionEntity extends Entity {

    public static final UserSessionEntity INVALID = createDefault();
    private UserEntity mUserEntity;
    private ExpirationDate mExpirationDate;

    public static boolean isValidSession(UserSessionEntity entity) {
        return entity != null && entity != INVALID;
    }

    public UserEntity getUserEntity() {
        return mUserEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        mUserEntity = userEntity;
    }

    public ExpirationDate getExpirationDate() {
        return mExpirationDate;
    }

    public void setExpirationDate(ExpirationDate expirationDate) {
        mExpirationDate = expirationDate;
    }

    public static UserSessionEntity createDefault() {
        UserSessionEntity sessionEntity = new UserSessionEntity();
        sessionEntity.setExpirationDate(ExpirationDate.NO_EXPIRATION_DATE);
        sessionEntity.setEntityId(EntityId.INVALID_ENTITY_ID);
        sessionEntity.setUserEntity(UserEntity.INVALID);
        return sessionEntity;
    }

    public EntityId getSessionId() {
        return getEntityId();
    }

    public void setSessionId(EntityId sessionId) {
        setEntityId(sessionId);
    }

    @Override
    public String toString() {
        return String.format("Session : %s", getSessionId().getId());
    }
}
