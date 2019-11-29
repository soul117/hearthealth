package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.adapter.items;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import ua.com.crosp.solutions.hearthealthmonitor.R;

/**
 * Created by Alexander Molochko on 2/11/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentResultItemView extends CardView {
    // Views
    private ImageView mIconImageView;
    private TextView mTitleTextView;
    private TextView mTimeTextView;
    private TextView mDateTextView;
    private TextView mDescriptionTextView;


    //================================================================================
    // Constructors
    //================================================================================

    public ExperimentResultItemView(Context context) {
        super(context);
        setupView();
    }

    public ExperimentResultItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public ExperimentResultItemView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        inflater.inflate(R.layout.list_item_experiment_result, this, true);
        mIconImageView = findViewById(R.id.image_view_icon);
        mTitleTextView = findViewById(R.id.text_view_title);
        mTimeTextView = findViewById(R.id.text_view_time);
        mDateTextView = findViewById(R.id.text_view_date);
        mDescriptionTextView = findViewById(R.id.text_view_description);
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        setClickable(true);
        setUseCompatPadding(true);
    }
    //================================================================================
    // Public methods
    //================================================================================

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setDescription(String description) {
        mDescriptionTextView.setText(description);
    }

    public void setTime(String time) {
        mTimeTextView.setText(time);
    }

    public void setDate(String description) {
        mDateTextView.setText(description);
    }

    public void setIcon(int drawableId) {
        mIconImageView.setImageResource(drawableId);
    }

}
