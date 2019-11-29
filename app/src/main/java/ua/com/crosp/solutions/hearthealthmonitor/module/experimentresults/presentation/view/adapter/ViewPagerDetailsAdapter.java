package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.adapter;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Molochko on 10/31/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ViewPagerDetailsAdapter extends PagerAdapter {
    private final List<View> mViewsList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public ViewPagerDetailsAdapter() {
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View viewToCreate = mViewsList.get(position);
        if (viewToCreate.getParent() == collection) {
            collection.removeView(viewToCreate);
        }
        collection.addView(viewToCreate);
        return viewToCreate;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mViewsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void addView(View view, String title) {
        mViewsList.add(view);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
