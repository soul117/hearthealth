package ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;

/**
 * Interface that represents a Repository for getting {@link ModeItem} related data.
 */
public interface UserSessionSettingsRepositoryContract {
    Single<Long> getUserSessionId();

    Single<Long> saveUserSessionId(Long sessionId);
}
