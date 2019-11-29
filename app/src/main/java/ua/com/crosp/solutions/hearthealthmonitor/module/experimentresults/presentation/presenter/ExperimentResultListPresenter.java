package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.GetAllExperimentResultsForUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.RemoveExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.ExperimentResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.ExperimentResultListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.ExperimentResultListViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentResultViewModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class ExperimentResultListPresenter extends BaseAppPresenter<ExperimentResultListViewContract, ExperimentResultRouterContract> implements ExperimentResultListPresenterContract {

    @Inject
    GetAllExperimentResultsForUserUseCase mGetAllExperimentResultsForUserUseCase;
    @Inject
    RemoveExperimentResultByIdUseCase mRemoveExperimentResultByIdUseCase;
    UserSessionManagerContract mUserSessionManager;

    public ExperimentResultListPresenter(RemoveExperimentResultByIdUseCase removeExperimentResultByIdUseCase, ExperimentResultRouterContract routerContract, GetAllExperimentResultsForUserUseCase getAllExperimentResultsForUserUseCase, UserSessionManagerContract userSessionManager) {
        setRouter(routerContract);
        mRemoveExperimentResultByIdUseCase = removeExperimentResultByIdUseCase;
        mGetAllExperimentResultsForUserUseCase = getAllExperimentResultsForUserUseCase;
        mUserSessionManager = userSessionManager;
    }

    @Override
    public void start() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void onViewReady() {
        loadExperimentResultsForUser();
    }

    @Override
    public void onViewDestroyed() {

    }

    public void loadExperimentResultsForUser() {
        // Map entities to view models
        mGetAllExperimentResultsForUserUseCase.execute(mUserSessionManager.getCurrentUserEntityId().getId())
                .subscribe(new Consumer<ExperimentResultEntity.List>() {
                    @Override
                    public void accept(ExperimentResultEntity.List experimentResultEntities) throws Exception {
                        getView().displayExperimentResultList(convertToViewModelList(experimentResultEntities));
                    }
                });
    }

    private ExperimentResultViewModel.List convertToViewModelList(ExperimentResultEntity.List experimentResultEntities) {
        return ExperimentResultViewModel.List.fromEntityList(experimentResultEntities);
    }

    @Override
    public void onExperimentItemClick(ExperimentResultViewModel experimentResultViewModel) {
        getRouter().openExperimentDetailsScreen(experimentResultViewModel.id);
    }

    @Override
    public void onBackButtonPress() {
        getRouter().navigateBack();
    }

    @Override
    public void onExperimentLongClickListener(final ExperimentResultViewModel item) {
        getView().showExperimentContextDialog()
                .subscribe(new BiConsumer<Integer, Throwable>() {
                    @Override
                    public void accept(Integer integer, Throwable throwable) throws Exception {
                        switch (integer) {
                            case 0: {
                                removeExperiment(item);
                            }
                            case 1: {

                            }
                            default:
                                break;
                        }
                    }
                });
    }

    private void removeExperiment(final ExperimentResultViewModel item) {
        mRemoveExperimentResultByIdUseCase.provideSingleObservable(item.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Boolean, Throwable>() {
                    @Override
                    public void accept(Boolean aBoolean, Throwable throwable) throws Exception {
                        getView().onExperimentRemoved(item);
                        getView().showSuccessMessage(R.string.message_experiment_removed);
                    }
                });
    }
}