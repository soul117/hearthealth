package ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module;

import android.content.res.AssetManager;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerInfrastructure;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper.XmlMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper.xml.XmlMapper;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerInfrastructure
@Module
public class XmlModule {

    @PerInfrastructure
    @Provides
    XmlMapperContract provideXmlMapper(AssetManager assetManager) {
        return new XmlMapper(assetManager);
    }

}
