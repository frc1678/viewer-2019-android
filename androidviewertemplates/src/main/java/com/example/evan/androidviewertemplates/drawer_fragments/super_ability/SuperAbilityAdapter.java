package com.example.evan.androidviewertemplates.drawer_fragments.super_ability;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.evan.androidviewertemplates.firebase_classes.CalculatedTeamData;
import com.example.evan.androidviewertemplates.firebase_classes.TeamTemplate;

/**
 * Created by colinunger on 1/31/16.
 */
public class SuperAbilityAdapter extends FragmentStatePagerAdapter {
    Context context;
    //todo
    String[] fields = {
            "calculatedData.agilityZScore",
            "calculatedData.speedZScore",
            "calculatedData.driverAbility",
            "calculatedData.avgRankDefense",
            "calculatedData.avgCounterDefense",
            "calculatedData.pushAbility",
    };

    public SuperAbilityAdapter(Context context, FragmentManager mgr) {
        super(mgr);
        this.context = context;
    }

    @Override
    public int getCount() {
        return fields.length;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putString("field", fields[position]);

        SuperAbilityListFragment secondPickAbilityListFragment = new SuperAbilityListFragment();
        secondPickAbilityListFragment.setArguments(argumentsBundle);
        return secondPickAbilityListFragment;
    }
}
