package ua.com.crosp.solutions.hearthealthmonitor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;

import com.joanzapata.iconify.IconDrawable;

import ua.com.crosp.solutions.hearthealthmonitor.R;

/**
 * Created by Alexander Molochko on 1/16/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FitIconEditText extends AppCompatEditText {

    private static final int DEFAULT_ICON_COLOR = Color.BLACK;
    private static final int DEFAULT_ICON_SIZE = -1;
    // Attribute Variables
    private String mTextIcon;
    private int mIconColor;
    // Variables

    private ViewTreeObserver.OnGlobalLayoutListener mFitIconGlobalLayoutListener;
    private int mIconSize;
    //================================================================================
    // Constructors
    //================================================================================

    public FitIconEditText(Context context) {
        super(context);
        initView();
    }

    public FitIconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public FitIconEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        initView();
    }

    private void parseAttributes(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FitIconEditText, 0, 0);
        mTextIcon = a.getString(R.styleable.FitIconEditText_textIcon);
        mIconColor = a.getColor(R.styleable.FitIconEditText_textIconColor, DEFAULT_ICON_COLOR);
        mIconSize = a.getDimensionPixelSize(R.styleable.FitIconEditText_textIconSize, DEFAULT_ICON_SIZE);
        a.recycle();
    }

    private void initView() {
        mFitIconGlobalLayoutListener = new FitIconTreeViewObservable(this);
        this.getViewTreeObserver()
                .addOnGlobalLayoutListener(mFitIconGlobalLayoutListener);
    }

    public String getTextIcon() {
        return mTextIcon;
    }

    public void setTextIcon(String textIcon) {
        mTextIcon = textIcon;
    }

    private void setDrawableLeftIconFitSize(String textIcon) {
        mTextIcon = textIcon;
        if (mIconSize == DEFAULT_ICON_SIZE) {
            mIconSize = this.getMeasuredHeight() / 2;

        }
        try {
            Drawable iconDrawable = new IconDrawable(getContext(), textIcon)
                    .sizePx(mIconSize)
                    .color(mIconColor);
            this.setCompoundDrawables(iconDrawable, null, null, null);
        } catch (Exception ignore) {

        }


    }

    private void setDrawableLeftIconFitSize() {
        this.setDrawableLeftIconFitSize(mTextIcon);
    }

    private static class FitIconTreeViewObservable implements ViewTreeObserver.OnGlobalLayoutListener {
        private final FitIconEditText mEditText;

        private FitIconTreeViewObservable(FitIconEditText targetEditText) {
            mEditText = targetEditText;
        }


        @Override
        public void onGlobalLayout() {
            mEditText.setDrawableLeftIconFitSize();
            mEditText.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }
    }
}
