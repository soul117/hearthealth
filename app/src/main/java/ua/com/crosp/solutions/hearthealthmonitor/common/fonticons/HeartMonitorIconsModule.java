package ua.com.crosp.solutions.hearthealthmonitor.common.fonticons;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * Created by Alexander Molochko on 1/16/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class HeartMonitorIconsModule implements IconFontDescriptor {

    @Override
    public String ttfFileName() {
        return "fonticons/heartmonitor.ttf";
    }

    @Override
    public Icon[] characters() {
        return HeartMonitorIcons.values();
    }
}