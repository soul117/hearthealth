package ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Gender {
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    @HumanGender
    private String mGenderString = GENDER_FEMALE;

    public Gender() {
    }

    public Gender(@HumanGender String genderString) {
        mGenderString = genderString;
    }

    @HumanGender
    public String getGenderString() {
        return mGenderString;
    }

    public void setGenderString(@HumanGender String genderString) {
        mGenderString = genderString;
    }

    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @StringDef({GENDER_MALE, GENDER_FEMALE})
    // Create an interface for validating String types
    public @interface HumanGender {
    }


}
