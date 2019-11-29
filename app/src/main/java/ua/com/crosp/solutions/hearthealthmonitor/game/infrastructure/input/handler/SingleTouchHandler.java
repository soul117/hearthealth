package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler;

import android.view.MotionEvent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.datastructure.Pool;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.Input.TouchEvent;

public class SingleTouchHandler implements TouchInputHandler {
    public static final int TOUCH_EVENTS_MAX_COUNT = 100;
    boolean isTouched;
    int touchX;
    int touchY;
    Queue<TouchEvent> touchEventsQueue;
    Pool<TouchEvent> touchEventPool;
    Queue<TouchEvent> touchEventReturnBuffer = new LinkedList<>();
    float scaleX;
    float scaleY;

    Pool.PoolObjectFactory<TouchEvent> poolFactory = new Pool.PoolObjectFactory<TouchEvent>() {
        public TouchEvent createObject() {
            return new TouchEvent();
        }
    };

    public SingleTouchHandler() {
        this(1, 1);
    }

    public SingleTouchHandler(float scaleX, float scaleY) {
        touchEventPool = new Pool<>(poolFactory, TOUCH_EVENTS_MAX_COUNT);
        this.scaleX = scaleX;
        touchEventsQueue = new CircularFifoQueue<>(TOUCH_EVENTS_MAX_COUNT);
        this.scaleY = scaleY;
    }


    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            if (pointer == 0)
                return isTouched;
            else
                return false;
        }
    }

    public int getTouchX(int pointer) {
        synchronized (this) {
            return touchX;
        }
    }

    public int getTouchY(int pointer) {
        synchronized (this) {
            return touchY;
        }
    }

    public Queue<TouchEvent> getTouchEvents() {
        synchronized (this) {
            touchEventReturnBuffer.clear();
            for (int i = 0; i < touchEventsQueue.size(); i++) {
                TouchEvent currentTouchEvent = touchEventsQueue.remove();
                touchEventPool.free(currentTouchEvent);
                touchEventReturnBuffer.add(currentTouchEvent);
            }
            return touchEventReturnBuffer;
        }
    }

    @Override
    public boolean handleMotionEvent(MotionEvent motionEvent) {
        synchronized (this) {
            TouchEvent touchEvent = null;
            try {
                touchEvent = touchEventPool.newObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (touchEvent != null) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchEvent.type = TouchEvent.TOUCH_DOWN;
                        isTouched = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                        isTouched = true;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        touchEvent.type = TouchEvent.TOUCH_UP;
                        isTouched = false;
                        break;
                }

                touchEvent.x = touchX = (int) (motionEvent.getX() * scaleX);
                touchEvent.y = touchY = (int) (motionEvent.getY() * scaleY);
                touchEventsQueue.add(touchEvent);
            }
            return true;
        }
    }

    @Override
    public Queue<TouchEvent> handleTouchEventsByType(int type) {
        synchronized (this) {
            touchEventReturnBuffer.clear();
            for (int i = 0; i < touchEventsQueue.size(); i++) {
                TouchEvent currentTouchEvent = touchEventsQueue.remove();
                touchEventPool.free(currentTouchEvent);
                if (currentTouchEvent.type == type) {
                    touchEventReturnBuffer.add(currentTouchEvent);
                }
            }
            return touchEventReturnBuffer;
        }
    }

    @Override
    public Queue<TouchEvent> handleTouchEventsByPredicate(Predicate<TouchEvent> predicate) {
        synchronized (this) {
            touchEventReturnBuffer.clear();
            for (int i = 0; i < touchEventsQueue.size(); i++) {
                TouchEvent currentTouchEvent = touchEventsQueue.remove();
                touchEventPool.free(currentTouchEvent);
                if (predicate.test(currentTouchEvent)) {
                    touchEventReturnBuffer.add(currentTouchEvent);
                }
            }
            return touchEventReturnBuffer;
        }
    }

}
