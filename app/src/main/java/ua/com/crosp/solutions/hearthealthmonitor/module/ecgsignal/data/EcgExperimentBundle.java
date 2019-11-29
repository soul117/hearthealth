package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.data;

import android.os.Bundle;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgExperimentBundle {
    public Bundle parametersBundle;

    public static final EcgExperimentBundle EMPTY_PARAMETERS = new EcgExperimentBundle();

    public void setEcgAudioFilePath(String filePath) {

    }

    public boolean isEmpty() {
        return null != parametersBundle;
    }
}
