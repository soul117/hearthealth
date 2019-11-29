package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain;

import android.util.Base64;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.AudioRecordingConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.DateTimeConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.FileSystemConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper.XmlMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.write.AndroidWavFileHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.media.audio.rx.RxStreamAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.AndroidAudioUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.model.EcgResultXMLModel;

import static ua.com.crosp.solutions.hearthealthmonitor.configuration.FileSystemConfiguration.FILE_AUDIO_ECG;

/**
 * Created by Alexander Molochko on 4/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GetFinalExperimentResultUseCase extends BaseSingleUseCase<Long, String> {
    private XmlMapperContract mXmlMapper;
    private FileManager mFileManager;

    public GetFinalExperimentResultUseCase(XmlMapperContract xmlMapper, FileManager fileManager, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mXmlMapper = xmlMapper;
        mFileManager = fileManager;
    }

    @Override
    public Single<String> provideSingleObservable(Long aLong) {
        EcgResultXMLModel item = mXmlMapper.convertFromXmlFromAssets("1510586421963.xml", EcgResultXMLModel.class);
        String filePath =
                String.format(
                        FileSystemConfiguration.DIRECTORY_PATH_USERS_DATA_FORMAT_SUBDIR,
                        "4444",
                        String.format(FILE_AUDIO_ECG, DateUtils.formatCurrentDateTime(DateTimeConfiguration.DEFAULT_DATE_TIME_FILE_FORMAT)));
        filePath += ".wav";
        filePath = mFileManager.getExternalSdCardPath() + filePath;
        ReactiveAudioRecorder.Settings audioSettings = new RxStreamAudioRecorder.Settings.Builder()
                .sampleRate(AndroidAudioUtils.SampleRate.RATE_8K)
                .readBufferSize(AudioRecordingConfiguration.READ_BUFFER_SIZE)
                .mono()
                .encodingFormat(AudioRecordingConfiguration.RECORDING_FORMAT)
                .micAudioSource()
                .build();
        try {
            FileManager.FileHandler fileHandler = mFileManager.createFromFilePath(filePath);
            AndroidWavFileHandler androidWavFileHandler = AndroidWavFileHandler.createFromAudioSettings(filePath, audioSettings);
            byte[] binaryData = Base64.decode(item.ecgBinEncodedData, Base64.DEFAULT);
            androidWavFileHandler.writeByteArray(binaryData);
            androidWavFileHandler.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return Single.just("");
    }
}
