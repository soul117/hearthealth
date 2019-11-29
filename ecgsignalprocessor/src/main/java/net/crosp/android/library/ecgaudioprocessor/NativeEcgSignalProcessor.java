package net.crosp.android.library.ecgaudioprocessor;


public class NativeEcgSignalProcessor implements EcgSignalProcessorContract {
    static {
        System.loadLibrary("ecg-signal-processor");
    }

    public static NativeEcgSignalProcessor initLibraryWithDefaultParameters(EcgProcessingOptions ecgProcessingOptions) {
        NativeEcgSignalProcessor libraryProxy = new NativeEcgSignalProcessor();
        libraryProxy.init(ecgProcessingOptions.samplingRate, ecgProcessingOptions.readBlockSize, ecgProcessingOptions.decimationCoefficient, ecgProcessingOptions.sandboxPath);
        return libraryProxy;
    }


    private NativeEcgSignalProcessor() {

    }

    public native void release();

    private native void init(int samplingRate, int readBufferSize, int decimationCoefficient, String sandboxPath);
    @Override
    public native short[] demodulate(short[] readBuffer);

    @Override
    public native int detectRPeaks(short[] result);


    public static class EcgProcessingOptions {
        public int samplingRate;
        public int readBlockSize;
        public int decimationCoefficient;
        public String sandboxPath;
    }
}
