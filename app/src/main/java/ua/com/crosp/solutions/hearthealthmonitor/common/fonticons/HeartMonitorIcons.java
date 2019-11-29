package ua.com.crosp.solutions.hearthealthmonitor.common.fonticons;

import com.joanzapata.iconify.Icon;

/**
 * Created by Alexander Molochko on 1/16/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public enum HeartMonitorIcons implements Icon {
    // Icons
    hm_history('\ue900'),
    hm_respiratory('\ue901'),
    hm_rest('\ue904'),
    hm_physical_activity('\ue905'),
    hm_psychoemotional('\ue906'),
    hm_dormant('\ue907'),
    hm_modes('\ue903'),
    hm_realtime('\ue902'),
    hm_stopwatch('\ue90d'),
    hm_countdown('\ue90c'),
    hm_speaker('\ue90b'),
    hm_gamepad('\ue909'),
    hm_speedometer('\ue90a'),
    hm_play('\ue90f'),
    hm_stop('\ue910'),
    hm_pause('\ue90e'),
    hm_restart('\ue911'),
    hm_doctor('\ue914'),
    hm_user_info('\ue915'),
    hm_doctor_hat('\ue913'),
    hm_calendar('\ue91a'),
    hm_heart_gear('\ue912'),
    hm_avatar('\ue918'),
    hm_id_card_2('\ue919'),
    hm_id_card('\ue91b'),
    hm_email('\ue91d'),
    hm_phone_ringing('\ue917'),
    hm_info('\ue91c'),
    hm_genders('\ue916'),
    hm_cardiogram('\ue91f'),
    hm_brain_heart('\ue91e'),
    hm_experiment_results('\ue920'),
    hm_voltage('\ue921'),
    hm_alarm_clock('\ue922'),
    hm_comment('\ue92b'),
    hm_feeling_dead('\ue92c'),
    hm_bicycle('\ue92e'),
    hm_interrupt('\ue92f'),
    hm_actual_time('\ue923'),
    hm_estimated_time('\ue924'),
    hm_ecg_square('\ue92a'),
    hm_email_message('\ue927'),
    hm_xml_document('\ue928'),
    hm_share('\ue925'),
    hm_yoga('\ue930'),
    hm_cloud_weather('\ue92d'),
    hm_cloud_upload('\ue926'),
    hm_pdf_document('\ue929'),
    hm_arrow_right('\ue908');

    // Variables
    char character;

    HeartMonitorIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }

}
