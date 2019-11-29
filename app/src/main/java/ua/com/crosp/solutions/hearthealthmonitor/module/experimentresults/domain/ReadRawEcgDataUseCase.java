package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.domain;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.read.WaveFileReader;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.EcgRawAudioData;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ReadRawEcgDataUseCase extends BaseSingleUseCaseContract<String, EcgRawAudioData> {
    private FileManager mFileManager;

    public ReadRawEcgDataUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, FileManager fileManager) {
        super(executionThread, postExecutionThread);
        mFileManager = fileManager;
    }

    @Override
    public Single<EcgRawAudioData> provideSingleObservable(String path) {
        WaveFileReader waveFileReader = new WaveFileReader(path);
        return Single.just(EcgRawAudioData.fromIntArray(waveFileReader.getData()));
    }
}
