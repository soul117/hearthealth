package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;

import java.util.ArrayList;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class NotificationIntentModel {

    public String label;
    public String intentName;
    public PendingIntent pendingIntent;
    @IdRes
    public int clickableButtonId;
    @IdRes
    public int imageViewId;
    @DrawableRes
    public int drawableResourceId;

    public NotificationIntentModel(Context context, String label, String intentName, int clickableButtonId, int iconResourceId, int drawableResourceId) {
        this.label = label;
        this.intentName = intentName;
        this.clickableButtonId = clickableButtonId;
        this.imageViewId = iconResourceId;
        this.drawableResourceId = drawableResourceId;
        this.initPendingIntent(context, 0);
    }

    public void initPendingIntent(Context context, int requestCode) {
        this.pendingIntent = createPendingIntent(context, this.intentName, requestCode);
    }

    private static PendingIntent createPendingIntent(Context context, String action, int requestCode) {
        String pkg = context.getPackageName();
        return PendingIntent.getService(context, requestCode, new Intent(action), 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationIntentModel that = (NotificationIntentModel) o;

        return intentName != null ? intentName.equals(that.intentName) : that.intentName == null;

    }

    @Override
    public int hashCode() {
        return intentName != null ? intentName.hashCode() : 0;
    }

    public static class List extends ArrayList<NotificationIntentModel> {
        public NotificationIntentModel getByIntentName(String intentName) {
            for (int i = 0; i < size(); i++) {
                NotificationIntentModel current = get(i);
                if (current.intentName.equals(intentName)) {
                    return current;
                }
            }
            return null;
        }
    }
}
