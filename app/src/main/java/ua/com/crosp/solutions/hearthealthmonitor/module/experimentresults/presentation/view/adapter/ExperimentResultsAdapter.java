package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.listeners.OnItemClickListener;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.DateTimeConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.TimeUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.adapter.items.ExperimentResultItemView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentResultViewModel;

/**
 * Created by Alexander Molochko on 2/11/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentResultsAdapter extends RecyclerView.Adapter<ExperimentResultsAdapter.ViewHolder> {
    private final OnItemClickListener<ExperimentResultViewModel> mItemClickListener;
    private ExperimentResultViewModel.List mDataSource;

    public ExperimentResultsAdapter(OnItemClickListener<ExperimentResultViewModel> itemClickListener) {
        mDataSource = new ExperimentResultViewModel.List();
        mItemClickListener = itemClickListener;
    }

    public ExperimentResultsAdapter(ExperimentResultViewModel.List dataset, OnItemClickListener<ExperimentResultViewModel> itemClickListener) {
        mDataSource = dataset;
        mItemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ExperimentResultItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(mDataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public void setDataSource(ExperimentResultViewModel.List dataSource) {
        mDataSource = dataSource;
    }

    public void onItemRemoved(ExperimentResultViewModel item) {
        mDataSource.removeById(item.id);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(mDataSource.get(getAdapterPosition()), v, getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemClickListener.onItemLongClick(mDataSource.get(getAdapterPosition()), v, getAdapterPosition());
                    return false;
                }
            });
        }

        public void bindView(ExperimentResultViewModel experimentResultViewModel) {
            ExperimentResultItemView viewItem = (ExperimentResultItemView) itemView;
            viewItem.setTitle(experimentResultViewModel.resultName);
            viewItem.setDescription(experimentResultViewModel.description);
            viewItem.setDate(DateUtils.formatDate(experimentResultViewModel.resultDate, DateTimeConfiguration.DEFAULT_DATE_FORMAT));
            viewItem.setTime(TimeUtils.formatTime(experimentResultViewModel.resultDate, DateTimeConfiguration.DEFAULT_TIME_FORMAT, DateTimeConfiguration.DEFAULT_TIME_ZONE));
            viewItem.setIcon(R.drawable.ic_cardiogram_result);
        }
    }
}
