package ua.com.crosp.solutions.hearthealthmonitor.module.report.contract.delivery;

import java.util.List;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FilePath;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.transport.valueobject.Recipient;

/**
 * Created by Alexander Molochko on 12/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ReportTransporterContract {

    public Single<Boolean> sendReport(ReportSendPayload reportPayload);

    public static class ReportSendPayload {
        public Recipient.List recipients;
        public String title;
        public String body;
        public List<FilePath> fileAttachments;

        public ReportSendPayload(Recipient.List recipients, String title, String body, List<FilePath> fileAttachments) {
            this.recipients = recipients;
            this.title = title;
            this.body = body;
            this.fileAttachments = fileAttachments;
        }

        public ReportSendPayload(String title, String body, List<FilePath> fileAttachments) {
            this.title = title;
            this.body = body;
            this.fileAttachments = fileAttachments;
        }

        public ReportSendPayload() {
        }
    }
}
