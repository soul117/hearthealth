package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ua.com.crosp.solutions.hearthealthmonitor.base.mapper.MapperConstants;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.ModesRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.presenter.ModesListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.usecase.ModesListUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.view.ModesListViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class ModesListPresenter extends BaseAppPresenter<ModesListViewContract, ModesRouterContract> implements ModesListPresenterContract {

    private ModesListUseCaseContract mModesListUseCase;
    private BaseMapper<ModeItem, ModeListItem> mModeItemMapper;

    @Inject
    public ModesListPresenter(ModesRouterContract routerContract,
                              ModesListUseCaseContract modesListUseCase,
                              @Named(MapperConstants.EXPERIMENT_MODE) BaseMapper<ModeItem, ModeListItem> modeMapper) {
        setRouter(routerContract);
        mModeItemMapper = modeMapper;
        mModesListUseCase = modesListUseCase;
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
        getRouter().showNavigationBar();
        fetchModesList();
    }

    @Override
    public void onViewDestroyed() {

    }

    public void fetchModesList() {
        // Map entities to view models
        mModesListUseCase.getModesList()
                .map(modeItems -> {
                    // Convert
                    return mModeItemMapper.transform(modeItems);
                })
                .subscribe(modeListItems -> getView().displayModesList(modeListItems));
    }


    @Override
    public void onModeItemClick(ModeListItem modeListItem) {
        ModesRouterContract router = getRouter();
        router.hideNavigationBar();
        router.openModeDescription(modeListItem.id);
    }
}