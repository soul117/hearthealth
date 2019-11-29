package ua.com.crosp.solutions.hearthealthmonitor.di.module.session;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.CreateUserSessionFromUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.DestroyUserSessionUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.GetExistingUserSessionIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.GetUserSessionUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.SaveExistingUserSessionIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.SaveUserSessionUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.manager.UserSessionManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.userlist.contract.data.UserRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.usecase.SaveUserInfoUseCase;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerApplication
public class UserSessionModule {

    @Provides
    @PerApplication
    GetExistingUserSessionIdUseCase provideGetExistingUserSessionIdUseCase(UserSessionSettingsRepositoryContract repositoryContract, @Named(NamedConstants.Threading.APPLICATION_MAIN_EXECUTION_THREAD) ExecutionThread executionThread, @Named(NamedConstants.Threading.APPLICATION_POST_EXECUTION_THREAD) PostExecutionThread postExecutionThread) {
        return new GetExistingUserSessionIdUseCase(repositoryContract, executionThread, postExecutionThread);
    }

    @Provides
    @PerApplication
    GetUserSessionUseCase provideGetUserSessionUseCase(UserSessionRepositoryContract repositoryContract, @Named(NamedConstants.Threading.APPLICATION_MAIN_EXECUTION_THREAD) ExecutionThread executionThread, @Named(NamedConstants.Threading.APPLICATION_POST_EXECUTION_THREAD) PostExecutionThread postExecutionThread) {
        return new GetUserSessionUseCase(executionThread, postExecutionThread, repositoryContract);
    }

    @Provides
    @PerApplication
    DestroyUserSessionUseCase provideDestroyUserSessionUseCase(UserSessionRepositoryContract repositoryContract, @Named(NamedConstants.Threading.APPLICATION_MAIN_EXECUTION_THREAD) ExecutionThread executionThread, @Named(NamedConstants.Threading.APPLICATION_POST_EXECUTION_THREAD) PostExecutionThread postExecutionThread) {
        return new DestroyUserSessionUseCase(executionThread, postExecutionThread, repositoryContract);
    }

    @Provides
    @PerApplication
    SaveUserSessionUseCase provideSaveUserSessionUseCase(UserSessionRepositoryContract repositoryContract, @Named(NamedConstants.Threading.APPLICATION_MAIN_EXECUTION_THREAD) ExecutionThread executionThread, @Named(NamedConstants.Threading.APPLICATION_POST_EXECUTION_THREAD) PostExecutionThread postExecutionThread) {
        return new SaveUserSessionUseCase(executionThread, postExecutionThread, repositoryContract);
    }

    @Provides
    @PerApplication
    SaveUserInfoUseCase provideSaveUserSettingsUseCase(UserRepositoryContract repositoryContract, @Named(NamedConstants.Threading.APPLICATION_MAIN_EXECUTION_THREAD) ExecutionThread executionThread, @Named(NamedConstants.Threading.APPLICATION_POST_EXECUTION_THREAD) PostExecutionThread postExecutionThread) {
        return new SaveUserInfoUseCase(repositoryContract, postExecutionThread, executionThread);
    }

    @Provides
    @PerApplication
    SaveExistingUserSessionIdUseCase provideSaveExistingUserSessionIdUseCase(UserSessionSettingsRepositoryContract repositoryContract, @Named(NamedConstants.Threading.APPLICATION_MAIN_EXECUTION_THREAD) ExecutionThread executionThread, @Named(NamedConstants.Threading.APPLICATION_POST_EXECUTION_THREAD) PostExecutionThread postExecutionThread) {
        return new SaveExistingUserSessionIdUseCase(repositoryContract, executionThread, postExecutionThread);
    }

    @Provides
    @PerApplication
    CreateUserSessionFromUserUseCase provideInitDefaultUserSessionUseCase(UserSessionRepositoryContract repositoryContract, @Named(NamedConstants.Threading.APPLICATION_MAIN_EXECUTION_THREAD) ExecutionThread executionThread, @Named(NamedConstants.Threading.APPLICATION_POST_EXECUTION_THREAD) PostExecutionThread postExecutionThread) {
        return new CreateUserSessionFromUserUseCase(executionThread, postExecutionThread, repositoryContract);
    }

    @Provides
    @PerApplication
    UserSessionManagerContract provideUserSessionManager(GetUserSessionUseCase getUserSessionUseCase, GetExistingUserSessionIdUseCase getExistingUserSessionIdUseCase, SaveExistingUserSessionIdUseCase saveUserSettingsUseCase, DestroyUserSessionUseCase destroyUserSessionUseCase, CreateUserSessionFromUserUseCase initDefaultUserSessionUseCase, SaveUserSessionUseCase saveUserSessionUseCase) {
        return new UserSessionManager(initDefaultUserSessionUseCase, saveUserSessionUseCase, getUserSessionUseCase, destroyUserSessionUseCase, saveUserSettingsUseCase, getExistingUserSessionIdUseCase);
    }

}
