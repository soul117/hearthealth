package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.AudioHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.MusicHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.SoundHandler;

public class AndroidAudioHandler implements AudioHandler {
    public static final int MAX_STREAMS = 20;
    AssetManager mAssetManager;
    SoundPool mSoundPool;

    public AndroidAudioHandler(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.mAssetManager = activity.getAssets();
        AudioAttributes audioAttributes = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);

        }


    }

    public MusicHandler newMusic(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = mAssetManager.openFd(filename);
            return new AndroidMusicHandler(assetDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load music '" + filename + "'");
        }
    }

    public SoundHandler newSound(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = mAssetManager.openFd(filename);
            int soundId = mSoundPool.load(assetDescriptor, 0);
            return new AndroidSoundHandler(mSoundPool, soundId);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound '" + filename + "'");
        }
    }
}
