package ua.com.crosp.solutions.hearthealthmonitor.game;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseSingleFragmentActivity;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public abstract class BaseFullscreenActivity extends BaseSingleFragmentActivity {

    //================================================================================
    // Template methods
    //================================================================================
    public abstract int provideOrientation();

    //================================================================================
    // Lifecycle callbacks
    //================================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //================================================================================
    // Class methods
    //================================================================================

    protected void setFullscreenActivity() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //noinspection ResourceType
        setRequestedOrientation(provideOrientation());
    }
    //================================================================================
    // Hook methods implementation
    //================================================================================

    @Override
    public void beforeSetContent() {
        super.beforeSetContent();
        setFullscreenActivity();
    }

    //================================================================================
    // Override for layout inflating
    //================================================================================

    @Override
    public int getMainLayoutId() {
        return R.layout.activity_base_fullscreen;
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.content_motivationl_mode;
    }

    @Override
    public int getFragmentContainerLayoutId() {
        return R.id.frame_layout_fragment_container;
    }

    @Override
    public int getContentContainerId() {
        return R.id.linear_layout_content;
    }

}
