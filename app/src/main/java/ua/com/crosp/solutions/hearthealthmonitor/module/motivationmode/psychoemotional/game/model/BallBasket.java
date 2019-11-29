package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Rectangle2d;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BallBasket extends Rectangle2d {

    private int mCorrectBalls = 0;
    private int mIncorrectBalls = 0;

    public BallBasket(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        renderer.drawImageColored(getLeft(), getTop(), getRight(), getBottom(), R.drawable.open_cardboard_box, getColor());
    }

    public void increaseCorrectBallsNumber() {
        mCorrectBalls++;
    }

    public void increaseInCorrectBallsNumber() {
        mIncorrectBalls++;
    }

    public void clearBallsStatistic() {
        mCorrectBalls = 0;
        mIncorrectBalls = 0;
    }

    public int getIncorrectBalls() {
        return mIncorrectBalls;
    }

    public int getCorrectBalls() {
        return mCorrectBalls;
    }
}
