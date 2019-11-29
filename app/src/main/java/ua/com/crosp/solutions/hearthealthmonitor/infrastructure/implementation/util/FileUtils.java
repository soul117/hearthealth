package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import java.io.File;
import java.util.Date;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FileUtils {
    private final static Date CURRENT_DATE = new Date();
    private static Date sInitialDate = new Date(0);

    private FileUtils() {
        throw new AssertionError();
    }

    public static boolean makeDirectoriesIfNotExist(String path) {
        File pathFile = new File(path);
        boolean result = false;
        File parentFile = pathFile.getParentFile();
        if (!parentFile.exists()) {
            try {
                result = parentFile.mkdirs();
            } catch (Exception se) {
                se.printStackTrace();
                return false;
            }
        } else {
            result = true;
        }
        return result;
    }

}