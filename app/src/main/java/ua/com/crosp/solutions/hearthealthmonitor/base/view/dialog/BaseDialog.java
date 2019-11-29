package ua.com.crosp.solutions.hearthealthmonitor.base.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;

/**
 * Created by Alexander Molochko on 10/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BaseDialog implements DialogInterface {
    private final Dialog mRootDialog;

    public BaseDialog(Dialog dialog) {
        this.mRootDialog = dialog;
    }

    @Override
    public void cancel() {
        mRootDialog.cancel();
    }

    @Override
    public void dismiss() {
        mRootDialog.dismiss();
    }
}
