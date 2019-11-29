package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.media.audio.rx;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import androidx.annotation.Nullable;

import net.crosp.android.library.ecgaudioprocessor.NativeEcgSignalProcessor;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.AudioRecordingConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.exception.AudioRecordingException;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common.AudioRecordingBuffer;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.AudioFile;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.MathUtils;

public final class RxStreamAudioRecorder implements ReactiveAudioRecorder {

    private static final String TAG = "RxStreamAudioRecorder";
    public static final int CHECK_SUBSCRIBERS_PERIOD = 20000;
    public static final int CHECK_SUBSCRIBERS_DELAY = 5000;
    public static final int DEFAULT_DECIMATION_COEFFICIENT = 12;
    private final AtomicBoolean mIsRecording;
    private Timer mTimer;
    private ExecutorService mExecutorService;
    private ReactiveAudioRecorder.Settings mRecordingSettings;
    private StreamAudioRecordRunnable mCurrentStream;
    private AudioFile mCurrentRecordingFile;
    private RecordingStreamCallback mStreamCallback;


    public RxStreamAudioRecorder(RecordingStreamCallback recordingStreamCallback) {
        // singleton
        mTimer = new Timer();
        mStreamCallback = recordingStreamCallback;
        mIsRecording = new AtomicBoolean(false);
    }

    @Override
    public ReactiveAudioRecorder.Settings getSettings() {
        return mRecordingSettings;
    }

    @Override
    public Flowable<AudioRecordingBuffer> startRecordingStreaming(ReactiveAudioRecorder.Settings settings) {
        if (isStillRecording()) {
            Logger.error("RECORDING STOP IT !");
            stopRecording();
        }
        mRecordingSettings = settings;
        mExecutorService = Executors.newSingleThreadExecutor();
        createTimers();
        if (mIsRecording.compareAndSet(false, true)) {
            return createRecordingFlowable();
        }
        return Flowable.empty();
    }

    protected Flowable<AudioRecordingBuffer> createRecordingFlowable() {
        StreamAudioRecordRunnable audioRecordRunnable = new StreamAudioRecordRunnable(mRecordingSettings);
        mExecutorService.execute(audioRecordRunnable);
        mCurrentStream = audioRecordRunnable;
        return audioRecordRunnable;
    }

    @Override
    public boolean isStillRecording() {
        return mIsRecording.get() && mCurrentStream != null;
    }

    @Override
    public void stopRecording() {
        mIsRecording.compareAndSet(true, false);
        stopTimers();
        if (mExecutorService != null) {
            mExecutorService.shutdown();
            mCurrentStream = null;
            mExecutorService = null;
        }
        mStreamCallback.onRecordingStopped();
    }

    private void createTimers() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
    }

    private void stopTimers() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void pauseRecording() {
        mCurrentStream.pause();
        mStreamCallback.onRecordingPaused();
    }

    @Override
    public void resumeRecording() {
        mCurrentStream.resume();
    }

    @Nullable
    @Override
    public Flowable<AudioRecordingBuffer> getCurrentStream() {
        return mCurrentStream;
    }

    @Override
    public boolean isPaused() {
        return mCurrentStream.isPaused();
    }

    @Override
    public void onUnsubscribeFromStream() {
        if (mCurrentStream != null && mCurrentStream.isAnySubscribersLeft()) {

        } else {
            stopRecording();
        }
    }

    private class StreamAudioRecordRunnable extends Flowable<AudioRecordingBuffer> implements Runnable {

        private final AudioRecord mAudioRecord;
        private final ReactiveAudioRecorder.Settings mSettings;
        private List<Subscriber<? super AudioRecordingBuffer>> mSubscribers = new ArrayList<>();
        private AudioRecordingBuffer mAudioRecordingBuffer;
        private short[] mTempAudioBuffer;
        private final Object mPauseLock;
        private boolean mPaused;
        private int mReadBufferSizeInShorts;

        StreamAudioRecordRunnable(ReactiveAudioRecorder.Settings settings) {
            mSettings = settings;
            // Init variables
            mPauseLock = new Object();
            mAudioRecord = constructAudioRecordFromSettings();
        }

        private AudioRecord constructAudioRecordFromSettings() {
            int realBufferSize = mSettings.getRealBufferSize();
            mReadBufferSizeInShorts = mSettings.getReadBufferSize();
            mSettings.setReadBufferSize(mReadBufferSizeInShorts);
            mAudioRecordingBuffer = AudioRecordingBuffer.initWithBufferSize(mReadBufferSizeInShorts);
            mTempAudioBuffer = new short[mReadBufferSizeInShorts];
            Logger.error("getAudioSource " + mSettings.getAudioSource());
            Logger.error("getSampleRate " + mSettings.getSampleRate());
            Logger.error("getChannelCount " + mSettings.getChannelCount());
            Logger.error("getAudiopEncodingFormat " + mSettings.getAudiopEncodingFormat());
            Logger.error("mBufferSizeInBytes " + mReadBufferSizeInShorts);
            return new AudioRecord(
                    mSettings.getAudioSource(),
                    mSettings.getSampleRate(),
                    mSettings.getChannelCount(),
                    mSettings.getAudiopEncodingFormat(),
                    realBufferSize
            );
        }


        private void removeAllNullableSubscribers() {
            try {
                Logger.error("SUBSCRIBERS COUNT " + mSubscribers.size() + " " + Arrays.toString(mSubscribers.toArray()));
                for (ListIterator<Subscriber<? super AudioRecordingBuffer>> iter = mSubscribers.listIterator(); iter.hasNext(); ) {
                    Disposable subscriber = (Disposable) iter.next();
                    if (subscriber.isDisposed()) {
                        Logger.error("REMOVING SUBSCRIBER !!! ");
                        iter.remove();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void notifyAllSubscribersOnComplete() {
            for (Subscriber<? super AudioRecordingBuffer> subscriber : mSubscribers) {
                subscriber.onComplete();
            }
        }

        private void notifyAllSubscribers(AudioRecordingBuffer audioBuffer) {
            for (Subscriber<? super AudioRecordingBuffer> subscriber : mSubscribers) {
                subscriber.onNext(audioBuffer);
            }
        }

        @Override
        protected void subscribeActual(Subscriber<? super AudioRecordingBuffer> newSubscriber) {
            mSubscribers.add(newSubscriber);
        }

        private void notifyAllSubscribersAboutError(Throwable error) {
            for (Subscriber<? super AudioRecordingBuffer> subscriber : mSubscribers) {
                subscriber.onError(error);
            }
        }

        private void onRecordingStarted() {
            mTimer.schedule(new CheckSubscribersTimerTask(), CHECK_SUBSCRIBERS_DELAY, CHECK_SUBSCRIBERS_PERIOD);
            mStreamCallback.onRecordingStarted();
        }

        /**
         * Call this on pause.
         */
        public void pause() {
            synchronized (mPauseLock) {
                mPaused = true;
            }
        }

        /**
         * Call this on resume.
         */
        public void resume() {
            synchronized (mPauseLock) {
                mPaused = false;
                mPauseLock.notifyAll();
            }
        }

        @Override
        public void run() {
            setThreadAudioRecordingPriority();
            int readResults;

            try {
                if (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                    try {
                        mAudioRecord.startRecording();
                        onRecordingStarted();
                    } catch (IllegalStateException e) {
                        Logger.error("startRecordingStreaming fail: " + e.getMessage());
                        notifyAllSubscribersAboutError(e);
                        return;
                    }
                    while (mIsRecording.get()) {
                        synchronized (mPauseLock) {
                            while (mPaused) {
                                try {
                                    mPauseLock.wait();
                                } catch (InterruptedException e) {
                                    notifyAllSubscribersAboutError(e);
                                }
                            }
                        }

                        readResults = mAudioRecord.read(mTempAudioBuffer, 0, mReadBufferSizeInShorts);
                        if (readResults > 0) {
                            mAudioRecordingBuffer.setVoltageBufferContent(mTempAudioBuffer);
                            notifyAllSubscribers(mAudioRecordingBuffer);
                        } else {
                            onError(readResults);
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                notifyAllSubscribersOnComplete();
                try {
                    if (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                        mAudioRecord.stop();
                    }
                    mAudioRecord.release();
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
        }

        private void setThreadAudioRecordingPriority() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
        }

        private void onError(int errorCode) {
            if (errorCode == AudioRecord.ERROR_INVALID_OPERATION) {
                notifyAllSubscribersAboutError(new AudioRecordingException("Record fail: ERROR_INVALID_OPERATION"));
            } else if (errorCode == AudioRecord.ERROR_BAD_VALUE) {
                notifyAllSubscribersAboutError(new AudioRecordingException("Record fail: ERROR_BAD_VALUE"));
            }
        }

        public boolean isPaused() {
            return mPaused;
        }

        public boolean isAnySubscribersLeft() {
            return !mSubscribers.isEmpty();
        }
    }

    private class CheckSubscribersTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCurrentStream != null) {
                mCurrentStream.removeAllNullableSubscribers();
                if (!mCurrentStream.isAnySubscribersLeft()) {
//                    stopRecording();
                }
            }
        }
    }

    public static class Settings implements ReactiveAudioRecorder.Settings {
        // Constants
        public static final int DEFAULT_SAMPLE_RATE = AudioRecordingConfiguration.SAMPLE_RATE;
        public static final int DEFAULT_BUFFER_SIZE = AudioRecordingConfiguration.READ_BUFFER_SIZE;
        public static final int DEFAULT_DECIMATION_COEFFICIENT = 1;

        // Settings variables, should be private and final to prevent modifications
        private int mEncodingFormat;
        private int mSampleRate;
        private int mAudioSource;
        private int mDecimationCoefficient;
        private int mChannelCount;
        private int mReadBufferSize;
        private int mRealBufferSize;

        public Settings() {
            mDecimationCoefficient = DEFAULT_DECIMATION_COEFFICIENT;
            mAudioSource = MediaRecorder.AudioSource.MIC;
            mSampleRate = DEFAULT_SAMPLE_RATE;
            mEncodingFormat = AudioFormat.ENCODING_PCM_16BIT;
            mChannelCount = AudioFormat.CHANNEL_IN_MONO;
            mRealBufferSize = DEFAULT_BUFFER_SIZE;
            setReadBufferSize(DEFAULT_BUFFER_SIZE);
        }

        public Settings(int audioSource, int encoding, int sampleRate, int channelCount, int realBufferSize, int readBufferSize, int decimationCoefficient) {
            mEncodingFormat = encoding;
            mSampleRate = sampleRate;
            mAudioSource = audioSource;
            mChannelCount = channelCount;
            mRealBufferSize = realBufferSize;
            mDecimationCoefficient = decimationCoefficient;
            setReadBufferSize(readBufferSize);
        }

        @Override
        public void setEncodingFormat(int encodingFormat) {
            mEncodingFormat = encodingFormat;
        }

        @Override
        public void setSampleRate(int sampleRate) {
            mSampleRate = sampleRate;
        }

        @Override
        public void setAudioSource(int audioSource) {
            mAudioSource = audioSource;
        }

        @Override
        public void setChannelCount(int channelCount) {
            mChannelCount = channelCount;
        }

        public void setRealBufferSize(int realBufferSize) {
            mRealBufferSize = realBufferSize;
        }

        @Override
        public int getSampleRate() {
            return mSampleRate;
        }

        @Override
        public int getAudioSource() {
            return mAudioSource;
        }

        @Override
        public void setReadBufferSize(int readAudioBufferSize) {
            readAudioBufferSize = (int) MathUtils.findNearestFactorValue(readAudioBufferSize, mDecimationCoefficient);
            mReadBufferSize = readAudioBufferSize;
        }

        @Override
        public NativeEcgSignalProcessor.EcgProcessingOptions toEcgProcessingOptions() {
            NativeEcgSignalProcessor.EcgProcessingOptions ecgProcessingOptions = new NativeEcgSignalProcessor.EcgProcessingOptions();
            ecgProcessingOptions.decimationCoefficient = DEFAULT_DECIMATION_COEFFICIENT;
            ecgProcessingOptions.readBlockSize = this.mReadBufferSize;
            ecgProcessingOptions.samplingRate = this.mSampleRate;
            return ecgProcessingOptions;
        }

        @Override
        public int getAudiopEncodingFormat() {
            return mEncodingFormat;
        }

        @Override
        public int getChannelCount() {
            return mChannelCount;
        }

        @Override
        public int getRealBufferSize() {
            return mRealBufferSize;
        }

        @Override
        public int getReadBufferSize() {
            return mReadBufferSize;
        }

        public static class Builder {
            private int mReadBufferSize;
            private int mEncoding;
            private int mSampleRate;
            private int mAudioSource;
            private int mChannelCount;
            private int mRealBufferSize;
            private int mDecimationCoefficient;

            public Builder() {
                // Init defaults
                mAudioSource = MediaRecorder.AudioSource.MIC;
                mSampleRate = DEFAULT_SAMPLE_RATE;
                mEncoding = AudioFormat.ENCODING_PCM_16BIT;
                mChannelCount = AudioFormat.CHANNEL_IN_MONO;
                mRealBufferSize = DEFAULT_BUFFER_SIZE;
                mReadBufferSize = DEFAULT_BUFFER_SIZE;
                mDecimationCoefficient = DEFAULT_DECIMATION_COEFFICIENT;
            }

            public Builder mono() {
                mChannelCount = AudioFormat.CHANNEL_IN_MONO;
                return this;
            }

            public Builder stereo() {
                mChannelCount = AudioFormat.CHANNEL_IN_STEREO;
                return this;
            }

            public Builder micAudioSource() {
                mAudioSource = MediaRecorder.AudioSource.MIC;
                return this;
            }

            public Builder camRecorderAudioSource() {
                mAudioSource = MediaRecorder.AudioSource.CAMCORDER;
                return this;
            }

            public Builder sampleRate(int sampleRate) {
                mSampleRate = sampleRate;
                return this;
            }

            public Builder encodingFormat(int encoding) {
                mEncoding = encoding;
                return this;
            }

            public Builder readBufferSize(int bufferSize) {
                mReadBufferSize = bufferSize;
                return this;
            }

            public Builder decimationCoefficient(int decimationCoeff) {
                mDecimationCoefficient = decimationCoeff;
                return this;
            }

            public Builder realBufferSize(int bufferSize) {
                mRealBufferSize = bufferSize;
                return this;
            }


            public ReactiveAudioRecorder.Settings build() {
                return new Settings(mAudioSource, mEncoding, mSampleRate, mChannelCount, mRealBufferSize, mReadBufferSize, mDecimationCoefficient);
            }
        }
    }

    public static interface RecordingStreamCallback {
        void onRecordingStopped();

        void onRecordingStarted();

        void onRecordingPaused();
    }
}
