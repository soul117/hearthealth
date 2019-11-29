package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.data;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgNotificationInfo {
    public String title;
    public String description;
    @EcgRecordingState
    public int state;
    public String fontIconText;
    public String id;

    public EcgNotificationInfo() {
    }

    public EcgNotificationInfo(String title, String description, @EcgRecordingState int state, String fontIconText) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.fontIconText = fontIconText;
    }


    public static final int STATE_STOPPED = 0;
    public static final int STATE_RECORDING = 1;
    public static final int STATE_PAUSED = 2;

    @IntDef({STATE_STOPPED, STATE_RECORDING, STATE_PAUSED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EcgRecordingState {
    }
}
