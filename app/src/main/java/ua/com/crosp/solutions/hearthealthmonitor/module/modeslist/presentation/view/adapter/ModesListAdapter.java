package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.listeners.OnItemClickListener;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view.items.ModeListItemView;

/**
 * Created by Alexander Molochko on 2/11/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ModesListAdapter extends RecyclerView.Adapter<ModesListAdapter.ViewHolder> {
    private final OnItemClickListener<ModeListItem> mItemClickListener;
    private List<ModeListItem> mDataSource;

    public ModesListAdapter(OnItemClickListener<ModeListItem> itemClickListener) {
        mDataSource = new ArrayList<>();
        mItemClickListener = itemClickListener;
    }

    public ModesListAdapter(List<ModeListItem> dataset, OnItemClickListener<ModeListItem> itemClickListener) {
        mDataSource = dataset;
        mItemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ModeListItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(mDataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public void setDataSource(List<ModeListItem> dataSource) {
        mDataSource = dataSource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(mDataSource.get(getAdapterPosition()), v, getAdapterPosition());
                }
            });
        }

        public void bindView(ModeListItem modeListItem) {
            ModeListItemView viewItem = (ModeListItemView) itemView;
            viewItem.setTitle(modeListItem.title);
            viewItem.setDescription(modeListItem.shortDescription);
            viewItem.setTextIcon(modeListItem.icon);
            viewItem.setTextIconColor(viewItem.getContext().getResources().getColor(R.color.deep_silver));
        }
    }
}
