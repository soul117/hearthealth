package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.write;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;

public class AndroidSimpleFileHandler implements FileManager.FileHandler {
    public static final int BYTES_IN_SHORT = 2;
    protected final String mFilePath;
    protected DataOutputStream mFileOutputStream;
    private byte[] mReusableByteBuffer = new byte[BYTES_IN_SHORT];

    public AndroidSimpleFileHandler(String filePath, DataOutputStream fileOutputStream) {
        mFileOutputStream = fileOutputStream;
        mFilePath = filePath;
    }

    //region Factory methods
    public static FileManager.FileHandler createFromSimpleStream(String path) throws FileNotFoundException {
        return new AndroidSimpleFileHandler(path, new DataOutputStream(new FileOutputStream(path)));
    }

    public static FileManager.FileHandler createFromBufferedStream(String path) throws FileNotFoundException {
        return new AndroidSimpleFileHandler(path, new DataOutputStream(new FileOutputStream(path)));
    }

    @Override
    public void onStartWritingToFile() {

    }

    @Override
    public void writeShort(short value) throws IOException {
        byte[] bytesInShort = short2Byte(value);
        // Write upper part
        writeByte(bytesInShort[0]);
        // Write lower part
        writeByte(bytesInShort[1]);
    }

    //endregion

    @Override
    public void writeInt(int value) throws IOException {
        mFileOutputStream.write(value);
    }


    @Override
    public void writeByte(byte value) throws IOException {
        mFileOutputStream.writeByte(value);
    }

    @Override
    public void writeByteArray(byte[] bytes) throws IOException {
        mFileOutputStream.write(bytes);
    }

    @Override
    public void writeShortArray(short[] shorts) throws IOException {
        mFileOutputStream.write(short2byteArray(shorts));
    }


    @Override
    public void flush() throws IOException {
        mFileOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        mFileOutputStream.close();
    }

    @Override
    public String getFilePath() {
        return mFilePath;
    }

    @Override
    public DataOutputStream getOutputStream() {
        return mFileOutputStream;
    }

    public byte[] short2Byte(short value) {
        mReusableByteBuffer[0] = (byte) (value & 0xff);
        mReusableByteBuffer[1] = (byte) ((value >> 8) & 0xff);
        return mReusableByteBuffer;
    }

    public byte[] short2byteArray(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            byte[] bytesInShort = short2Byte(sData[i]);
            bytes[i * 2] = bytesInShort[0];
            bytes[(i * 2) + 1] = bytesInShort[1];
        }
        return bytes;
    }

    protected static void writeIntToRAFile(final RandomAccessFile output, final int value) throws IOException {
        output.write(value);
        output.write(value >> 8);
        output.write(value >> 16);
        output.write(value >> 24);
    }

    protected static void writeShortToRAFile(final RandomAccessFile output, final short value) throws IOException {
        output.write(value);
        output.write(value >> 8);
    }

    protected static void writeStringToRAFile(final RandomAccessFile output, final String value) throws IOException {
        for (int i = 0; i < value.length(); i++) {
            output.write(value.charAt(i));
        }
    }
}