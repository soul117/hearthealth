package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.MusicHandler;

public class AndroidMusicHandler implements MusicHandler, OnCompletionListener {
    MediaPlayer mMediaPlayer;
    boolean mIsPrepared = false;

    public AndroidMusicHandler(AssetFileDescriptor assetDescriptor) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            mMediaPlayer.prepare();
            mIsPrepared = true;
            mMediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    public void dispose() {
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    public boolean isLooping() {
        return mMediaPlayer.isLooping();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public boolean isStopped() {
        return !mIsPrepared;
    }

    public void pause() {
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
    }

    public void play() {
        if (mMediaPlayer.isPlaying())
            return;
        try {
            synchronized (this) {
                if (!mIsPrepared)
                    mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLooping(boolean isLooping) {
        mMediaPlayer.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        mMediaPlayer.setVolume(volume, volume);
    }

    public void stop() {
        mMediaPlayer.stop();
        synchronized (this) {
            mIsPrepared = false;
        }
    }

    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            mIsPrepared = false;
        }
    }
}
