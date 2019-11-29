package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Shape2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.VectorGeometry;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameSettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameUpdateStateBundle;

/**
 * Created by Alexander Molochko on 9/3/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface GameFallingBall {
    public static final int DIRECTION_STRAIGHT = 0;
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;

    void setFallDirectionToObject(GameScreen gameScreen, @FallingDirection int fallingDirection, Point2d objectTo);


    @IntDef({DIRECTION_STRAIGHT, DIRECTION_LEFT, DIRECTION_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FallingDirection {
    }

    @ColorInt
    int getColor();

    public float getCenterX();

    public float getCenterY();


    void updateState(GameUpdateStateBundle updateStateBundle, GameSettingsBundle gameSettingsBundle);

    public void decreaseYVelocity(float velocity);

    public void decreaseXVelocity(float velocity);

    public void increaseXVelocity(float velocity);

    public void increaseYVelocity(float velocity);

    void setVelocityVector(VectorGeometry.VelocityVector velocityVector);

    void setBoost(float value);

    void draw(GraphicsRenderer graphicsRenderer);

    boolean isIntersecting(Shape2d leftBasket);

    boolean hasSameColor(Shape2d leftBasket);

    boolean wentOutOfBoundaries(GameScreen gameScreen);

    float getRight();

    float getLeft();

    @FallingDirection
    int getFallingDirection();

    void setFallDirection(@FallingDirection int fallingDirection);


}
