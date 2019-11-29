package ua.com.crosp.solutions.hearthealthmonitor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.annotation.ColorInt;

import com.joanzapata.iconify.widget.IconTextView;

import ua.com.crosp.solutions.hearthealthmonitor.R;

/**
 * Created by Alexander Molochko on 10/24/15.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class IconDescriptionView extends ConstraintLayout {
    public static final int DEFAULT_ICON_SIZE = 18;
    private int mIconColor = 0;
    private String mTitle;
    private String mDescription;
    private float mIconTextSize = 0;
    private String mTextIcon;
    // Views
    private TextView mTextViewDescription, mTextViewTitle;
    private IconTextView mIconTextViewIcon;

    public IconDescriptionView(Context context) {
        this(context, null);
    }

    public IconDescriptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconDescriptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        inflateView();
        //set attributes
        setAttributes();
        setupView();
    }

    private void parseAttributes(AttributeSet attrs) {
        if (!isInEditMode() && attrs != null) {
            Context context = getContext();
            // Attribute initialization
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconDescriptionView, 0, 0);
            mTextIcon = a.getString(R.styleable.IconDescriptionView_idvIcon);

            //set the color even if we had no image yet
            mIconColor = a.getColor(R.styleable.IconDescriptionView_idvIconColor, 0);
            mTitle = a.getString(R.styleable.IconDescriptionView_idvTitle);
            mDescription = a.getString(R.styleable.IconDescriptionView_idvDescription);
            mIconTextSize = a.getDimension(R.styleable.IconDescriptionView_idvIconTextSize, DEFAULT_ICON_SIZE);
            //recycle the typedArray
            a.recycle();


            //if we have no icon return now
            if (mTextIcon == null || mTextIcon.length() == 0) {
                return;
            }
        }
    }

    public void setIconColor(@ColorInt int iconColor) {
        mIconColor = iconColor;
        mIconTextViewIcon.setTextColor(iconColor);
    }

    private void setupView() {
        mIconTextViewIcon.setText(mTextIcon);
        mTextViewTitle.setText(mTitle);
        mIconTextViewIcon.setTextSize(mIconTextSize);
        mTextViewDescription.setText(mDescription);
    }

    protected void inflateView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_icon_description_item, this, true);
        mIconTextViewIcon = findViewById(R.id.icon_text_view);
        mTextViewTitle = findViewById(R.id.text_view_title);
        mTextViewDescription = findViewById(R.id.text_view_description);
    }

    private void setAttributes() {
        if (mIconColor != 0) {
            mIconTextViewIcon.setTextColor(mIconColor);
        }
    }

    public void setDescription(String description) {
        mDescription = description;
        mTextViewDescription.setText(mDescription);
    }

    public void setTitle(String title) {
        mTitle = title;
        mTextViewTitle.setText(title);
    }
}
