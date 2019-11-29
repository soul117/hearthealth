package ua.com.crosp.solutions.hearthealthmonitor.module.report.infrastructure.generator.xml.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

@Root(name = "patient")
public class EcgPatientXMLModel {
    // Elements
    @Element(name = "name", required = false)
    public String firstName;
    @Element(name = "surname", required = false)
    public String lastName;
    @Element(name = "ID", required = false)
    public Long personalId;
    @Element(name = "sex", required = false)
    public String gender;
    @Element(name = "age", required = false)
    public int age;
    @Element(name = "email", required = false)
    public String email;

}
