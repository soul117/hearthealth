package ua.com.crosp.solutions.hearthealthmonitor.game.engine;

import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.BaseGameView;

/**
 * Created by Alexander Molochko on 2/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public abstract class CountdownGameDrawer {
    protected Params mParams;

    protected CountdownGameDrawer(Params params) {
        mParams = params;
    }

    public abstract PublishSubject<Boolean> startDrawingCountDown(BaseGameView gameView);

    public abstract void stopDrawing();

    public Params getParams() {
        return mParams;
    }

    public void setParams(Params params) {
        mParams = params;
    }

    public static class Params {
        public int backgroundColor;
        public int timeTextColor;
        public int timeTextSize;
        public int timeInSeconds;

        public Params() {
        }

        public Params(int backgroundColor, int timeTextColor, int timeTextColorSize, int timeInSeconds) {
            this.backgroundColor = backgroundColor;
            this.timeTextColor = timeTextColor;
            this.timeTextSize = timeTextColorSize;
            this.timeInSeconds = timeInSeconds;
        }
    }
}
