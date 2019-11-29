package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FilePath;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper.XmlMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.resource.ResourceProviderContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.GetExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.RemoveExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.ExperimentResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.ExperimentResultsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.ExperimentResultDetailPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.presenter.ExperimentResultDetailPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.contract.delivery.ReportGeneratorContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.domain.GenerateAndSendXmlReportUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.domain.SendEmailReportUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.XmlReportGenerator;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class ExperimentDetailModule {

    @Provides
    @PerModule
    ExperimentResultDetailPresenterContract provideExperimentResultDetailPresenter(ExperimentResultRouterContract router, GenerateAndSendXmlReportUseCase reportUseCase, GetExperimentResultByIdUseCase experimentUseCase) {
        return new ExperimentResultDetailPresenter(reportUseCase, router, experimentUseCase);
    }

    @Provides
    @PerModule
    GenerateAndSendXmlReportUseCase provideGenerateAndSendXmlReportUseCase(ExecutionThread execution, PostExecutionThread observimg, ReportGeneratorContract<XmlReportGenerator.ReportPayload, FilePath> reportGenerator, SendEmailReportUseCase sendMailUseCase, UserSessionManagerContract sessionManager, ResourceProviderContract resourceProvider, SaveExperimentResultForCurrentUserUseCase saveResult) {
        return new GenerateAndSendXmlReportUseCase(execution, observimg, reportGenerator, sendMailUseCase, saveResult, sessionManager, resourceProvider);
    }

    @Provides
    @PerModule
    SaveExperimentResultForCurrentUserUseCase provideSaveExperimentResultForCurrentUserUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, ExperimentResultsRepositoryContract repository) {
        return new SaveExperimentResultForCurrentUserUseCase(repository, executionThread, postExecutionThread);
    }

    @Provides
    @PerModule
    SendEmailReportUseCase provideSendEmailReportUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, Activity context) {
        return new SendEmailReportUseCase(executionThread, postExecutionThread, context);
    }

    @Provides
    @PerModule
    ReportGeneratorContract<XmlReportGenerator.ReportPayload, FilePath> provideXmlReportGenerator(XmlMapperContract xmlMapper, FileManager fileManager) {
        return new XmlReportGenerator(xmlMapper, fileManager);
    }

    @Provides
    @PerModule
    GetExperimentResultByIdUseCase provideGetExperimentResultByIdUseCase(ExperimentResultsRepositoryContract repository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new GetExperimentResultByIdUseCase(repository, executionThread, postExecutionThread);
    }

    @Provides
    @PerModule
    RemoveExperimentResultByIdUseCase provideRemoveExperimentResultByIdUseCase(ExperimentResultsRepositoryContract repository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new RemoveExperimentResultByIdUseCase(repository, executionThread, postExecutionThread);
    }
}
