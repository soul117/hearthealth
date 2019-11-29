package ua.com.crosp.solutions.hearthealthmonitor.module.report.domain;

import java.util.ArrayList;

import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FilePath;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.resource.ResourceProviderContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.contract.delivery.ReportGeneratorContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.XmlReportGenerator;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.transport.valueobject.Recipient;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;

import static ua.com.crosp.solutions.hearthealthmonitor.module.report.contract.delivery.ReportTransporterContract.ReportSendPayload;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class GenerateAndSendXmlReportUseCase extends BaseSingleUseCase<ExperimentResultEntity, Boolean> {
    ReportGeneratorContract<XmlReportGenerator.ReportPayload, FilePath> mXmlReportGenerator;
    SendEmailReportUseCase mSendReportUseCase;
    SaveExperimentResultForCurrentUserUseCase mSaveExperimentResultForCurrentUserUseCase;
    UserSessionManagerContract mUserSessionManager;
    ResourceProviderContract mResourceProvider;

    public GenerateAndSendXmlReportUseCase(ExecutionThread threadExecutor, PostExecutionThread postExecutionThread, ReportGeneratorContract<XmlReportGenerator.ReportPayload, FilePath> xmlReportGenerator, SendEmailReportUseCase sendReportUseCase, SaveExperimentResultForCurrentUserUseCase saveExperimentResultForCurrentUserUseCase, UserSessionManagerContract userSessionManager, ResourceProviderContract resourceProvider) {
        super(threadExecutor, postExecutionThread);
        mXmlReportGenerator = xmlReportGenerator;
        mSendReportUseCase = sendReportUseCase;
        mSaveExperimentResultForCurrentUserUseCase = saveExperimentResultForCurrentUserUseCase;
        mUserSessionManager = userSessionManager;
        mResourceProvider = resourceProvider;
    }

    @Override
    public Single<Boolean> provideSingleObservable(final ExperimentResultEntity experimentResultEntity) {
        final UserEntity currentUser = mUserSessionManager.getCurrentSessionUserEntity();
        FilePath xmlReportPath = experimentResultEntity.getXmlReportPath();
        if (!isValidXmlPath(xmlReportPath)) {
            xmlReportPath = experimentResultEntity.getXmlReportPathInitIfRequired();
            return mXmlReportGenerator.generateReport(new XmlReportGenerator.ReportPayload(experimentResultEntity, currentUser, xmlReportPath))
                    .flatMap(new BaseMapper<FilePath, ObservableSource<Long>>() {
                        @Override
                        public ObservableSource<Long> transform(FilePath inputItem) {
                            return mSaveExperimentResultForCurrentUserUseCase.provideSingleObservable(new SaveExperimentResultForCurrentUserUseCase.Params(mUserSessionManager.getCurrentUserEntityId().getId(), experimentResultEntity)).toObservable();
                        }
                    })
                    .flatMap(new BaseMapper<Long, ObservableSource<Boolean>>() {
                        @Override
                        public ObservableSource<Boolean> transform(Long inputItem) {
                            ReportSendPayload reportBundle = generateReportPayload(experimentResultEntity, currentUser);
                            return mSendReportUseCase.provideSingleObservable(reportBundle).toObservable();
                        }
                    })
                    .singleOrError();
        } else {
            ReportSendPayload reportBundle = generateReportPayload(experimentResultEntity, currentUser);
            return mSendReportUseCase.provideSingleObservable(reportBundle)
                    .flatMap(new BaseMapper<Boolean, SingleSource<? extends Boolean>>() {
                        @Override
                        public SingleSource<? extends Boolean> transform(Boolean inputItem) {
                            return Single.just(inputItem);
                        }
                    });
        }
    }

    private ReportSendPayload generateReportPayload(ExperimentResultEntity resultEntity, UserEntity userEntity) {
        // TODO generate by templates,remove hardcorded values
        ReportSendPayload reportSendPayload = new ReportSendPayload();
        reportSendPayload.title = mResourceProvider.getStringById(R.string.title_mail_report_default);
        reportSendPayload.title += mResourceProvider.getStringById(R.string.title_part_email_report) + resultEntity.getGameResultEntity().getGameExperimentId();
        reportSendPayload.body = mResourceProvider.getStringById(R.string.body_mail_report_xml_default);
        reportSendPayload.body += " " + resultEntity.getExperimentName();
        reportSendPayload.recipients = new Recipient.List();
        // Add recipients
        DoctorEntity.List doctos = userEntity.getUserDoctors();
        for (DoctorEntity doctorEntity : doctos) {
            Recipient recipient = new Recipient(doctorEntity.getEmail().getEmailString());
            reportSendPayload.recipients.add(recipient);
        }
        reportSendPayload.fileAttachments = new ArrayList<>();
        reportSendPayload.fileAttachments.add(resultEntity.getXmlReportPath());

        return reportSendPayload;
    }


    private boolean isValidXmlPath(FilePath filePath) {
        return filePath != null && filePath != FilePath.NULLABLE_FILE_PATH && !filePath.getStringPath().equals("");
    }
}
