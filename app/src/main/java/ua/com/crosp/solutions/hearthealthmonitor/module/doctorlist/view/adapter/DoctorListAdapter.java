package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.viewmodel.DoctorListItem;

/**
 * Created by Alexander Molochko on 10/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorListAdapter extends
        RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private DoctorListItem.List mDataSource;


    public DoctorListAdapter(DoctorListItem.List dataSource, Context context) {
        mDataSource = dataSource;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View contactView = mLayoutInflater.inflate(R.layout.list_item_doctor, parent, false);
        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        DoctorListItem doctor = mDataSource.get(position);
        // Set item views based on your views and data model
        holder.textViewName.setText(doctor.getDoctorName());
        holder.checkBoxSelect.setChecked(doctor.isSelected);
        holder.textViewEmail.setText(doctor.doctorEmail);
    }

    public void setDataSource(DoctorListItem.List dataSource) {
        mDataSource = dataSource;
    }

    public DoctorListItem.List getDoctorsList() {
        return mDataSource;
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public void addDoctor(DoctorListItem doctorListItem) {
        mDataSource.add(doctorListItem);
        this.notifyDataSetChanged();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewEmail;
        private final TextView textViewName;
        private final CheckBox checkBoxSelect;

        private final CompoundButton.OnCheckedChangeListener onCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                DoctorListItem doctorListItem = mDataSource.get(getLayoutPosition());
                doctorListItem.isSelected = isChecked;
            }
        };

        private final View.OnClickListener onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxSelect.toggle();
            }
        };

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.text_view_doctor_email);
            textViewName = itemView.findViewById(R.id.text_view_doctor_name);
            checkBoxSelect = itemView.findViewById(R.id.checkbox_select_doctor);
            checkBoxSelect.setOnCheckedChangeListener(onCheckChangeListener);
            itemView.setOnClickListener(onItemClickListener);
        }
    }
}