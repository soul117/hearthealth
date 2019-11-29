package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.receivers;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Alexander Molochko on 11/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class HeadphonesPlugBroadcastReceiver extends HeadphonesPlugNotifier {

    private final Context mContext;
    BehaviorSubject<Integer> mEventSubject;

    public HeadphonesPlugBroadcastReceiver(Context context) {
        mContext = context;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.isWiredHeadsetOn()) {
            mEventSubject = BehaviorSubject.createDefault(PLUG);
        } else {
            mEventSubject = BehaviorSubject.createDefault(UNPLUG);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case UNPLUG:
                    mEventSubject.onNext(UNPLUG);
                    break;
                case PLUG:
                    mEventSubject.onNext(PLUG);
                    break;
                default:
                    mEventSubject.onNext(UNKNOWN);
                    break;
            }
        }
    }


    @Override
    public BehaviorSubject<Integer> getEventSubject() {
        return mEventSubject;
    }
}