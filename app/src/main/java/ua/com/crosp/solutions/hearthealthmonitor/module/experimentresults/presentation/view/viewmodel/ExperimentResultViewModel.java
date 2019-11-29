package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;

/**
 * Created by Alexander Molochko on 10/31/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentResultViewModel {
    public String resultName;
    public Date resultDate;
    public String description;
    public Long id;

    public ExperimentResultViewModel() {
    }

    public ExperimentResultViewModel(String resultName, Date resultDate, String description, Long id) {
        this.resultName = resultName;
        this.resultDate = resultDate;
        this.description = description;
        this.id = id;
    }

    public static ExperimentResultViewModel fromEntity(ExperimentResultEntity experimentResultEntity) {
        ExperimentResultViewModel experimentResultViewModel = new ExperimentResultViewModel();
        experimentResultViewModel.description = experimentResultEntity.getGameResultEntity().getGameName();
        experimentResultViewModel.id = experimentResultEntity.getEntityId().getId();
        experimentResultViewModel.resultName = experimentResultEntity.getExperimentName();
        experimentResultViewModel.resultDate = experimentResultEntity.getDateOfCreation().getDate();
        return experimentResultViewModel;
    }


    public static class List extends ArrayList<ExperimentResultViewModel> {
        public List() {
        }

        public List(@NonNull Collection<? extends ExperimentResultViewModel> c) {
            super(c);
        }

        public static List fromEntityList(ExperimentResultEntity.List experimentResultEntities) {
            ExperimentResultViewModel.List viewModels = new List();
            for (ExperimentResultEntity experimentResultEntity : experimentResultEntities) {
                viewModels.add(fromEntity(experimentResultEntity));
            }
            return viewModels;
        }

        public void removeById(Long id) {
            Iterator<ExperimentResultViewModel> i = this.iterator();
            while (i.hasNext()) {
                ExperimentResultViewModel experimentResultModel = i.next(); // must be called before you can call i.remove()
                if (experimentResultModel.id == id) {
                    i.remove();
                    break;
                }
            }
        }
    }

}
