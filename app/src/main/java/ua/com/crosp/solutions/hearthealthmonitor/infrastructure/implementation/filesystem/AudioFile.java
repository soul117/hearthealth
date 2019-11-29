package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;

/**
 * Created by Alexander Molochko on 11/5/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface AudioFile extends FileManager.FileHandler {
    void setAudioSettings(ReactiveAudioRecorder.Settings settings);

    ReactiveAudioRecorder.Settings getAudioSettings();
}
