package ua.com.crosp.solutions.hearthealthmonitor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatImageView;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconDrawable;

import ua.com.crosp.solutions.hearthealthmonitor.R;

/**
 * Created by Alexander Molochko on 10/24/15.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class IconifyImageView extends AppCompatImageView {
    private String mTextIcon;
    private IconDrawable mIcon = null;
    private int mColor = 0;
    private float mSize = -1;

    public IconifyImageView(Context context) {
        this(context, null);
    }

    public IconifyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconifyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    private void parseAttributes(AttributeSet attrs) {
        if (!isInEditMode()) {
            Context context = getContext();
            // Attribute initialization
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconifyImageView, 0, 0);
            mTextIcon = a.getString(R.styleable.IconifyImageView_iconifyIcon);

            //set the color even if we had no image yet
            mColor = a.getColor(R.styleable.IconifyImageView_iconifyColor, 0);
            mSize = a.getDimension(R.styleable.IconifyImageView_iconifySize, -1);

            //recycle the typedArray
            a.recycle();

            //set the scale type for this view
            setScaleType(ScaleType.CENTER_INSIDE);

            //if we have no icon return now
            if (mTextIcon == null || mTextIcon.length() == 0) {
                return;
            }


            //get the drawable
            mIcon = new IconDrawable(context, mTextIcon);

            //set attributes
            setAttributes();

            //set our values for this view
            setImageDrawable(mIcon);
        }
    }

    public void setIcon(Icon icon) {
        setIcon(new IconDrawable(getContext(), icon), true);
    }

    public void setIcon(Icon icon, boolean resetAttributes) {
        setIcon(new IconDrawable(getContext(), icon), resetAttributes);
    }

    public void setIcon(String icon) {
        setIcon(icon, true);
    }

    public void setIcon(String icon, boolean resetAttributes) {
        setIcon(new IconDrawable(getContext(), icon), resetAttributes);
    }

    public void setColor(@ColorInt int color) {
        Drawable drawable = getDrawable();
        if (drawable instanceof IconDrawable) {
            ((IconDrawable) drawable).color(color);
        }
    }

    public void setColorRes(@ColorRes int colorRes) {
        Drawable drawable = getDrawable();
        if (drawable instanceof IconDrawable) {
            ((IconDrawable) drawable).colorRes(colorRes);
        }
    }

    public void setIcon(IconDrawable icon, boolean resetAttributes) {
        mIcon = icon;
        //reset the attributes defined via the layout
        if (resetAttributes) {
            setAttributes();
        }
        //set the imageDrawable
        setImageDrawable(mIcon);
    }

    public void setIcon(IconDrawable icon) {
        setIcon(icon, true);
    }

    public IconDrawable getIcon() {
        Drawable drawable = getDrawable();
        if (drawable instanceof IconDrawable) {
            return ((IconDrawable) drawable);
        }
        return null;
    }

    private void setAttributes() {
        if (mColor != 0) {
            mIcon.color(mColor);
        }
        if (mSize != -1) {
            mIcon.sizeDp((int) mSize);
        }
    }
}
