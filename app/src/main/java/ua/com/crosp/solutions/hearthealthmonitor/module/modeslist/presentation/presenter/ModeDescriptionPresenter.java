package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.functions.BiConsumer;
import ua.com.crosp.solutions.hearthealthmonitor.base.mapper.MapperConstants;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.ModesRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.presenter.ModeDescriptionPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.usecase.ModeDescriptionUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.view.ModeDescriptionViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class ModeDescriptionPresenter extends BaseAppPresenter<ModeDescriptionViewContract, ModesRouterContract> implements ModeDescriptionPresenterContract {

    // Dependencies
    private BaseMapper<ModeItem, ModeListItem> mModeItemMapper;
    private ModeDescriptionUseCaseContract mModeDescriptionUseCase;
    // Variables
    private int mModeId;

    @Inject
    public ModeDescriptionPresenter(ModesRouterContract routerContract,
                                    ModeDescriptionUseCaseContract modeDescriptionUseCase,
                                    @Named(MapperConstants.EXPERIMENT_MODE) BaseMapper<ModeItem, ModeListItem> modeMapper) {
        setRouter(routerContract);
        mModeItemMapper = modeMapper;
        mModeDescriptionUseCase = modeDescriptionUseCase;
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

    }

    @Override
    public void openModeExperiment(ModeListItem modeListItem) {
        getRouter().startMotivationalMode(modeListItem.id);
    }

    @Override
    public void openModeSettings(ModeListItem modeListItem) {
        getRouter().openMotivationalModeSettings(modeListItem.id);
    }

    @Override
    public void initialize(int modeId) {
        mModeId = modeId;
        fetchModeInfo(modeId);
    }

    @Override
    public void onBackButtonPress() {
        getRouter().navigateBack();
    }

    private void fetchModeInfo(int modeId) {
        mModeDescriptionUseCase.getModeDescriptionWithId(modeId).subscribe(new BiConsumer<ModeItem, Throwable>() {
            @Override
            public void accept(ModeItem modeItem, Throwable throwable) throws Exception {
                getView().displayModeInformation(mModeItemMapper.transform(modeItem));
            }
        });

    }
}