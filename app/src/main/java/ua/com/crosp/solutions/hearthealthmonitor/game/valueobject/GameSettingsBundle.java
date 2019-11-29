package ua.com.crosp.solutions.hearthealthmonitor.game.valueobject;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Molochko on 10/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GameSettingsBundle {
    private Map<String, Object> mGameSettingsMap = new HashMap<>();

    public GameSettingsBundle(Map<String, Object> gameSettingsMap) {
        mGameSettingsMap = gameSettingsMap;
    }

    public GameSettingsBundle() {


    }

    public void initWithSettings(Map<String, Object> gameSettingsMap) {
        mGameSettingsMap.putAll(gameSettingsMap);
    }

    public <T> T getSettingsValue(String key) {
        return (T) mGameSettingsMap.get(key);
    }

    public <T> void putSettingsValue(String key, T value) {
        mGameSettingsMap.put(key, value);
    }

}
