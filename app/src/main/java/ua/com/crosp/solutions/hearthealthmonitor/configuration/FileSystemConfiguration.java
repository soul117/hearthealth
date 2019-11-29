package ua.com.crosp.solutions.hearthealthmonitor.configuration;

import ua.com.crosp.solutions.hearthealthmonitor.configuration.base.BaseConfiguration;

/**
 * Created by Alexander Molochko on 10/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FileSystemConfiguration implements BaseConfiguration {
    public static final String DIRECTORY_PATH_USERS_DATA_FORMAT = "/users_data/user_%s/";
    public static final String DIRECTORY_PATH_USERS_DATA_FORMAT_SUBDIR = DIRECTORY_PATH_USERS_DATA_FORMAT + "%s";
    public static final String FILE_AUDIO_ECG = "ecg_audio.wav";
    public static final String FILE_XML_ECG = "ecg.xml";
    public static final String FILE_PDF_ECG = "ecg_report.pdf";
}
