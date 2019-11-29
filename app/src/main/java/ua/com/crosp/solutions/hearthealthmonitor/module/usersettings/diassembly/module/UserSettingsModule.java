package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.diassembly.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.presenter.UserSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.router.UserSettingsRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.usecase.GetUserWithSettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.usecase.SaveUserInfoUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.presentation.UserSettingsPresenter;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class UserSettingsModule {
    public UserSettingsModule() {
    }

    @Provides
    @PerModule
    GetUserWithSettingsUseCase provideGetUserSessionUseCase(UserSessionManagerContract sessionManagerContract, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetUserWithSettingsUseCase(sessionManagerContract, postExecutionThread, threadExecutor);
    }
    @Provides
    @PerModule
    UserSettingsPresenterContract provideUserSettingsPresenter(EntityId userEntityId, UserSettingsRouterContract router, GetUserWithSettingsUseCase getUserSettingsUseCase, SaveUserInfoUseCase saveUserSettingsUseCase, UserSessionManagerContract userSessionManager) {
        return new UserSettingsPresenter(userEntityId, router, getUserSettingsUseCase, saveUserSettingsUseCase, userSessionManager);
    }

    @Provides
    @PerModule
    EntityId provideUserEntityId(UserSessionManagerContract userSessionManager) {
        return userSessionManager.getCurrentUserEntityId();
    }

    @Provides
    @PerModule
    UserSettingsRouterContract provideRouter(Activity activity) {
        return (UserSettingsRouterContract) activity;
    }

}
