package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.GetExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.ExperimentResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.ExperimentResultDetailPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.ExperimentResultDetailViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.domain.GenerateAndSendXmlReportUseCase;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class ExperimentResultDetailPresenter extends BaseAppPresenter<ExperimentResultDetailViewContract, ExperimentResultRouterContract> implements ExperimentResultDetailPresenterContract {

    @Inject
    GetExperimentResultByIdUseCase mExperimentResultByIdUseCase;
    GenerateAndSendXmlReportUseCase mGenerateAndSendXmlReportUseCase;
    private long mExperimentId;
    private ExperimentResultEntity mExperimentResult;

    public ExperimentResultDetailPresenter(GenerateAndSendXmlReportUseCase reportUseCase, ExperimentResultRouterContract routerContract, GetExperimentResultByIdUseCase getExperimentResultByIdUseCase) {
        setRouter(routerContract);
        mExperimentResultByIdUseCase = getExperimentResultByIdUseCase;
        getRouter().hideNavigationBar();
        mGenerateAndSendXmlReportUseCase = reportUseCase;
    }

    @Override
    public void start() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void onViewReady() {
    }

    @Override
    public void onViewDestroyed() {
        getRouter().showNavigationBar();
    }

    @Override
    public void initialize(long modeId) {
        mExperimentId = modeId;
        mExperimentResultByIdUseCase
                .provideSingleObservable(modeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<ExperimentResultEntity, Throwable>() {
                    @Override
                    public void accept(ExperimentResultEntity experimentResultEntity, Throwable throwable) throws Exception {
                        if (null != throwable) {
                            getView().showErrorMessage(R.string.message_error_invalid_experiment);
                        } else {
                            mExperimentResult = experimentResultEntity;
                            getView().displayExperimentDetails(experimentResultEntity);
                        }
                    }
                });
    }

    @Override
    public void onBackButtonPress() {
        getRouter().navigateBack();
    }

    @Override
    public void showEcgResultChart() {
        getRouter().switchToEcgChartScreen(mExperimentId);
    }

    @Override
    public void onGenerateXMLReport() {
        mGenerateAndSendXmlReportUseCase.provideSingleObservable(mExperimentResult)
                .subscribe(new BiConsumer<Boolean, Throwable>() {
                    @Override
                    public void accept(Boolean aBoolean, Throwable throwable) throws Exception {
                        getView().showSuccessMessage(R.string.message_success_mail_sent);
                    }
                });
    }

}