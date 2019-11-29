package ua.com.crosp.solutions.hearthealthmonitor.module.report.contract.delivery;

import io.reactivex.Observable;

/**
 * Created by Alexander Molochko on 12/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ReportGeneratorContract<T, O> {
    public Observable<O> generateReport(T inputData);

    public int provideReportDataType();
}
