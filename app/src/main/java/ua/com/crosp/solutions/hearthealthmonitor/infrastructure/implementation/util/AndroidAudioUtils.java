package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import android.media.AudioRecord;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */


public class AndroidAudioUtils {
    private static final int INVALID_SAMPLING_RATE = -1;


    public static boolean isSupportedSampleRate(int sampleRate, int channelConfig, int format) {
        int bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, format);
        return bufferSize > 0;
    }

    public static int getSupportedSampleRate(int desiredRate, int channelConfig, int format) {
        if (isSupportedSampleRate(desiredRate, channelConfig, format)) {
            return desiredRate;
        }
        for (int rate : AndroidAudioUtils.SampleRate.AVAILABLE_SAMPLE_RATES) {
            if (isSupportedSampleRate(rate, channelConfig, format)) {
                return rate;
            }
        }
        return INVALID_SAMPLING_RATE;
    }

    public static class SampleRate {
        public static final int RATE_8K = 8000;
        public static final int RATE_11K = 11025;
        public static final int RATE_16K = 16000;
        public static final int RATE_22K = 22050;
        public static final int RATE_44K = 44100;
        public static final int RATE_48K = 48000;
        public static final int RATE_88K = 88200;
        public static final int RATE_96K = 96000;
        public static final int RATE_176K = 176400;

        public static final int[] AVAILABLE_SAMPLE_RATES = new int[]{RATE_8K, RATE_11K, RATE_16K, RATE_22K, RATE_44K, RATE_48K, RATE_88K, RATE_96K, RATE_176K};

    }
}