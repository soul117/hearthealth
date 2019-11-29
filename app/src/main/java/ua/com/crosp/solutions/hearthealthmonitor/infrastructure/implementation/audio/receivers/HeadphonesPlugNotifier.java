package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.receivers;

import android.content.BroadcastReceiver;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Alexander Molochko on 11/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public abstract class HeadphonesPlugNotifier extends BroadcastReceiver {

    public abstract BehaviorSubject<Integer> getEventSubject();
    public static final int PLUG = 1;
    public static final int UNPLUG = 0;
    public static final int UNKNOWN = -999;

    @IntDef({PLUG, UNPLUG, UNKNOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlugEvent {
    }
}
