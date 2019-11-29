package ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;

/**
 * Created by Alexander Molochko on 10/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface UserSessionManagerContract {
    Single<Boolean> loadPreviousSession();

    UserSessionEntity getCurrentUserSession();

    UserEntity getCurrentSessionUserEntity();

    Single<Long> destroyCurrentUserSession();

    boolean hasPreviousSession();

    Single<Long> setUserForSession(UserEntity userEntity);

    EntityId getCurrentUserEntityId();
}
