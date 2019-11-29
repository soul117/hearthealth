package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common;

import android.media.SoundPool;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.SoundHandler;


public class AndroidSoundHandler implements SoundHandler {
    int soundId;
    SoundPool soundPool;

    public AndroidSoundHandler(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(soundId);
    }
}

