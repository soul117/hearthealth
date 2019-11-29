package ua.com.crosp.solutions.hearthealthmonitor.common.contants;

/**
 * Created by Alexander Molochko on 4/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class IntentExtras {

    private IntentExtras() {
    }

    public static final class Arguments {
        private Arguments() {
        }

        public static final String MODE_LAUNCH_SCREEN = "mode.screen";
        public static final String MODE_ID = "mode.id";
        public static final String EXPERIMENT_RESULT_ID = "experiment_result.id";

    }

    public static final class EcgServiceIntent {
        public static final String TITLE = "ecg.service.title";
        public static final String AUDIO_FILE_PATH = "ecg.service.file_path";
        public static final String RECORDING_DESTINATION_SOURCE = "ecg.service.destination_source";
        public static final String DESCRIPTION = "ecg.service.description";
    }
}
