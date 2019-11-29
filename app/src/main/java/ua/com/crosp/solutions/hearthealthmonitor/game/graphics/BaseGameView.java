package ua.com.crosp.solutions.hearthealthmonitor.game.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.SurfaceListener;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

public abstract class BaseGameView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int DEFAULT_COUNT_DOWN_TIME = 5;
    private final TouchInputHandler mTouchInputHandler;
    private final KeyboardInputHandler mKeyboardInputHandler;
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private CanvasSize mCurrentCanvasSize;
    private SurfaceListener mSurfaceListener;

    public BaseGameView(Context context, TouchInputHandler touchInputHandler, KeyboardInputHandler keyboardInputHandler, SurfaceListener surfaceListener) {
        super(context);
        mKeyboardInputHandler = keyboardInputHandler;
        mTouchInputHandler = touchInputHandler;
        mSurfaceHolder = getHolder();
        mSurfaceListener = surfaceListener;
        mSurfaceHolder.addCallback(this);
    }

    public BaseGameView(Context context, SurfaceListener surfaceListener) {
        this(context, null, null, surfaceListener);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("SURFACE", "SURFACE CREATED");
        mSurfaceListener.surfaceCreated();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("SURFACE", "SURFACE CHANGED");
        mCurrentCanvasSize = new CanvasSize(width, height);
        mSurfaceListener.surfaceChanged(format, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("SURFACE", "SURFACE DESTROYED");
        mSurfaceListener.surfaceDestroyed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mTouchInputHandler == null || mTouchInputHandler.handleMotionEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mKeyboardInputHandler == null || mKeyboardInputHandler.handleKeyDown(keyCode, event);
    }


    public void shouldStop() {
        mSurfaceHolder.removeCallback(this);
    }

    public void shouldStart() {
    }

    public int provideCountDownTime() {
        return DEFAULT_COUNT_DOWN_TIME;
    }

    public CanvasSize getCanvasSize() {
        return mCurrentCanvasSize;
    }

    public Canvas prepareAndGetCanvas() {
        while (!mSurfaceHolder.getSurface().isValid()) {
            // Do nothing
            Logger.debug("WAITING FOR CANVAS");
        }
        mCanvas = mSurfaceHolder.lockCanvas();
        return mCanvas;
    }

    public void releaseCanvas() {
        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
    }

}
