package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserRepositoryContract;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SaveUserInfoUseCase extends BaseSingleUseCaseContract<UserEntity, Long> {

    private final UserRepositoryContract mUserSettingsRepository;

    public SaveUserInfoUseCase(UserRepositoryContract userSettingsRepository, PostExecutionThread postExecutionThread, ExecutionThread executionThread) {
        super(executionThread, postExecutionThread);
        mUserSettingsRepository = userSettingsRepository;
    }


    @Override
    public Single<Long> provideSingleObservable(UserEntity userEntity) {
        if (userEntity.getEntityId() == EntityId.INVALID_ENTITY_ID) {
            userEntity.setEntityId(EntityId.generateUniqueId());
        }
        if (userEntity.getUserSettingsEntity().getEntityId() == EntityId.INVALID_ENTITY_ID) {
            userEntity.getUserSettingsEntity().setEntityId(EntityId.generateUniqueId());
        }
        return mUserSettingsRepository.saveUserWithSettings(userEntity);
    }
}
