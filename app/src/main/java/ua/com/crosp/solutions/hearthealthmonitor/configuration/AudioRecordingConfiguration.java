package ua.com.crosp.solutions.hearthealthmonitor.configuration;

import ua.com.crosp.solutions.hearthealthmonitor.configuration.base.BaseConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.AndroidAudioUtils;

import static android.media.AudioFormat.ENCODING_PCM_16BIT;

/**
 * Created by Alexander Molochko on 10/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AudioRecordingConfiguration implements BaseConfiguration {
    public static final int SAMPLE_RATE = AndroidAudioUtils.SampleRate.RATE_8K;
    public static final int READ_BUFFER_SIZE = 2048;
    public static final int RECORDING_FORMAT = ENCODING_PCM_16BIT;
}
