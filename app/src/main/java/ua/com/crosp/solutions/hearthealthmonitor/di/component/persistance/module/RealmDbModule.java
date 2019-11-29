package ua.com.crosp.solutions.hearthealthmonitor.di.component.persistance.module;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerApplication
@Module
public class RealmDbModule {
    private static final int REALM_SCHEMA_VERSION = 1;
    public static final String REALM_DB_NAME = "heart_monitor.realm";

    public RealmDbModule(@Named(APPLICATION_CONTEXT) final Context context, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread) {
        realmThread.executeBlocking(new Runnable() {
            @Override
            public void run() {
                Realm.init(context);
                //  Realm.deleteRealm(provideRealmConfiguration());
                Realm.setDefaultConfiguration(provideRealmConfiguration(provideRealmMigrationModule()));
            }
        });

    }

    @Provides
    @PerApplication
    Realm provideRealmInstance(@Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread)

    {
        return new Single<Realm>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Realm> observer) {
                try {
                    Realm realmDb = Realm.getDefaultInstance();
                    observer.onSuccess(realmDb);
                } catch (Throwable throwable) {
                    observer.onError(throwable);
                }
            }
        }
                .subscribeOn(realmThread.getScheduler())
                .blockingGet();

    }

    @Provides
    @PerApplication
    RealmMigration provideRealmMigrationModule() {
        return new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                realm.deleteAll();
            }
        };
    }


    @Provides
    @PerApplication
    RealmConfiguration provideRealmConfiguration(RealmMigration migration) {
        return new RealmConfiguration.Builder()
                .name(REALM_DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(REALM_SCHEMA_VERSION)
                .build();
    }

    public void onTerminate() {
        Realm.getDefaultInstance().close();
    }
}
