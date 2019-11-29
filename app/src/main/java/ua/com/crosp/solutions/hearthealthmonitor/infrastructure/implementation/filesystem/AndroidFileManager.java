package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.write.AndroidSimpleFileHandler;

/**
 * Created by Alexander Molochko on 11/5/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AndroidFileManager implements FileManager {
    private final Context mAppContext;

    public AndroidFileManager(Context applicationContext) {
        mAppContext = applicationContext;
    }

    @Override
    public FileHandler createFromFilePath(String filePath) throws FileNotFoundException {
        makeDirectoriesIfNotExist(filePath);
        return AndroidSimpleFileHandler.createFromSimpleStream(filePath);
    }

    public String getFilePathInSandboxDirectory() {
        return mAppContext.getFilesDir().getAbsolutePath();
    }

    @Override
    public String getExternalSdCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
    @Override
    public boolean makeDirectoriesIfNotExist(String path) {
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

    @Override
    public String appendSdcardPath(String filePath) {
        return getExternalSdCardPath() + filePath;
    }


}
