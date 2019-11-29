package ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure;

import android.content.res.AssetManager;

import dagger.Component;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.UtilsModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module.AssetsModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module.ContextModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module.IoModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module.XmlModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerInfrastructure;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper.XmlMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.random.RandomValuesGeneratorContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.resource.ResourceProviderContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper.JsonMapper;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerInfrastructure
@Component(modules = {IoModule.class, UtilsModule.class, ContextModule.class, XmlModule.class, AssetsModule.class})
public interface InfrastructureComponent {
    FileManager exposeFileManager();

    XmlMapperContract exposeXmlMapper();

    AssetManager exposeAssetManager();

    JsonMapper exposeJsonMapper();

    ResourceProviderContract exposeResourceProvider();

    RandomValuesGeneratorContract exposeRandomValuesGenerator();

}
