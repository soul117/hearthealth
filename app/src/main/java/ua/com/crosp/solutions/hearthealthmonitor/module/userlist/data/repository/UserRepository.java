package ua.com.crosp.solutions.hearthealthmonitor.module.userlist.data.repository;

import java.util.List;

import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.realm.Realm;
import io.realm.RealmResults;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.common.exception.data.ItemNotFoundDataException;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.database.BaseRealmRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model.UserModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model.UserModelFields;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserRepositoryContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserRepository extends BaseRealmRepository implements UserRepositoryContract {
    private final Realm mRealmDb;
    // Variables
    private UserE2MMapperContract mE2MMapperContract;
    private UserM2EMapperContract mM2EMapperContract;

    public UserRepository(Realm realmDb, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread, UserE2MMapperContract e2MMapperContract, UserM2EMapperContract m2EMapperContract) {
        super(realmThread);
        mRealmDb = realmDb;
        mE2MMapperContract = e2MMapperContract;
        mM2EMapperContract = m2EMapperContract;
    }

    @Override
    public Single<UserEntity> getUserWithSettingsById(final Long userId) {
        return wrappInRealmDbThread(new Single<UserEntity>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super UserEntity> observer) {
                UserModel userModel = mRealmDb.where(UserModel.class).equalTo(UserModelFields.ID, userId).findFirst();
                if (null != userModel) {
                    observer.onSuccess(mM2EMapperContract.transform(userModel));
                } else {
                    observer.onError(new ItemNotFoundDataException("User with ID : " + userId + " was not found"));
                }
            }
        });
    }

    @Override
    public Single<UserEntity.List> getAllUsers() {
        return wrappInRealmDbThread(new Single<UserEntity.List>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super UserEntity.List> observer) {
                final RealmResults<UserModel> userModels = mRealmDb.where(UserModel.class).findAll();
                final List<UserEntity> userEntities = mM2EMapperContract.transform(new UserModel.ModelList(userModels));
                observer.onSuccess(new UserEntity.List(userEntities));
            }
        });
    }

    @Override
    public Single<Long> saveUserWithSettings(final UserEntity userEntity) {
        return wrappInRealmDbThread(new Single<Long>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Long> observer) {
                UserModel userModel = mE2MMapperContract.transform(userEntity);
                mRealmDb.beginTransaction();
                mRealmDb.copyToRealmOrUpdate(userModel);
                mRealmDb.commitTransaction();
                observer.onSuccess(userModel.getId());
            }
        });
    }
}
