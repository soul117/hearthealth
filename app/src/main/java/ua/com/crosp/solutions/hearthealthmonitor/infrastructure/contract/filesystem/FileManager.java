package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Alexander Molochko on 11/5/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface FileManager {
    public FileHandler createFromFilePath(String filePath) throws FileNotFoundException;

    public String getFilePathInSandboxDirectory();

    public String getExternalSdCardPath();

    boolean makeDirectoriesIfNotExist(String filePath);

    String appendSdcardPath(String filePath);

    public interface FileHandler {
        void onStartWritingToFile();

        //endregion

        void writeShort(short value) throws IOException;

        void writeInt(int value) throws IOException;

        void writeByte(byte value) throws IOException;

        void writeByteArray(byte[] bytes) throws IOException;

        void writeShortArray(short[] shorts) throws IOException;

        void flush() throws IOException;

        void close() throws IOException;

        String getFilePath();

        DataOutputStream getOutputStream();
    }
}
