package ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

@Root(name = "audio_format")
public class EcgAudioFormatXMLModel {
    // Elements
    @Element(name = "format_name")
    public String formatName;
    @Element(name = "total_blocks")
    public long totalBlocks;
    @Element(name = "rhythm_block")
    public String rhythmBlock;
    @Element(name = "lead_time")
    public double leadTime;

}
