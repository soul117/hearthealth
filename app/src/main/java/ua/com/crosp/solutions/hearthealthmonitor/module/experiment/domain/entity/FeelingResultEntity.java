package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.model.Entity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FeelingResultEntity extends Entity {
    public static final FeelingResultEntity INVALID = createNullableEntity();
    @FeelingType
    private String mFeelingType;
    @WeatherType
    private String mDayType;
    private String mComment;
    // Feeling type
    public static final String FEELING_GOOD = "Good";
    public static final String FEELING_WELL = "Well";
    public static final String FEELING_SICK = "Sick";
    public static final String FEELING_BAD = "Bad";

    @StringDef({FEELING_GOOD, FEELING_WELL, FEELING_SICK, FEELING_BAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FeelingType {
    }

    // Day type
    public static final String DAY_CLOUDY = "Cloudy";
    public static final String DAY_STORMY = "Stormy";
    public static final String DAY_SUNNY = "Sunny";
    public static final String DAY_RAINY = "Rainy";

    @StringDef({DAY_CLOUDY, DAY_STORMY, DAY_SUNNY, DAY_RAINY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeatherType {
    }

    public void setEntityId(Long id) {
        setEntityId(new EntityId(id));
    }


    public static FeelingResultEntity createNullableEntity() {
        FeelingResultEntity feelingEntity = new FeelingResultEntity();
        feelingEntity.setEntityId(EntityId.INVALID_ENTITY_ID);
        feelingEntity.setWeatherType(DAY_CLOUDY);
        feelingEntity.setFeelingType(FEELING_GOOD);
        feelingEntity.setComment("");
        return feelingEntity;
    }

    @FeelingType
    public String getFeelingType() {
        return mFeelingType;
    }

    public void setFeelingType(@FeelingType String feelingType) {
        mFeelingType = feelingType;
    }

    @WeatherType
    public String getWeatherType() {
        return mDayType;
    }

    public void setWeatherType(@WeatherType String dayType) {
        mDayType = dayType;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = new String(comment);
    }
}
