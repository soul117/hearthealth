package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler;

import android.view.MotionEvent;

import java.util.Queue;
import java.util.function.Predicate;

import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.Input;

public interface TouchInputHandler {
    public boolean handleMotionEvent(MotionEvent motionEvent);

    public Queue<Input.TouchEvent> handleTouchEventsByType(int type);

    public Queue<Input.TouchEvent> handleTouchEventsByPredicate(Predicate<Input.TouchEvent> predicate);


}
