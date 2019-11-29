package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.repository;

import java.util.List;

import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.realm.Realm;
import io.realm.RealmResults;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.common.exception.data.ItemNotFoundDataException;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.database.BaseRealmRepository;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.model.DoctorModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.data.model.DoctorModelFields;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorRepository extends BaseRealmRepository implements DoctorsRepositoryContract {
    private final Realm mRealmDb;
    // Variables
    private DoctorE2MMapperContract mE2MMapperContract;
    private DoctorM2EMapperContract mM2EMapperContract;

    public DoctorRepository(Realm realmDb, DoctorE2MMapperContract e2MMapperContract, DoctorM2EMapperContract m2EMapperContract, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD)
            ExecutionThread realmThread) {
        super(realmThread);
        mRealmDb = realmDb;
        mE2MMapperContract = e2MMapperContract;
        mM2EMapperContract = m2EMapperContract;
    }


    @Override
    public Single<DoctorEntity> getDoctorById(final Long doctorId) {
        return new Single<DoctorEntity>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super DoctorEntity> observer) {
                DoctorModel doctorModel = mRealmDb.where(DoctorModel.class).equalTo(DoctorModelFields.ID, doctorId).findFirst();
                if (null != doctorModel) {
                    observer.onSuccess(mM2EMapperContract.transform(doctorModel));
                } else {
                    observer.onError(new ItemNotFoundDataException("Doctor with ID : " + doctorId + " was not found"));
                }
            }
        }.subscribeOn(mRealmExecutionThread.getScheduler());
    }

    @Override
    public Single<DoctorEntity.List> getAllDoctors() {
        return new Single<DoctorEntity.List>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super DoctorEntity.List> observer) {
                final RealmResults<DoctorModel> doctorModels = mRealmDb.where(DoctorModel.class).findAll();
                final List<DoctorEntity> doctorEntities = mM2EMapperContract.transform(new DoctorModel.ModelList(doctorModels));
                observer.onSuccess(new DoctorEntity.List(doctorEntities));
            }
        }.subscribeOn(mRealmExecutionThread.getScheduler());
    }

    @Override
    public Single<Long> addNewDoctor(final DoctorEntity doctorEntity) {
        return new Single<Long>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Long> observer) {
                Logger.error("addNewDoctor CURRENT THREAD " + Thread.currentThread().getName());
                doctorEntity.setEntityId(EntityId.generateUniqueId());
                DoctorModel doctorModel = mE2MMapperContract.transform(doctorEntity);
                mRealmDb.beginTransaction();
                mRealmDb.copyToRealm(doctorModel);
                mRealmDb.commitTransaction();
                observer.onSuccess(doctorModel.getId());
            }
        }.subscribeOn(mRealmExecutionThread.getScheduler());
    }
}
