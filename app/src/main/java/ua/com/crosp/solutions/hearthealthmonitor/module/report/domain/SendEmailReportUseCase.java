package ua.com.crosp.solutions.hearthealthmonitor.module.report.domain;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FilePath;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.contract.delivery.ReportTransporterContract;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class SendEmailReportUseCase extends BaseSingleUseCase<ReportTransporterContract.ReportSendPayload, Boolean> {
    private Context mContext;

    public SendEmailReportUseCase(ExecutionThread threadExecutor, PostExecutionThread postExecutionThread, Context context) {
        super(threadExecutor, postExecutionThread);
        mContext = context;
    }

    @Override
    public Single<Boolean> provideSingleObservable(ReportTransporterContract.ReportSendPayload reportSendPayload) {
        //need to "send multiple" to get more than one attachment

        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                reportSendPayload.recipients.toStringList());
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, reportSendPayload.title);
        emailIntent.putExtra(Intent.EXTRA_TEXT, reportSendPayload.body);
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
        for (FilePath file : reportSendPayload.fileAttachments) {
            File fileIn = new File(file.getStringPath());
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        mContext.startActivity(Intent.createChooser(emailIntent, "Send XML report..."));
        return Single.just(true);
    }


}
