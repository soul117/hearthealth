package ua.com.crosp.solutions.hearthealthmonitor.module.session.data.repository;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.settings.AndroidSettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionSettingsRepositoryContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSessionSettingsRepository implements UserSessionSettingsRepositoryContract {
    private static final String GENERAL_USER_SESSION_ID = "general.user.session_id";
    private SettingsManager mSettingsManager;
    private static final String GENERAL_USER_SETTINGS_BUNDLE_NAME = "general.user_settings";


    public UserSessionSettingsRepository(SettingsManager settingsManager) {
        mSettingsManager = settingsManager;
    }

    @Override
    public Single<Long> getUserSessionId() {
        SettingsBundle bundle
                = mSettingsManager.getSettingsBundle(GENERAL_USER_SETTINGS_BUNDLE_NAME);

        Long sessionId = bundle.getLongSetting(GENERAL_USER_SESSION_ID);
        if (sessionId == AndroidSettingsBundle.DefaultSettingsValue.LONG) {
            return Single.just(EntityId.INVALID_ENTITY_ID.getId());
        }
        return Single.just(sessionId);
    }

    @Override
    public Single<Long> saveUserSessionId(Long sessionId) {
        SettingsBundle bundle
                = mSettingsManager.getSettingsBundle(GENERAL_USER_SETTINGS_BUNDLE_NAME);

        bundle.setLongSetting(GENERAL_USER_SESSION_ID, sessionId);
        return Single.just(sessionId);
    }
}
