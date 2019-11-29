package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.viewmodel;

import java.util.ArrayList;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Email;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FullName;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.DoctorViewModel;

/**
 * Created by Alexander Molochko on 10/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorListItem {
    public Long doctorId;
    public boolean isSelected;
    public String doctorEmail;
    private String doctorFirstName;
    private String doctorLastName;

    public static DoctorListItem fromEntity(DoctorEntity entity) {
        DoctorListItem doctorListItem = new DoctorListItem();
        doctorListItem.isSelected = false;
        doctorListItem.doctorFirstName = entity.getFullName().getFirstName();
        doctorListItem.doctorLastName = entity.getFullName().getLastName();
        doctorListItem.doctorEmail = entity.getEmail().toString();
        doctorListItem.doctorId = entity.getEntityId().getId();
        return doctorListItem;
    }

    public void setDoctorName(String firstName, String lastName) {
        this.doctorFirstName = firstName;
        this.doctorLastName = lastName;
    }

    public String getDoctorName() {
        return doctorFirstName + doctorLastName;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public DoctorViewModel toViewModel() {
        DoctorViewModel doctorViewModel = new DoctorViewModel();
        doctorViewModel.setId(doctorId);
        doctorViewModel.setFirstName(doctorFirstName);
        doctorViewModel.setLastName(doctorLastName);
        doctorViewModel.setEmail(doctorEmail);
        return doctorViewModel;
    }

    public void setId(Long id) {
        doctorId = id;
    }

    public DoctorEntity toEntity() {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setEntityId(new EntityId(doctorId));
        doctorEntity.setFullName(new FullName(doctorFirstName, doctorLastName));
        doctorEntity.setEmail(new Email(doctorEmail));
        return doctorEntity;
    }

    public static class List extends ArrayList<DoctorListItem> {
        public static List fromEntities(DoctorEntity.List entities) {
            List items = new List();
            for (DoctorEntity doctorEntity : entities) {
                items.add(DoctorListItem.fromEntity(doctorEntity));
            }
            return items;
        }

        public static List fromEntitiesWithSelected(DoctorEntity.List entities) {
            List items = new List();
            for (DoctorEntity doctorEntity : entities) {
                items.add(DoctorListItem.fromEntity(doctorEntity));
            }
            return items;
        }

        public java.util.List<Long> getSelectedDoctorIds() {
            java.util.List<Long> doctorIds = new ArrayList<>();

            for (DoctorListItem doctorListItem : this) {
                if (doctorListItem.isSelected) {
                    doctorIds.add(doctorListItem.doctorId);
                }
            }
            return doctorIds;
        }

        public DoctorViewModel.List toSelectedViewModelList() {
            DoctorViewModel.List doctorViewModels = new DoctorViewModel.List();

            for (DoctorListItem doctorListItem : this) {
                if (doctorListItem.isSelected) {
                    doctorViewModels.add(doctorListItem.toViewModel());
                }
            }
            return doctorViewModels;
        }

        public String toSingleString(String separator) {
            StringBuilder sb = new StringBuilder();
            for (DoctorListItem item : this) {
                sb.append(separator).append(item.toString());
            }
            return sb.toString();

        }

        public DoctorListItem getDoctorItemById(long id) {
            for (DoctorListItem doctorListItem : this) {
                if (doctorListItem.doctorId == id) {
                    return doctorListItem;
                }
            }
            return null;
        }

        public void setSelectedDoctors(long[] selectedIds) {
            for (long selectedId : selectedIds) {
                DoctorListItem doctorListItem = getDoctorItemById(selectedId);
                if (doctorListItem != null) {
                    doctorListItem.isSelected = true;
                }
            }
        }
    }
}
