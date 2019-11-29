package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Alexander Molochko on 4/29/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SystemUtils {

    public static boolean isRunningOnEmulator(Context context) {
        SensorManager manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        return manager.getSensorList(Sensor.TYPE_ALL).isEmpty() ||
                "generic".equals(Build.BRAND.toLowerCase()) ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK");
    }
}
