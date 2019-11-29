package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common;

import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

/**
 * Created by Alexander Molochko on 11/10/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AudioRecordingBuffer {
    private ShortValues mYVoltageBuffer;
    private DoubleValues mXTimeBuffer;

    public static AudioRecordingBuffer initWithBufferSize(int bufferSize) {
        AudioRecordingBuffer recordingBuffer = new AudioRecordingBuffer();
        recordingBuffer.mYVoltageBuffer = new ShortValues(bufferSize);
        recordingBuffer.mXTimeBuffer = new DoubleValues(bufferSize);
        recordingBuffer.mXTimeBuffer.setSize(bufferSize);
        recordingBuffer.mYVoltageBuffer.setSize(bufferSize);
        return recordingBuffer;
    }

    public DoubleValues getTimeBufferContent() {
        return mXTimeBuffer;
    }

    public ShortValues getVoltageBufferContent() {
        return mYVoltageBuffer;
    }

    public void setVoltageBufferContent(double[] bufferContent) {
        this.mXTimeBuffer.clear();
        this.mXTimeBuffer.add(bufferContent);
    }

    public void setVoltageBufferContent(short[] bufferContent) {
        this.mYVoltageBuffer.clear();
        this.mYVoltageBuffer.add(bufferContent);
    }

}
