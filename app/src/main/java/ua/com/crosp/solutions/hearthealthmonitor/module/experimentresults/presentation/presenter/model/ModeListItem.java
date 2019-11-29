package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.presenter.model;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

/**
 * Mode model for presentation layer
 * Used in modes list
 */
public class ModeListItem {
    public static final int MAX_CHARS = 100;
    public int id;
    public String title;
    public String shortDescription;
    public String description;
    public String imageUrl;
    public String icon;

    public ModeListItem(int id, String title, String description, String icon, String imageUrl) {
        this.id = id;
        this.title = title;
        this.shortDescription = description.substring(0, MAX_CHARS);
        this.imageUrl = imageUrl;
        this.description = description;
        this.icon = icon;
    }
}
