package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileIOHandler {
    public InputStream readAsset(String fileName) throws IOException;

    public InputStream readFile(String fileName) throws IOException;

    public OutputStream writeFile(String fileName) throws IOException;
}
