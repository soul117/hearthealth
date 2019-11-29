package ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FilePath {
    public static FilePath NULLABLE_FILE_PATH = new FilePath("");
    private String mFilePath;

    public FilePath() {
    }

    public FilePath(String filePath) {
        mFilePath = filePath;
    }

    public String getStringPath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }
}
