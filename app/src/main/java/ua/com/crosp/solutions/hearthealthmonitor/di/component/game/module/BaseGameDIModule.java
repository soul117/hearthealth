package ua.com.crosp.solutions.hearthealthmonitor.di.component.game.module;

import android.content.Context;

import javax.inject.Named;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.BaseGameView;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;

import static ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule.CONTEXT_ACTIVITY;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public interface BaseGameDIModule {
    public TouchInputHandler provideTouchInputHandler();

    public KeyboardInputHandler provideKeyboardInputHandler();

    public GraphicsRenderer provideGraphicsRenderer(@Named(CONTEXT_ACTIVITY) Context context);

    public BaseGameView provideBaseGameView(@Named("context.activity") Context context, TouchInputHandler touchInputHandler, KeyboardInputHandler keyboardInputHandler);
}
