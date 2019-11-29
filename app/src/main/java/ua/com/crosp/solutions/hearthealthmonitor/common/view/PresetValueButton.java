package ua.com.crosp.solutions.hearthealthmonitor.common.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import ua.com.crosp.solutions.hearthealthmonitor.R;

/**
 * Created by Alexander Molochko on 1/16/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PresetValueButton extends RelativeLayout implements RadioCheckable {
    // Views
    private TextView mValueTextView, mUnitTextView;

    // Constants
    public static final int DEFAULT_TEXT_COLOR = Color.TRANSPARENT;

    // Attribute Variables
    private String mValue;
    private String mUnit;
    private int mValueTextColor;
    private int mUnitTextColor;
    private int mPressedTextColor;

    // Variables
    private Drawable mInitialBackgroundDrawable;
    private OnClickListener mOnClickListener;
    private OnTouchListener mOnTouchListener;
    private boolean mChecked;
    private ArrayList<OnCheckedChangeListener> mOnCheckedChangeListeners = new ArrayList<>();
    private Drawable mNormalDrawableBackground;
    private Drawable mPressedDrawableBackground;


    //================================================================================
    // Constructors
    //================================================================================

    public PresetValueButton(Context context) {
        super(context);
        setupView();
    }

    public PresetValueButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public PresetValueButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PresetValueButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        setupView();
    }

    //================================================================================
    // Init & inflate methods
    //================================================================================

    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.PresetValueButton, 0, 0);
        Resources resources = getContext().getResources();
        try {
            mValue = a.getString(R.styleable.PresetValueButton_presetButtonValueText);
            mUnit = a.getString(R.styleable.PresetValueButton_presetButtonUnitText);
            mValueTextColor = a.getColor(R.styleable.PresetValueButton_presetButtonValueTextColor, resources.getColor(R.color.black));
            mPressedTextColor = a.getColor(R.styleable.PresetValueButton_presetButtonPressedTextColor, Color.WHITE);
            mUnitTextColor = a.getColor(R.styleable.PresetValueButton_presetButtonUnitTextColor, resources.getColor(R.color.gray));
            mPressedDrawableBackground = a.getDrawable(R.styleable.PresetValueButton_presetButtonPressedDrawable);
            mNormalDrawableBackground = a.getDrawable(R.styleable.PresetValueButton_presetButtonNormalDrawable);
        } finally {
            a.recycle();
        }
    }

    // Template method
    private void setupView() {
        inflateView();
        bindView();
        setCustomTouchListener();
    }

    protected void inflateView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_preset_button, this, true);
        mValueTextView = (TextView) findViewById(R.id.text_view_value);
        mUnitTextView = (TextView) findViewById(R.id.text_view_unit);
        mInitialBackgroundDrawable = getBackground();
    }

    protected void bindView() {
        if (mUnitTextColor != DEFAULT_TEXT_COLOR) {
            mUnitTextView.setTextColor(mUnitTextColor);
        }
        if (mValueTextColor != DEFAULT_TEXT_COLOR) {
            mValueTextView.setTextColor(mValueTextColor);
        }
        mUnitTextView.setText(mUnit);
        mValueTextView.setText(mValue);
    }

    //================================================================================
    // Overriding default behavior
    //================================================================================

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
    }

    protected void setCustomTouchListener() {
        super.setOnTouchListener(new TouchListener());
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    public OnTouchListener getOnTouchListener() {
        return mOnTouchListener;
    }

    private void onTouchDown(MotionEvent motionEvent) {
        setChecked(true);
    }

    private void onTouchUp(MotionEvent motionEvent) {
        // Handle user defined click listeners
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this);
        }
    }
    //================================================================================
    // Public methods
    //================================================================================

    public void setCheckedState() {
        setBackgroundDrawable(mPressedDrawableBackground != null ? mPressedDrawableBackground : mInitialBackgroundDrawable);
        mValueTextView.setTextColor(mPressedTextColor);
        mUnitTextView.setTextColor(mPressedTextColor);
        mChecked = true;
    }

    public void setNormalState() {
        mChecked = false;
        setBackgroundDrawable(mNormalDrawableBackground == null ? mInitialBackgroundDrawable : mNormalDrawableBackground);
        mValueTextView.setTextColor(mValueTextColor);
        mUnitTextView.setTextColor(mUnitTextColor);
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    //================================================================================
    // Checkable implementation
    //================================================================================

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            if (!mOnCheckedChangeListeners.isEmpty()) {
                for (int i = 0; i < mOnCheckedChangeListeners.size(); i++) {
                    mOnCheckedChangeListeners.get(i).onCheckedChanged(this, checked);
                }
            }
            if (checked) {
                setCheckedState();
            } else {
                setNormalState();
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener);
    }

    @Override
    public void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener);
    }

    //================================================================================
    // Inner classes
    //================================================================================
    private final class TouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onTouchDown(event);
                    break;
                case MotionEvent.ACTION_UP:
                    onTouchUp(event);
                    break;
            }
            if (mOnTouchListener != null) {
                mOnTouchListener.onTouch(v, event);
            }
            return true;
        }
    }
}
