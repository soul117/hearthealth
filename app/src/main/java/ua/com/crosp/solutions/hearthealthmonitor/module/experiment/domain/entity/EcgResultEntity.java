package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.model.Entity;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FilePath;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.GeneralDate;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.DateTimeConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.FileSystemConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgResultEntity extends Entity {
    public static final EcgResultEntity INVALID = createNullableEntity();
    private FilePath mRawRecordingPath;
    private FilePath mPdfRecordingPath;
    private FilePath mXmlRecordingPath;
    private Date mDateRecordingStart;
    private Date mDateRecordingStop;
    private List<Long> mRRIntervalValues;
    private Long mEstimatedRecordingTimeInSeconds;
    private boolean mWasInterrupted;

    private static final String XML_EXTENSION = ".xml";
    public static final String ECG_FILE_PREFIX = "/ecg_";
    public static final String XML_FILE_PREFIX = "/xml_";
    public static final String WAV_EXTENSION = ".wav";
    public static final String RESULT_FILE_PATH_FORMAT = "%s%s%s%s";

    public void setEntityId(Long id) {
        setEntityId(new EntityId(id));
    }


    public static EcgResultEntity createNullableEntity() {
        EcgResultEntity ecgResultEntity = new EcgResultEntity();
        ecgResultEntity.setEntityId(EntityId.INVALID_ENTITY_ID);
        ecgResultEntity.setDateRecordingStart(GeneralDate.NULLABLE.getDate());
        ecgResultEntity.setDateRecordingStop(GeneralDate.NULLABLE.getDate());
        ecgResultEntity.setWasInterrupted(false);
        ecgResultEntity.setEstimatedRecordingTimeInSeconds(0L);
        ecgResultEntity.setRRIntervalValues(new ArrayList<Long>());
        ecgResultEntity.setPdfRecordingPath(FilePath.NULLABLE_FILE_PATH);
        ecgResultEntity.setXmlRecordingPath(FilePath.NULLABLE_FILE_PATH);
        ecgResultEntity.setRawRecordingPath(FilePath.NULLABLE_FILE_PATH);
        return ecgResultEntity;
    }

    public String generateAudioFilePath(String baseFilePath) {
        return baseFilePath + FileSystemConfiguration.FILE_AUDIO_ECG;
    }

    public String generateXmlFilePath(String baseFilePath) {
        return baseFilePath + FileSystemConfiguration.FILE_XML_ECG;
    }

    public String generatePDFFilePath(String baseFilePath) {
        return baseFilePath + FileSystemConfiguration.FILE_PDF_ECG;
    }

    public Long getEstimatedRecordingTimeInSeconds() {
        return mEstimatedRecordingTimeInSeconds;
    }

    public void setEstimatedRecordingTimeInSeconds(Long estimatedRecordingTimeInSeconds) {
        mEstimatedRecordingTimeInSeconds = estimatedRecordingTimeInSeconds;
    }

    public boolean isWasInterrupted() {
        return mWasInterrupted;
    }

    public void setWasInterrupted(boolean wasInterrupted) {
        mWasInterrupted = wasInterrupted;
    }

    public FilePath getPdfRecordingPath() {
        return mPdfRecordingPath;
    }

    public void setPdfRecordingPath(FilePath pdfRecordingPath) {
        mPdfRecordingPath = pdfRecordingPath;
    }

    public FilePath getRawRecordingPath() {
        return mRawRecordingPath;
    }

    public FilePath getRawRecordingPathAndInit(String baseExperimentPath) {
        mRawRecordingPath = new FilePath(String.format(RESULT_FILE_PATH_FORMAT, baseExperimentPath, ECG_FILE_PREFIX, DateUtils.formatCurrentDateTime(DateTimeConfiguration.DEFAULT_DATE_TIME_FILE_FORMAT), WAV_EXTENSION));
        return mRawRecordingPath;
    }

    public void setRawRecordingPath(FilePath rawRecordingPath) {
        mRawRecordingPath = rawRecordingPath;
    }

    public FilePath getXmlRecordingPath() {
        return mXmlRecordingPath;
    }

    public void setXmlRecordingPath(FilePath xmlRecordingPath) {
        mXmlRecordingPath = xmlRecordingPath;
    }

    public Date getDateRecordingStart() {
        return mDateRecordingStart;
    }

    public void setDateRecordingStart(Date dateRecordingStart) {
        mDateRecordingStart = dateRecordingStart;
    }

    public Date getDateRecordingStop() {
        return mDateRecordingStop;
    }

    public void setDateRecordingStop(Date dateRecordingStop) {
        mDateRecordingStop = dateRecordingStop;
    }

    public List<Long> getRRIntervalValues() {
        return mRRIntervalValues;
    }

    public void setRRIntervalValues(List<Long> rrintervalvalues) {
        mRRIntervalValues = rrintervalvalues;
    }

    public FilePath getXmlReportPathAndInit(String baseExperimentPath) {
        mXmlRecordingPath = new FilePath(String.format(RESULT_FILE_PATH_FORMAT, baseExperimentPath, XML_FILE_PREFIX, DateUtils.formatCurrentDateTime(DateTimeConfiguration.DEFAULT_DATE_TIME_FILE_FORMAT), XML_EXTENSION));
        return mXmlRecordingPath;
    }

    public void setIdIfRequired() {
        if (getEntityId() == EntityId.INVALID_ENTITY_ID || getEntityId().getId() < 0) {
            setEntityId(EntityId.generateUniqueId());
        }
    }
}
