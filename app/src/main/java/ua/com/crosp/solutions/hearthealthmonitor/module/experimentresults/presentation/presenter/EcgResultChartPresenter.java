package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.presenter;

import io.reactivex.SingleSource;
import io.reactivex.functions.BiConsumer;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.GetExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.EcgResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.EcgChartResultPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.EcgResultChartViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.domain.ReadRawEcgDataUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.EcgChartAudioData;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.EcgRawAudioData;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class EcgResultChartPresenter extends BaseAppPresenter<EcgResultChartViewContract, EcgResultRouterContract> implements EcgChartResultPresenterContract {
    private final GetExperimentResultByIdUseCase mExperimentResultByIdUseCase;
    private final ReadRawEcgDataUseCase mReadWavFileUseCase;

    public EcgResultChartPresenter(EcgResultRouterContract routerContract, GetExperimentResultByIdUseCase getExperimentResultByIdUseCase, ReadRawEcgDataUseCase readWavFileUseCase) {
        mReadWavFileUseCase = readWavFileUseCase;
        setRouter(routerContract);
        mExperimentResultByIdUseCase = getExperimentResultByIdUseCase;
    }


    @Override
    public void onViewReady() {

    }

    @Override
    public void onViewDestroyed() {
    }


    @Override
    public void onBackButtonPress() {
        getRouter().navigateBack();
    }

    @Override
    public void initialize(long expId) {
        mExperimentResultByIdUseCase.provideSingleObservable(expId)
                .flatMap(new BaseMapper<ExperimentResultEntity, SingleSource<EcgRawAudioData>>() {
                    @Override
                    public SingleSource<EcgRawAudioData> transform(ExperimentResultEntity inputItem) {
                        return mReadWavFileUseCase.provideSingleObservable(inputItem.getAudioEcgRecordingPath());
                    }
                })
                .map(new BaseMapper<EcgRawAudioData, EcgChartAudioData>() {
                    @Override
                    public EcgChartAudioData transform(EcgRawAudioData inputItem) {
                        return EcgChartAudioData.fromRawData(inputItem);
                    }
                })
                .subscribe(new BiConsumer<EcgChartAudioData, Throwable>() {
                    @Override
                    public void accept(EcgChartAudioData ecgChartAudioData, Throwable throwable) throws Exception {
                        getView().displayChartData(ecgChartAudioData);
                    }
                });


    }


}