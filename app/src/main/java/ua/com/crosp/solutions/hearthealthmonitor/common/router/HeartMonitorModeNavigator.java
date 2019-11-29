package ua.com.crosp.solutions.hearthealthmonitor.common.router;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.routing.ModesActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.MotivationalModeNavigator;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.routing.PAActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.routing.PsychoEmotionalActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.routing.RestModeActivity;

/**
 * Created by Alexander Molochko on 2/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class HeartMonitorModeNavigator implements MotivationalModeNavigator {

    @Override
    public Class getModeActivityClassById(int id) {
        Class activityClass;
        switch (id) {
            case R.id.mode_rest_after_activity:
                activityClass = RestModeActivity.class;
                break;
            case R.id.mode_physical_activity:
                activityClass = PAActivity.class;
                break;
            case R.id.mode_psychoemotional:
                activityClass = PsychoEmotionalActivity.class;
                break;
            case R.id.nav_menu_item_realtime:
                activityClass = ModesActivity.class;
                break;
            default:
                activityClass = ModesActivity.class;
        }
        return activityClass;

    }
}
