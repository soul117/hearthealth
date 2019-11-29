package ua.com.crosp.solutions.hearthealthmonitor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import ua.com.crosp.solutions.hearthealthmonitor.R;

/**
 * Created by Alexander Molochko on 1/16/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class NavigationDrawerListItem extends LinearLayout {
    // Views
    private IconifyImageView mIconImageView;
    private TextView mTitleTextView;

    // Attribute Variables
    private String mTitle;
    private String mFontIcon;
    private int mSelectedColor;
    private int mTextColor;
    private Drawable mDrawableIcon;
    private float mIconSize;

    // Variables
    private Drawable mInitialBackgroundDrawable;

    //================================================================================
    // Constructors
    //================================================================================

    public NavigationDrawerListItem(Context context) {
        super(context);
        setupView();
    }

    public NavigationDrawerListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public NavigationDrawerListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavigationDrawerListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        setupView();
    }

    //================================================================================
    // Init & inflate methods
    //================================================================================

    private void parseAttributes(AttributeSet attrs) {
        // If not created manually from code
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.NavigationItem, 0, 0);
            try {
                mFontIcon = a.getString(R.styleable.NavigationItem_navItemFontIcon);
                mTitle = a.getString(R.styleable.NavigationItem_navItemTitle);
                mDrawableIcon = a.getDrawable(R.styleable.NavigationItem_navItemDrawableIcon);
                mIconSize = a.getDimension(R.styleable.NavigationItem_navItemFontIconSize, 0);
                mTextColor = a.getColor(R.styleable.NavigationItem_navItemTextColor, Color.RED);
                mSelectedColor = a.getColor(R.styleable.NavigationItem_navItemSelectedColor, Color.GRAY);
            } finally {
                a.recycle();
            }
        }
    }

    // Template method
    private void setupView() {
        inflateView();
        bindView();
    }

    protected void inflateView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.list_item_custom_navigation, this, true);
        mIconImageView = (IconifyImageView) findViewById(R.id.iconify_image_view_icon);
        mTitleTextView = (TextView) findViewById(R.id.text_view_title_toolbar);
        setClickable(true);
        mInitialBackgroundDrawable = getBackground();
    }


    protected void bindView() {
        if (mDrawableIcon != null) {
            mIconImageView.setImageDrawable(mDrawableIcon);
        } else {
            mIconImageView.setIcon(mFontIcon);
            mIconImageView.setColor(mTextColor);
        }
        mTitleTextView.setTextColor(mTextColor);
        mTitleTextView.setText(mTitle);
    }

    //================================================================================
    // Public methods
    //================================================================================

    public void setStateSelected() {
        setBackgroundColor(mSelectedColor);
    }

    public void setStateNormal() {
        setBackgroundDrawable(mInitialBackgroundDrawable);
    }

}
