package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.common.exception.data.ItemNotFoundDataException;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.data.ModesRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ModesRepository implements ModesRepositoryContract {

    private static final List<ModeItem> MODES_LIST = new ArrayList<>();

    // TODO Get rid of resources dependency
    public ModesRepository(Resources resources) {
        initStaticItems(resources);
    }

    // Init static Modes available by default
    // TODO fetch from server, or file
    private void initStaticItems(Resources resources) {
        String descriptionPictureUrl = String.valueOf(R.drawable.ic_heart_background);
        ModeItem modeDormant = new ModeItem(R.id.mode_dormant, resources.getString(R.string.mode_title_dormant), resources.getString(R.string.icon_mode_dormant), resources.getString(R.string.mode_description_dormant), descriptionPictureUrl);
        ModeItem modePsychoemotional = new ModeItem(R.id.mode_psychoemotional, resources.getString(R.string.mode_title_psychoemotional), resources.getString(R.string.icon_mode_psychoemotional), resources.getString(R.string.mode_description_psychoemotional), descriptionPictureUrl);
        ModeItem modePhysicalActivity = new ModeItem(R.id.mode_physical_activity, resources.getString(R.string.mode_title_physical_activity), resources.getString(R.string.icon_mode_physical_activity), resources.getString(R.string.mode_description_physical_activity), descriptionPictureUrl);
        ModeItem modeRespiratory = new ModeItem(R.id.mode_respiratory_gymnastics, resources.getString(R.string.mode_title_respiratory_gymnastics), resources.getString(R.string.icon_mode_respiratory_gymnastics), resources.getString(R.string.mode_description_respiratory_gymnastics), descriptionPictureUrl);
        ModeItem modeRest = new ModeItem(R.id.mode_rest_after_activity, resources.getString(R.string.mode_title_rest_after_activity), resources.getString(R.string.icon_mode_rest_after_activity), resources.getString(R.string.mode_description_rest_after_activity), descriptionPictureUrl);
        MODES_LIST.add(modeDormant);
        MODES_LIST.add(modePsychoemotional);
        MODES_LIST.add(modePhysicalActivity);
        MODES_LIST.add(modeRespiratory);
        MODES_LIST.add(modeRest);
    }

    @Override
    public Single<List<ModeItem>> getAllModes() {
        // Return static mode list
        return Single.just(MODES_LIST);
    }

    @Override
    public Single<ModeItem> getModeById(int modeId) {
        for (int i = 0; i < MODES_LIST.size(); i++) {
            ModeItem current = MODES_LIST.get(i);
            if (current.getId() == modeId) {
                return Single.just(current);
            }
        }
        throw new ItemNotFoundDataException("Mode with the id" + modeId + " was not found");
    }

}
