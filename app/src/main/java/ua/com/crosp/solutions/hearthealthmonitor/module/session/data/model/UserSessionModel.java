package ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSessionModel extends RealmObject {
    @PrimaryKey
    private Long sessionId;
    private UserModel userModel;
    private Date expirationDate;

    public static final UserSessionModel NULLABLE_SESSION = createNullableSession();

    private static UserSessionModel createNullableSession() {
        UserSessionModel sessionModel = new UserSessionModel();
        sessionModel.setUserModel(new UserModel());
        return sessionModel;
    }


    public UserSessionModel() {
    }

    public UserSessionModel(Long id) {
        sessionId = id;
    }

    public boolean hasTheSameId(Long sessionId) {
        return this.sessionId.equals(sessionId);
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
