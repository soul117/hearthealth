package ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;

/**
 * Interface that represents a Repository for getting {@link ModeItem} related data.
 */
public interface UserSessionRepositoryContract {
    Single<UserSessionEntity> getUserSession(Long sessionId);

    Single<Long> saveUserSession(UserSessionEntity userSession);

    Single<UserSessionEntity> createNewSession(UserSessionEntity userSession);

    Single<Boolean> destroySession(Long sessionId);
}
