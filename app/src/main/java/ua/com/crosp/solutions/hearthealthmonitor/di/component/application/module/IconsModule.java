package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module;

import com.joanzapata.iconify.IconFontDescriptor;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.common.fonticons.HeartMonitorIconsModule;

/**
 * Created by Alexander Molochko on 1/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerApplication
public class IconsModule {
    public IconsModule() {
    }

    @Provides
    @PerApplication
    public List<IconFontDescriptor> provideIconFontDescriptors() {
        List<IconFontDescriptor> iconFontDescriptors = new ArrayList<>();
        iconFontDescriptors.add(new HeartMonitorIconsModule());
        return iconFontDescriptors;
    }
}
