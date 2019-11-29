package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.model.Entity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GameResultEntity extends Entity {
    public static final GameResultEntity INVALID = createNullableEntity();
    private String mCustomJsonObject;
    private Long mGameActualTime;
    private String mGameName;
    private String mGameType;
    private Long mGameEstimatedTime;
    private boolean mWasInterrupted;
    private long mGameExperimentId;

    public void setEntityId(Long id) {
        setEntityId(new EntityId(id));
    }


    public static GameResultEntity createNullableEntity() {
        GameResultEntity ecgResultEntity = new GameResultEntity();
        ecgResultEntity.setEntityId(EntityId.INVALID_ENTITY_ID);
        ecgResultEntity.setWasInterrupted(false);
        ecgResultEntity.setCustomJsonObject("");
        ecgResultEntity.setGameName("");
        ecgResultEntity.setGameType("");
        ecgResultEntity.setGameActualTime(0L);
        ecgResultEntity.setGameEstimatedTime(0L);
        return ecgResultEntity;
    }

    public String getCustomJsonObject() {
        return mCustomJsonObject;
    }

    public void setCustomJsonObject(String customJsonObject) {
        mCustomJsonObject = customJsonObject;
    }

    public Long getGameActualTime() {
        return mGameActualTime;
    }

    public void setGameActualTime(Long gameActualTime) {
        mGameActualTime = gameActualTime;
    }

    public Long getGameEstimatedTime() {
        return mGameEstimatedTime;
    }

    public void setGameEstimatedTime(Long gameEstimatedTime) {
        mGameEstimatedTime = gameEstimatedTime;
    }

    public boolean isWasInterrupted() {
        return mWasInterrupted;
    }

    public void setWasInterrupted(boolean wasInterrupted) {
        mWasInterrupted = wasInterrupted;
    }

    public String getGameName() {
        return mGameName;
    }

    public void setGameName(String gameName) {
        mGameName = gameName;
    }

    public String getGameType() {
        return mGameType;
    }

    public void setGameType(String gameType) {
        mGameType = gameType;
    }

    public long getGameExperimentId() {
        return mGameExperimentId;
    }

    public void setGameExperimentId(long gameExperimentId) {
        mGameExperimentId = gameExperimentId;
    }
}
