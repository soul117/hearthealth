package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.visualization;

/**
 * Interface for audio recorder
 */
public interface IAudioRecorder {
    void startRecord();

    void finishRecord();

    boolean isRecording();

    public AudioRecorderVisualization recordingCallback(AudioRecorderVisualization.RecordingCallback recordingCallback);
}
