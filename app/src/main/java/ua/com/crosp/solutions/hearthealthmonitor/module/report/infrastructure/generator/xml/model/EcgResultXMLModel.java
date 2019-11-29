package ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

@Root(name = "ecg_bioss_data")
public class EcgResultXMLModel {
    // Attributes
    @Attribute(name = "version")
    public String version;
    // Elements
    @Element(name = "date")
    public Date recordingDate;
    @Element(name = "diagnostic", required = false)
    public String diagnostic;
    @Element(name = "patient")
    public EcgPatientXMLModel patient;
    @ElementList(name = "notes", required = false)
    public ArrayList<String> notes;
    @Element(name = "audio_format")
    public EcgAudioFormatXMLModel audioFormat;
    @Element(name = "polarity")
    public String polarity;
    @Element(name = "ecg_bin_data")
    public String ecgBinEncodedData;
}
