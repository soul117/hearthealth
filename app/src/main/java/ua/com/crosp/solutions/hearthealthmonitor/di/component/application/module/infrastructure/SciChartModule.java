package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.infrastructure;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
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
public class SciChartModule {

    @Provides
    @PerApplication
    @Named(NamedConstants.Markers.SCI_CHART_LICENSED)
    Boolean provideSciChartLicenseRegistration(@Named(APPLICATION_CONTEXT) Context context) {
        try {
            com.scichart.charting.visuals.SciChartSurface.setRuntimeLicenseKeyFromResource(context, "license");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
