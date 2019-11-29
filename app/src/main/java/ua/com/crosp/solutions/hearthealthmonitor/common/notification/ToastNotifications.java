package ua.com.crosp.solutions.hearthealthmonitor.common.notification;

import android.content.Context;

import ua.com.crosp.solutions.library.prettytoast.PrettyToast;


/**
 * Created by Molochko Oleksandr on 10/6/15.
 * Package com.crosp.solutions.kopilkareferalapp.notifications
 * Project KopilkaReferalApp
 * Copyright (c) 2013-2015 Datascope Ltd. All Rights Reserved.
 */
public class ToastNotifications {
    public static boolean ENABLE_NOTIFICATIONS = true;

    private ToastNotifications() {
    }

    public static void showErrorMessage(Context context, String message) {
        if (ENABLE_NOTIFICATIONS) {
            PrettyToast.showError(context, message);
        }
    }

    public static void showInfoMessage(Context context, String message) {
        if (ENABLE_NOTIFICATIONS) {
            PrettyToast.showInfo(context, message);
        }
    }

    public static void showSuccessMessage(Context context, String message) {
        if (ENABLE_NOTIFICATIONS) {
            PrettyToast.showSuccess(context, message);
        }
    }


    public static void showWarningMessage(Context context, String message) {
        if (ENABLE_NOTIFICATIONS) {
            PrettyToast.showWarning(context, message);
        }
    }

    public static void showToastMessageCustomTextSize(Context context, String message, int textSize) {
        if (ENABLE_NOTIFICATIONS) {
            new PrettyToast.Builder(context)
                    .withTextSize(textSize)
                    .withMessage(message)
                    .build()
                    .show();
        }
    }
}
