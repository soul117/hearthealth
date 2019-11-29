package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler;

import java.util.ArrayList;
import java.util.List;

import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.datastructure.Pool;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.Input.KeyEvent;


public class AndroidKeyboardHandler implements KeyboardInputHandler {
    boolean[] pressedKeys = new boolean[128];
    Pool<KeyEvent> keyEventPool;
    List<KeyEvent> keyEventsBuffer = new ArrayList<>();
    List<KeyEvent> keyEvents = new ArrayList<>();

    public AndroidKeyboardHandler() {
        Pool.PoolObjectFactory<KeyEvent> factory = new Pool.PoolObjectFactory<KeyEvent>() {
            public KeyEvent createObject() {
                return new KeyEvent();
            }
        };
        keyEventPool = new Pool<>(factory, 100);
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode > 127) {
            return false;
        }
        return pressedKeys[keyCode];
    }

    public List<KeyEvent> getKeyEvents() {
        synchronized (this) {
            int len = keyEvents.size();
            for (int i = 0; i < len; i++) {
                keyEventPool.free(keyEvents.get(i));
            }
            keyEvents.clear();
            keyEvents.addAll(keyEventsBuffer);
            keyEventsBuffer.clear();
            return keyEvents;
        }
    }

    @Override
    public boolean handleKeyDown(int keyCode, android.view.KeyEvent event) {
        if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
            return false;

        synchronized (this) {
            KeyEvent keyEvent = keyEventPool.newObject();
            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = (char) event.getUnicodeChar();
            if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                keyEvent.type = KeyEvent.KEY_DOWN;
                if (keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = true;
            }
            if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
                keyEvent.type = KeyEvent.KEY_UP;
                if (keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = false;
            }
            keyEventsBuffer.add(keyEvent);
        }
        return false;
    }
}
