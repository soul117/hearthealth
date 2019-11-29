package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio;

public interface AudioHandler {
    public MusicHandler newMusic(String filename);

    public SoundHandler newSound(String filename);
}
