package ua.com.crosp.solutions.hearthealthmonitor.common.router;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.routing.AppSettingsActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing.EcgConnectionActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.routing.ExperimentResultsActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.routing.ModesActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.ApplicationNavigator;

/**
 * Created by Alexander Molochko on 2/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class HeartMonitorNavigator implements ApplicationNavigator {
    @Override
    public Class getActivityModuleClassById(int id) {
        Class activityClass;
        switch (id) {
            case R.id.nav_menu_item_results:
                activityClass = ExperimentResultsActivity.class;
                break;
            case R.id.nav_menu_item_modes_list:
                activityClass = ModesActivity.class;
                break;
            case R.id.nav_menu_item_realtime:
                activityClass = EcgConnectionActivity.class;
                break;
            case R.id.nav_menu_item_app_settings:
                activityClass = AppSettingsActivity.class;
                break;
            default:
                activityClass = ModesActivity.class;
        }
        return activityClass;
    }

    @Override
    public int getInitialModuleId() {
        return R.id.nav_menu_item_modes_list;
    }

    @Override
    public void exitApplication() {
        // Get rid of this code, move it somewhere
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
