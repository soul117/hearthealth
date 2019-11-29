package ua.com.crosp.solutions.hearthealthmonitor.base.view;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import butterknife.ButterKnife;

/**
 * Created by Alexander Molochko on 12/13/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public abstract class BaseSimpleViewGroup extends FrameLayout {
    public BaseSimpleViewGroup(Context context) {
        super(context);
        initView();
    }

    public BaseSimpleViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BaseSimpleViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseSimpleViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    protected void initView() {
        inflateView();
        injectViews();
    }

    private void injectViews() {
        ButterKnife.bind(this, this);
    }

    protected void inflateView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(getViewLayoutId(), this, true);

    }

    //================================================================================
    // Lifecycle callbacks
    //================================================================================
    public abstract int getViewLayoutId();

    protected String getStringByResourceId(int id) {
        return getResources().getString(id);
    }
}
