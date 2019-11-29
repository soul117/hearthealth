package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view.items;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.joanzapata.iconify.widget.IconTextView;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.IconifyImageView;

/**
 * Created by Alexander Molochko on 2/11/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ModeListItemView extends CardView {
    // Views
    private IconifyImageView mIconImageView;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private IconTextView mNextIconTextView;


    //================================================================================
    // Constructors
    //================================================================================

    public ModeListItemView(Context context) {
        super(context);
        setupView();
    }

    public ModeListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public ModeListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }

    //================================================================================
    // Init & inflate methods
    //================================================================================

    private void parseAttributes(AttributeSet attrs) {

    }

    // Template method
    private void setupView() {
        inflateView();
    }

    protected void inflateView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.list_item_custom_mode, this, true);
        mIconImageView = (IconifyImageView) findViewById(R.id.iconify_image_view_icon);
        mTitleTextView = (TextView) findViewById(R.id.text_view_title_toolbar);
        mDescriptionTextView = (TextView) findViewById(R.id.text_view_description);
        mNextIconTextView = (IconTextView) findViewById(R.id.icon_text_view_next);
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        setClickable(true);
        setUseCompatPadding(true);
    }
    //================================================================================
    // Public methods
    //================================================================================

    public void setTextIcon(String textIcon) {
        mIconImageView.setIcon(textIcon);
    }

    public void setTextIconColor(int color) {
        mIconImageView.setColor(color);
    }

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setDescription(String description) {
        mDescriptionTextView.setText(description);
    }

}
