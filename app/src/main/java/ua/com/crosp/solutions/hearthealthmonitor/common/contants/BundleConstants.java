package ua.com.crosp.solutions.hearthealthmonitor.common.contants;

/**
 * Created by Alexander Molochko on 4/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BundleConstants {

    private BundleConstants() {
    }

    public static final class Arguments {
        public static final java.lang.String ESTIMATED_RECORDING_TIME = "experiment_time";
        public static final String ECG_ACTIVITY = "ecg_activity.arguments";
        public static final String ECG_SERVICE_OPTIONS = "ecg_service_options";

        private Arguments() {
        }

        public static final String MODE_LAUNCH_SCREEN = "mode.screen";
        public static final String MODE_ID = "mode.id";
        public static final String EXPERIMENT_RESULT_ID = "experiment_result.id";

    }

    public static final class Values {
        public static final String MODE_GAME_SCREEN = "mode.game_screen";
        public static final String MODE_SETTINGS_SCREEN = "mode.settings_screen";
    }
}
