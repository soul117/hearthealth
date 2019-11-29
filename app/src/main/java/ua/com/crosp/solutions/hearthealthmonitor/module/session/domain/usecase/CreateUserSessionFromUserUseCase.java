package ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.ExpirationDate;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CreateUserSessionFromUserUseCase extends BaseSingleUseCaseContract<UserEntity, UserSessionEntity> {


    private final UserSessionRepositoryContract mRepository;

    public CreateUserSessionFromUserUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, UserSessionRepositoryContract repositoryContract) {
        super(executionThread, postExecutionThread);
        mRepository = repositoryContract;
    }


    @Override
    public Single<UserSessionEntity> provideSingleObservable(UserEntity userEntity) {
        UserSessionEntity sessionEntity = new UserSessionEntity();
        sessionEntity.setExpirationDate(ExpirationDate.NO_EXPIRATION_DATE);
        sessionEntity.setUserEntity(userEntity);
        sessionEntity.setEntityId(EntityId.generateUniqueId());
        return mRepository.createNewSession(sessionEntity);
    }
}
