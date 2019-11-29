package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view;

import android.content.Context;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.BaseGameView;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.SurfaceListener;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;


/**
 * Created by Alexander Molochko on 2/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CatchTheBallGameView extends BaseGameView {

    public CatchTheBallGameView(Context context, TouchInputHandler touchInputHandler, KeyboardInputHandler keyboardInputHandler, SurfaceListener surfaceListener) {
        super(context, touchInputHandler, keyboardInputHandler, surfaceListener);
    }
}
