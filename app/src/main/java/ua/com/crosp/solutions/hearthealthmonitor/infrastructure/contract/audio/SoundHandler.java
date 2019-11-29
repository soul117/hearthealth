package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio;

public interface SoundHandler {
    public void play(float volume);

    public void dispose();
}
