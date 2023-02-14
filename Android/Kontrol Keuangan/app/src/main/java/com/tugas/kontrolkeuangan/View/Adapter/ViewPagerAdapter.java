package com.tugas.kontrolkeuangan.View.Adapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> FragmentList;

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> mFragmentList) {
        super(manager);
        this.FragmentList = mFragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }
}
