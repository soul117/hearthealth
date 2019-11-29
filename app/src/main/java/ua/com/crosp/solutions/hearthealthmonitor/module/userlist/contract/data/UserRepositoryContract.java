package ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;

/**
 * Interface that represents a Repository for getting {@link ModeItem} related data.
 */
public interface UserRepositoryContract {
    Single<UserEntity> getUserWithSettingsById(Long userId);
    Single<UserEntity.List> getAllUsers();

    Single<Long> saveUserWithSettings(UserEntity userSettingsEntity);
}
