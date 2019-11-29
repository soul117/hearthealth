package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data;

import android.content.res.Resources;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import ua.com.crosp.solutions.hearthealthmonitor.R;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PEBallGameMode {
    // Constants

    // Variables
    private long mId;
    private String mModeName;
    private String mDescription;
    public static PEBallGameMode.List DEFAULT_GAME_MODES;

    public PEBallGameMode(long id, String modeName, String description) {
        mId = id;
        mModeName = modeName;
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getModeName() {
        return mModeName;
    }

    public void setModeName(String modeName) {
        mModeName = modeName;
    }

    @Override
    public String toString() {
        return mModeName;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PEBallGameMode that = (PEBallGameMode) o;

        return mId == that.mId;

    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }


    public static class List extends ArrayList<PEBallGameMode> {
        public List() {
        }

        public List(@NonNull Collection<? extends PEBallGameMode> c) {
            super(c);
        }

        public PEBallGameMode getModeById(long modeId) {
            for (PEBallGameMode mode : this) {
                if (mode.getId() == modeId) {
                    return mode;
                }
            }
            if (this.size() > 0) {
                return this.get(0);
            } else {
                return getDefaultMode();
            }
        }
    }

    public static PEBallGameMode getDefaultMode() {
        return DEFAULT_GAME_MODES.get(0);
    }

    // TODO fetch from somewhere
    public static void initDefaultGameModes(Resources resources) {
        DEFAULT_GAME_MODES = new List();
        PEBallGameMode activationOnlyMode = new PEBallGameMode(R.id.catch_the_balL_game_mode_activation_only, resources.getString(R.string.pe_game_mode_title_activation_only), resources.getString(R.string.pe_game_mode_description_activation_only));
        DEFAULT_GAME_MODES.add(activationOnlyMode);
        PEBallGameMode activationAndStopMode = new PEBallGameMode(R.id.catch_the_balL_game_mode_activation_and_stop, resources.getString(R.string.pe_game_mode_title_activation_and_stop), resources.getString(R.string.pe_game_mode_description_activation_and_stop));
        DEFAULT_GAME_MODES.add(activationAndStopMode);
    }

    public static List getDefaultGameModes() {
        return DEFAULT_GAME_MODES;
    }

}
