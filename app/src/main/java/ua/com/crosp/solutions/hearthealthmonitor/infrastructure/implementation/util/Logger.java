package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import android.util.Log;

import java.util.List;

/**
 * Created by CROSP on 5/2/2017.
 */

public class Logger {
    public static final String DEBUG_TAG = "---Logger Debug---";
    public static final String ERROR_TAG = "---Logger Error---";

    private Logger() {
    }

    //================================================================================
    // Debug methods
    //================================================================================
    private static void debugBase(String message) {
        Log.d(DEBUG_TAG, message);
    }

    public static void debug(String message) {
        debugBase(message);
    }

    public static void debug(Object object) {
        debugBase(object.toString());
    }

    public static void debug(Exception exception) {
        debugBase(exception.getMessage());
    }

    public static void debug(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            debugBase(list.get(i).toString());
        }
    }

    //================================================================================
    // Error methods
    //================================================================================
    private static void errorBase(String message) {
        Log.e(ERROR_TAG, message);
    }

    public static void error(String message) {
        errorBase(message);
    }

    public static void error(Object object) {
        errorBase(object.toString());
    }

    public static void error(Exception exception) {
        errorBase(exception.getMessage());
    }

    public static void error(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            errorBase(list.get(i).toString());
        }
    }
}
