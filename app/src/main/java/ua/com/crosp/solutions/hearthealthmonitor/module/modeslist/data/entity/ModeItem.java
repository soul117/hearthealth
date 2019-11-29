package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Mode model used in domain layer
 */
public class ModeItem {

    private int mId;
    private String mTitle;
    private String mDescription;
    private String mIcon;
    private String mDescriptionPictureUrl;
    private Map<String, String> mParameters;

    public ModeItem(int id, String title, String icon, String description, String descriptionPictureUrl, Map<String, String> parameters) {
        mId = id;
        mTitle = title;
        mIcon = icon;
        mDescription = description;
        mDescriptionPictureUrl = descriptionPictureUrl;
        mParameters = parameters;
    }

    public ModeItem(int id, String title, String icon, String description, String descriptionPictureUrl) {
        mId = id;
        mIcon = icon;
        mTitle = title;
        mDescription = description;
        mDescriptionPictureUrl = descriptionPictureUrl;
        mParameters = new HashMap<>();
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public Map<String, String> getParameters() {
        return mParameters;
    }

    public void setParameters(Map<String, String> parameters) {
        mParameters = parameters;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModeItem modeItem = (ModeItem) o;

        return mId == modeItem.mId;

    }

    public String getDescriptionPictureUrl() {
        return mDescriptionPictureUrl;
    }

    public void setDescriptionPictureUrl(String descriptionPictureUrl) {
        mDescriptionPictureUrl = descriptionPictureUrl;
    }

    public static class List extends ArrayList<ModeItem> {

    }

    @Override
    public int hashCode() {
        return mId;
    }

    @Override
    public String toString() {
        return "ModeItem{" +
                "id=" + mId +
                ", title='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", icon='" + mIcon + '\'' +
                ", mParameters=" + mParameters +
                '}';
    }
}
