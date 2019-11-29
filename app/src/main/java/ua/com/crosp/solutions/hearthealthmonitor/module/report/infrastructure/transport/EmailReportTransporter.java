package ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.transport;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.contract.delivery.ReportTransporterContract;

/**
 * Created by Alexander Molochko on 12/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EmailReportTransporter implements ReportTransporterContract {


    @Override
    public Single<Boolean> sendReport(ReportSendPayload reportPayload) {
        return null;
    }
}
