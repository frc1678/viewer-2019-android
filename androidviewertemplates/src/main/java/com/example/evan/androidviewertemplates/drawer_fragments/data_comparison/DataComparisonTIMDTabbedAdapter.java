package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataComparisonTIMDTabbedAdapter extends FragmentStatePagerAdapter {
    public List<Fragment> fragmentList = new ArrayList<>();
    public String[] fragmentListTitles = new String[]{"Bar Chart", "Trend Chart"};

    public DataComparisonTIMDTabbedAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles[position];
    }

    public void AddFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
    }

}