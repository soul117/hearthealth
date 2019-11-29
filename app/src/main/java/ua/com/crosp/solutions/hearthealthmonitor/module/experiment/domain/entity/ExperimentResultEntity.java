package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.model.Entity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FilePath;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.GeneralDate;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.FileSystemConfiguration;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentResultEntity extends Entity {
    public static final ExperimentResultEntity INVALID = createNullableEntity();

    private EntityId mUserId;
    private EcgResultEntity mEcgResultEntity;
    private GameResultEntity mGameResultEntity;
    private FeelingResultEntity mFeelingResultEntity;
    private GeneralDate mDateOfCreation;
    private String mExperimentName;
    private String mBaseExperimentPath;


    public void setEntityId(Long id) {
        setEntityId(new EntityId(id));
    }

    public static ExperimentResultEntity createNullableEntity() {
        ExperimentResultEntity ecgResultEntity = new ExperimentResultEntity();
        ecgResultEntity.setEntityId(EntityId.INVALID_ENTITY_ID);
        ecgResultEntity.setDateOfCreation(GeneralDate.NULLABLE);
        ecgResultEntity.setEcgResultEntity(EcgResultEntity.INVALID);
        ecgResultEntity.setFeelingResultEntity(FeelingResultEntity.INVALID);
        ecgResultEntity.setGameResultEntity(GameResultEntity.INVALID);
        ecgResultEntity.setExperimentName("");
        ecgResultEntity.setBaseExperimentPath("");
        return ecgResultEntity;
    }

    public EntityId getUserId() {
        return mUserId;
    }

    public void setUserId(EntityId userId) {
        mUserId = userId;
    }

    public EcgResultEntity getEcgResultEntity() {
        return mEcgResultEntity;
    }

    public void setEcgResultEntity(EcgResultEntity ecgResultEntity) {
        mEcgResultEntity = ecgResultEntity;
    }

    public GeneralDate getDateOfCreation() {
        return mDateOfCreation;
    }

    public void setDateOfCreation(GeneralDate dateOfCreation) {
        mDateOfCreation = dateOfCreation;
    }

    public String getExperimentName() {
        return mExperimentName;
    }

    public void setExperimentName(String experimentName) {
        mExperimentName = experimentName;
    }

    public void setGameResultEntity(GameResultEntity gameResultEntity) {
        mGameResultEntity = gameResultEntity;
    }

    public GameResultEntity getGameResultEntity() {
        return mGameResultEntity;
    }

    public FeelingResultEntity getFeelingResultEntity() {
        return mFeelingResultEntity;
    }

    public void setFeelingResultEntity(FeelingResultEntity feelingResultEntity) {
        mFeelingResultEntity = feelingResultEntity;
    }

    public void generateIdsForResultsIfRequired() {
        if (mFeelingResultEntity.getEntityId() == null || mFeelingResultEntity.getEntityId() == EntityId.INVALID_ENTITY_ID) {
            mFeelingResultEntity.setEntityId(EntityId.generateUniqueId());
        }
        if (mGameResultEntity.getEntityId() == null || mGameResultEntity.getEntityId() == EntityId.INVALID_ENTITY_ID) {
            mGameResultEntity.setEntityId(EntityId.generateUniqueId());
        }
        if (mEcgResultEntity.getEntityId() == null || mGameResultEntity.getEntityId() == EntityId.INVALID_ENTITY_ID) {
            mEcgResultEntity.setEntityId(EntityId.generateUniqueId());
        }
    }

    public String getBaseExperimentPath() {
        return mBaseExperimentPath;
    }

    public String getAudioEcgRecordingPathInitIfRequired() {
        setBaseExperimentPathFromUserId(mUserId.toString());
        return mEcgResultEntity.getRawRecordingPathAndInit(getBaseExperimentPath()).getStringPath();
    }

    public String getAudioEcgRecordingPath() {
        return mEcgResultEntity.getRawRecordingPath().getStringPath();
    }

    public void setBaseExperimentPath(String path) {
        mBaseExperimentPath = path;

    }

    public void setBaseExperimentPathFromUserId(String userEntityId) {
        if (mBaseExperimentPath == null || mBaseExperimentPath.isEmpty() && getEntityId() != EntityId.INVALID_ENTITY_ID) {
            mBaseExperimentPath = String.format(
                    FileSystemConfiguration.DIRECTORY_PATH_USERS_DATA_FORMAT_SUBDIR,
                    userEntityId, getEntityId().getId().toString());
        }

    }

    public void setFullRawAudioPath(String fullRawAudioPath) {
        mEcgResultEntity.setRawRecordingPath(new FilePath(fullRawAudioPath));
    }

    public Long getExperimentTimeInSeconds() {
        return mEcgResultEntity.getEstimatedRecordingTimeInSeconds();
    }

    public FilePath getXmlReportPath() {
        return mEcgResultEntity.getXmlRecordingPath();
    }

    public FilePath getXmlReportPathInitIfRequired() {
        setBaseExperimentPathFromUserId(mUserId.toString());
        return mEcgResultEntity.getXmlReportPathAndInit(getBaseExperimentPath());
    }

    public static class List extends ArrayList<ExperimentResultEntity> {
        public List() {
        }

        public List(@NonNull Collection<? extends ExperimentResultEntity> c) {
            super(c);
        }
    }
}

