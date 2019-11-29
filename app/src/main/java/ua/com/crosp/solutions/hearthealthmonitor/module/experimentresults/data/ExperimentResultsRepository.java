package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.data;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.realm.Realm;
import io.realm.RealmResults;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.common.exception.data.ItemNotFoundDataException;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.database.BaseRealmRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.ExperimentResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.ExperimentResultModelFields;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.ExperimentResultsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.ExperimentResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.ExperimentResultM2EMapperContract;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentResultsRepository extends BaseRealmRepository implements ExperimentResultsRepositoryContract {
    private ExperimentResultM2EMapperContract mExperimentResultM2EMapperContract;
    private ExperimentResultE2MMapperContract mExperimentResultE2MMapperContract;
    private Realm mRealmDb;


    public ExperimentResultsRepository(ExecutionThread realmExecutionThread, ExperimentResultM2EMapperContract experimentResultM2EMapperContract, ExperimentResultE2MMapperContract experimentResultE2MMapperContract, Realm realmDb) {
        super(realmExecutionThread);
        mExperimentResultM2EMapperContract = experimentResultM2EMapperContract;
        mExperimentResultE2MMapperContract = experimentResultE2MMapperContract;
        mRealmDb = realmDb;
    }

    @Override
    public Single<ExperimentResultEntity.List> getAllExperimentResults() {
        return wrappInRealmDbThread(new Single<ExperimentResultEntity.List>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super ExperimentResultEntity.List> observer) {
                final RealmResults<ExperimentResultModel> experimentResultModels = mRealmDb.where(ExperimentResultModel.class).findAll();
                final List<ExperimentResultEntity> experimentResultEntities = mExperimentResultM2EMapperContract.transform(new ExperimentResultModel.ModelList(experimentResultModels));
                observer.onSuccess(new ExperimentResultEntity.List(experimentResultEntities));
            }
        });
    }

    @Override
    public Single<ExperimentResultEntity.List> getAllExperimentResultsForUser(final Long userId) {
        return wrappInRealmDbThread(new Single<ExperimentResultEntity.List>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super ExperimentResultEntity.List> observer) {
                final RealmResults<ExperimentResultModel> experimentResultModels = mRealmDb.where(ExperimentResultModel.class).equalTo(ExperimentResultModelFields.USER_ID, userId).findAll();
                final List<ExperimentResultEntity> experimentResultEntities = mExperimentResultM2EMapperContract.transform(new ExperimentResultModel.ModelList(experimentResultModels));
                observer.onSuccess(new ExperimentResultEntity.List(experimentResultEntities));
            }
        });
    }

    @Override
    public Single<ExperimentResultEntity> getExperimentResultById(final long experimentId) {

        return wrappInRealmDbThread(new Single<ExperimentResultEntity>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super ExperimentResultEntity> observer) {
                ExperimentResultModel experimentResultModel = mRealmDb.where(ExperimentResultModel.class).equalTo(ExperimentResultModelFields.ID, experimentId).findFirst();
                if (null != experimentResultModel) {
                    observer.onSuccess(mExperimentResultM2EMapperContract.transform(experimentResultModel));
                } else {
                    observer.onError(new ItemNotFoundDataException("Experiment with ID : " + experimentId + " was not found"));
                }
            }
        });
    }

    @Override
    public Single<Long> saveExperimentResultForUser(final ExperimentResultEntity experimentResult) {
        return wrappInRealmDbThread(new Single<Long>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Long> observer) {
                ExperimentResultModel experimentResultModel = mExperimentResultE2MMapperContract.transform(experimentResult);
                mRealmDb.beginTransaction();
                mRealmDb.copyToRealmOrUpdate(experimentResultModel);
                mRealmDb.commitTransaction();
                observer.onSuccess(experimentResult.getEntityId().getId());
            }
        });
    }

    @Override
    public Single<Boolean> removeExperimentById(final Long experimentId) {
        return wrappInRealmDbThread(new Single<Boolean>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Boolean> observer) {
                RealmResults<ExperimentResultModel> result = mRealmDb.where(ExperimentResultModel.class).equalTo(ExperimentResultModelFields.ID, experimentId).findAll();
                mRealmDb.beginTransaction();
                result.deleteAllFromRealm();
                mRealmDb.commitTransaction();
                observer.onSuccess(result.size() > 0);
            }
        });
    }

}
