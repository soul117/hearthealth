package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.CountdownGameDrawer;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.AndroidDrawingPaint;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.BaseGameView;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.DrawingPaint;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;

/**
 * Created by Alexander Molochko on 10/7/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class MotivationalModeCountdownDrawer extends CountdownGameDrawer {
    public static final int MS_IN_SECOND = 1000;
    public static final int DELAY = 200;
    public static final int FONT_SIZE_COEFFICIENT = 3;
    private PublishSubject<Boolean> mDrawingSubject = PublishSubject.create();
    private GraphicsRenderer mGraphicDrawer;
    private int mCurrentTimeInSeconds;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private BaseGameView mGameView;
    private DrawingPaint mTextDrawingPaint;
    private Rect mRect = new Rect();
    private boolean mFontSizeIsAlreadySet = false;

    @Inject
    public MotivationalModeCountdownDrawer(Params params, GraphicsRenderer graphicDrawer) {
        super(params);
        mGraphicDrawer = graphicDrawer;
        mCurrentTimeInSeconds = params.timeInSeconds;
        initTimer();
        initTextDrawingPaint();
    }


    private void initTimer() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                drawTimeSeconds();
                mCurrentTimeInSeconds--;
                if (mCurrentTimeInSeconds < 0) {
                    stopTimersAndComplete();
                }
            }

        };
    }

    private void stopTimersAndComplete() {
        stopDrawing();
        mDrawingSubject.onNext(true);
        mDrawingSubject.onComplete();

    }

    private void initTextDrawingPaint() {
        mTextDrawingPaint = new AndroidDrawingPaint();
        mTextDrawingPaint.setColor(mParams.timeTextColor);
        mTextDrawingPaint.setTextSize(mParams.timeTextSize);
        mTextDrawingPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void drawTimeSeconds() {
        setGraphicDrawer();
        setFontSize();
        mGraphicDrawer.clear(mParams.backgroundColor);
        drawTextCenter(mGraphicDrawer, mTextDrawingPaint, String.valueOf(mCurrentTimeInSeconds));
        releaseDrawer();
    }

    private void drawTextCenter(GraphicsRenderer canvas, DrawingPaint paint, String text) {
        canvas.getClipBounds(mRect);
        int cHeight = canvas.getCanvasHeight();
        int cWidth = canvas.getCanvasWidth();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), mRect);
        float x = cWidth / 2f - mRect.width() / 2f - mRect.left;
        float y = cHeight / 2f + mRect.height() / 2f - mRect.bottom;
        canvas.drawText(text, x, y, paint);
    }

    private void setFontSize() {
        setFontSizeFromHeight(FONT_SIZE_COEFFICIENT, mGraphicDrawer.getCanvasHeight(), mTextDrawingPaint, String.valueOf(mCurrentTimeInSeconds));
    }

    private void setFontSizeFromHeight(float coefficient, float canvasHeight, DrawingPaint paint,
                                       String text) {

        if (!mFontSizeIsAlreadySet) {
            // Pick a reasonably large value for the test. Larger values produce
            // more accurate results, but may cause problems with hardware
            // acceleration. But there are workarounds for that, too; refer to
            // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
            final float testTextSize = 48f;
            final float desiredHeight = canvasHeight / coefficient;
            // Get the bounds of the text, using our testTextSize.
            paint.setTextSize(testTextSize);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);

            // Calculate the desired size as a proportion of our testTextSize.
            float desiredTextSize = testTextSize * desiredHeight / bounds.height();

            // Set the paint for that size.
            paint.setTextSize(desiredTextSize);
            mFontSizeIsAlreadySet = true;
        }
    }

    @Override
    public PublishSubject<Boolean> startDrawingCountDown(BaseGameView gameView) {
        mGameView = gameView;
        startTimer();
        return mDrawingSubject;
    }

    @Override
    public void stopDrawing() {
        mTimerTask.cancel();
        mTimer.cancel();
    }

    private void setGraphicDrawer() {
        mGraphicDrawer.setCurrentCanvas(mGameView.prepareAndGetCanvas());
    }

    private void releaseDrawer() {
        mGameView.releaseCanvas();
    }

    private void startTimer() {
        mTimer.schedule(mTimerTask, DELAY, MS_IN_SECOND);
    }


}
