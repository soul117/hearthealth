package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler;

import android.view.MotionEvent;


import java.util.Queue;
import java.util.function.Predicate;

import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.Input.TouchEvent;

public class IgnoreTouchHandler implements TouchInputHandler {


    @Override
    public boolean handleMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public Queue<TouchEvent> handleTouchEventsByType(int type) {
        return null;
    }

    @Override
    public Queue<TouchEvent> handleTouchEventsByPredicate(Predicate<TouchEvent> predicate) {
        return null;
    }
}
