package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.visualization;

import com.cleveroad.audiovisualization.DbmHandler;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.math.Complex;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.math.FFT;

public class AudioRecordingDbmHandler extends DbmHandler<byte[]> implements AudioRecorderVisualization.RecordingCallback {

    private static final float MAX_DB_VALUE = 170;
    public static final double AMPLIFICATION_COEFFICIENT = 150.0;

    private float[] dbs;
    private float[] allAmps;

    @Override
    protected void onDataReceivedImpl(byte[] bytes, int layersCount, float[] dBmArray, float[] ampsArray) {

        final int bytesPerSample = 2; // As it is 16bit PCM
        Complex[] fft = new Complex[bytes.length / bytesPerSample];
        for (int index = 0, floatIndex = 0; index < bytes.length - bytesPerSample + 1; index += bytesPerSample, floatIndex++) {
            double sample = 0;
            for (int b = 0; b < bytesPerSample; b++) {
                int v = bytes[index + b];
                if (b < bytesPerSample - 1) {
                    v &= 0xFF;
                }
                sample += v << (b * 8);
            }
            double sample32 = AMPLIFICATION_COEFFICIENT * (sample / 32768.0);
            fft[floatIndex] = new Complex(sample32, 0);
        }
        fft = FFT.fft(fft);
        // calculate dBs and amplitudes
        int dataSize = fft.length / 2 - 1;
        if (dbs == null || dbs.length != dataSize) {
            dbs = new float[dataSize];
        }
        if (allAmps == null || allAmps.length != dataSize) {
            allAmps = new float[dataSize];
        }

        for (int i = 0; i < dataSize; i++) {
            dbs[i] = (float) fft[i].abs();
            float k = 1;
            if (i == 0 || i == dataSize - 1) {
                k = 2;
            }
            float re = (float) fft[2 * i].re();
            float im = (float) fft[2 * i + 1].im();
            float sqMag = re * re + im * im;
            allAmps[i] = (float) (k * Math.sqrt(sqMag) / dataSize);
        }
        int size = dbs.length / layersCount;
        for (int i = 0; i < layersCount; i++) {
            int index = (int) ((i + 0.5f) * size);
            float db = dbs[index];
            float amp = allAmps[index];
            dBmArray[i] = db > MAX_DB_VALUE ? 1 : db / MAX_DB_VALUE;
            ampsArray[i] = amp;
        }
    }

    public void stop() {
        try {
            calmDownAndStopRendering();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDataReady(byte[] data) {
        try {
            onDataReceived(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}