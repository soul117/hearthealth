package ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import ua.com.crosp.solutions.hearthealthmonitor.BuildConfig;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FilePath;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper.XmlMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.read.WaveFileReader;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.EcgResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.EcgRawAudioData;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.contract.delivery.ReportGeneratorContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.model.EcgAudioFormatXMLModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.model.EcgPatientXMLModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.model.EcgResultXMLModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;

/**
 * Created by Alexander Molochko on 12/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class XmlReportGenerator implements ReportGeneratorContract<XmlReportGenerator.ReportPayload, FilePath> {
    public static final int REPORT_TYPE_XML = 22;
    public static final String AUDIO_FORMAT_NAME = "CNTU XML Format";
    public static final String POLARITY_OFF = "OFF";
    private static final int DECIMATION_COEFFICIENT = 8;
    private XmlMapperContract mXmlMapper;
    private FileManager mFileManager;

    public XmlReportGenerator(XmlMapperContract xmlMapper, FileManager fileManager) {
        mXmlMapper = xmlMapper;
        mFileManager = fileManager;
    }

    @Override
    public Observable<FilePath> generateReport(final ReportPayload inputData) {

        return Observable.create(new ObservableOnSubscribe<FilePath>() {
            @Override
            public void subscribe(ObservableEmitter<FilePath> e) throws Exception {
                EcgResultXMLModel ecgResultXMLModel = new EcgResultXMLModel();
                ecgResultXMLModel.audioFormat = generateAudioFormatModel(inputData.experimentResult);
                ecgResultXMLModel.patient = generatePatientModel(inputData.currentUser);
                // Fill info
                ecgResultXMLModel.diagnostic = "Some diagnosis will be here";
                ecgResultXMLModel.polarity = getPolarity();
                ecgResultXMLModel.version = BuildConfig.VERSION_NAME;
                ecgResultXMLModel.notes = new ArrayList<>();
                ecgResultXMLModel.recordingDate = inputData.experimentResult.getDateOfCreation().getDate();
                ecgResultXMLModel.ecgBinEncodedData = generateEcgBinaryData(inputData);
                inputData.pathToSave.setFilePath(mFileManager.getExternalSdCardPath() + inputData.pathToSave.getStringPath());
                // Save xml file to file system
                FilePath filePath = saveXmlReportToFileSystem(inputData.pathToSave, mXmlMapper.convertToXmlString(ecgResultXMLModel));
                e.onNext(filePath);
                e.onComplete();
            }
        });
    }

    private FilePath saveXmlReportToFileSystem(FilePath pathToSave, String xmlString) {
        try {
            mFileManager.makeDirectoriesIfNotExist(pathToSave.getStringPath());
            PrintWriter outFile = new PrintWriter(pathToSave.getStringPath());
            outFile.write(xmlString);
            outFile.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return pathToSave;
        }
    }

    private String generateEcgBinaryData(ReportPayload inputData) {
        WaveFileReader waveFileReader = new WaveFileReader(inputData.experimentResult.getAudioEcgRecordingPath());
        EcgRawAudioData audioData = EcgRawAudioData.fromIntArray(waveFileReader.getData());
        // Get first channel
        int[] data = audioData.getFirstChannelData();
        // Convert to Bytes
        // ByteBuffer byteBguffer = ByteBuffer.allocate(data.length * 4);
        // IntBuffer intBuffer = byteBuffer.asIntBuffer();
        //  intBuffer.put(data);
        // Decimate in 8 times
        byte[] byteDataArray = new byte[(data.length * 2) / DECIMATION_COEFFICIENT];
        int j = 0;
        for (int i = 0; i < data.length; i = i + DECIMATION_COEFFICIENT) {
            //  buffer.putShort((short) data[i]);
            byteDataArray[j] = (byte) (((short) data[i]) & 0xff);
            byteDataArray[j + 1] = (byte) ((((short) data[i]) >> 8) & 0xff);
            j += 2;
        }
        // Convert to byte array
        // byte[] byteDataArray = buffer.array();
        // byte[] byteDataArray = byteBuffer.array();
        // Gzip
        ByteArrayOutputStream byteStream =
                new ByteArrayOutputStream(byteDataArray.length);
        try {
            GZIPOutputStream zipStream =
                    new GZIPOutputStream(byteStream);
            try {
                zipStream.write(byteDataArray);
            } finally {
                zipStream.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                byteStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] compressedData = byteStream.toByteArray();
        // Convert to base64 string
        String base64Data = Base64.encodeToString(compressedData, Base64.DEFAULT);
        return base64Data;

    }

    private String getPolarity() {
        return POLARITY_OFF;
    }

    private EcgPatientXMLModel generatePatientModel(UserEntity currentUser) {
        EcgPatientXMLModel patientXMLModel = new EcgPatientXMLModel();
        patientXMLModel.age = currentUser.getDateOfBirth().getAge();
        patientXMLModel.email = currentUser.getEmail();
        patientXMLModel.firstName = currentUser.getFullName().getFirstName();
        patientXMLModel.lastName = currentUser.getFullName().getLastName();
        patientXMLModel.gender = currentUser.getGender().getGenderString();
        patientXMLModel.personalId = currentUser.getPersonalId().getId();
        return patientXMLModel;
    }

    private EcgAudioFormatXMLModel generateAudioFormatModel(ExperimentResultEntity experimentResultEntity) {
        EcgAudioFormatXMLModel model = new EcgAudioFormatXMLModel();
        EcgResultEntity ecgResultEntity = experimentResultEntity.getEcgResultEntity();
        model.leadTime = -1;
        // Convert to single String separated by commas
        StringBuilder result = new StringBuilder();
        for (Long item : ecgResultEntity.getRRIntervalValues()) {
            result.append(String.valueOf(item));
            result.append(",");
        }

        model.rhythmBlock = result.toString();
        model.totalBlocks = ecgResultEntity.getRRIntervalValues().size();
        model.formatName = AUDIO_FORMAT_NAME;
        return model;
    }

    @Override
    public int provideReportDataType() {
        return REPORT_TYPE_XML;
    }

    public static class ReportPayload {
        public ExperimentResultEntity experimentResult;
        public UserEntity currentUser;
        public FilePath pathToSave;

        public ReportPayload(ExperimentResultEntity experimentResult, UserEntity currentUser, FilePath pathToSave) {
            this.experimentResult = experimentResult;
            this.currentUser = currentUser;
            this.pathToSave = pathToSave;
        }
    }
}
