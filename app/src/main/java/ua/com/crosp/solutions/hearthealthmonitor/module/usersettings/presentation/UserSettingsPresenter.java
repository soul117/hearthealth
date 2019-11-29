package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.presentation;

import javax.inject.Inject;

import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.dialog.DoctorsListDialogFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.presenter.UserSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.router.UserSettingsRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.view.UserSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.usecase.GetUserWithSettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.usecase.SaveUserInfoUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.DoctorViewModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.UserSettingsViewModel;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSettingsPresenter extends BaseAppPresenter<UserSettingsViewContract, UserSettingsRouterContract> implements UserSettingsPresenterContract, DoctorsListDialogFragment.OnDoctorSelectionListener {
    private final SaveUserInfoUseCase mSaveUserSettingsUseCase;
    private final GetUserWithSettingsUseCase mGetUserSettingsUseCase;
    private final EntityId mUserEntityId;
    private UserEntity mUserSettings;
    private final UserSessionManagerContract mUserSessionManager;
    // Component & modular dependencies

    @Inject
    public UserSettingsPresenter(EntityId userEntityId, UserSettingsRouterContract router, GetUserWithSettingsUseCase getUserSettingsUseCase, SaveUserInfoUseCase saveUserSettingsUseCase, UserSessionManagerContract userSessionManager) {
        mGetUserSettingsUseCase = getUserSettingsUseCase;
        mSaveUserSettingsUseCase = saveUserSettingsUseCase;
        mUserEntityId = userEntityId;
        mUserSessionManager = userSessionManager;
        setRouter(router);
    }

    @Override
    public void onViewReady() {
        mGetUserSettingsUseCase.execute(mUserEntityId)
                .subscribe(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity entity) throws Exception {
                        mUserSettings = entity;
                        getView().displayExistingUserSettings(UserSettingsViewModel.createFromUserEntity(entity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().showErrorMessage(throwable.getMessage());
                        getView().displayExistingUserSettings(UserSettingsViewModel.createFromUserEntity(UserEntity.INVALID));
                        mUserSettings = UserEntity.INVALID;

                    }
                });
    }

    @Override
    public void onViewDestroyed() {

    }

    @Override
    public void onBackButtonPress() {
        getRouter().navigateBack();
    }

    @Override
    public void saveUserSettings(UserSettingsViewModel userViewModel) {
        updateUserEntityFromViewModel(userViewModel);
        mSaveUserSettingsUseCase.execute(mUserSettings)
                .flatMap(new Function<Long, SingleSource<Long>>() {
                    @Override
                    public SingleSource<Long> apply(@NonNull Long aLong) throws Exception {
                        return mUserSessionManager.setUserForSession(mUserSettings);
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getView().showNotificationAndDestoryViews();
                        getRouter().switchToMainAppScreen();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.error(throwable);
                    }
                });
    }

    private void updateUserEntityFromViewModel(UserSettingsViewModel viewModel) {
        mUserSettings.setAbout(viewModel.getAbout());
        mUserSettings.setGenderFromString(viewModel.getGender());
        mUserSettings.setFirstName(viewModel.getFirstName());
        mUserSettings.setLastName(viewModel.getLastName());
        mUserSettings.setEmail(viewModel.getEmail());
        mUserSettings.setDateOfBirth(viewModel.getDateOfBirth());
        mUserSettings.setPhoneNumber(viewModel.getPhone());
        mUserSettings.setDoctors(viewModel.getDoctors().toDoctorEntitiesList());
        mUserSettings.setPersonalId(viewModel.getUserPersonalId());
    }

    @Override
    public void onDoctorsSelected(DoctorViewModel.List doctorViewModels) {
        mUserSettings.setDoctors(doctorViewModels.toDoctorEntitiesList());
        getView().updateSelectedDoctorsInfo(doctorViewModels);
    }
}
