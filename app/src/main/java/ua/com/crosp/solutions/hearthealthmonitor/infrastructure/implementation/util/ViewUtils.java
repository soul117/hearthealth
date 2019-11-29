package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alexander Molochko on 4/29/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ViewUtils {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static final String EMPTY_STRING = "";

    @SuppressLint("NewApi")
    public static int generateViewId() {

        if (Build.VERSION.SDK_INT < 17) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }

    }

    public static int getActionBarHeight(AppCompatActivity appCompatActivity) {
        int actionBarHeight = appCompatActivity.getSupportActionBar().getHeight();
        if (actionBarHeight != 0)
            return actionBarHeight;
        TypedValue tv = new TypedValue();
        if (appCompatActivity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, appCompatActivity.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static void clearEditTexts(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setText(EMPTY_STRING);
        }
    }

    public static void setEditTextSelection(EditText editText, String text) {
        // Trim at first
        String trimmedText = text.trim();
        editText.setText(trimmedText);
        editText.setSelection(editText.getText().length());
    }
}
