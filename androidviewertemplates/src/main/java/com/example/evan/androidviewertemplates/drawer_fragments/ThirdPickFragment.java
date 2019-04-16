package com.example.evan.androidviewertemplates.drawer_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.evan.androidviewertemplates.team_details.TeamDetailsActivity;
import com.example.evan.androidviewertemplates.utils.Util;
import com.example.evan.androidviewertools.team_ranking.TeamRankingsAdapter;
import com.example.evan.androidviewertools.team_ranking.TeamRankingsFragment;
import com.example.evan.androidviewertools.utils.Constants;

public class ThirdPickFragment extends TeamRankingsFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.isInSeedingFragment = false;
        Util.setAllSortConstantsFalse();
        setListAdapter(new ThirdPickFragment.ThirdPickAdapter(getActivity().getApplicationContext()));
    }

    /**
     * Created by colinunger on 1/28/16.
     */
    //some change
    public static class ThirdPickAdapter extends TeamRankingsAdapter {

        public ThirdPickAdapter(Context context) {
            super(context, "calculatedData.thirdPickAbility", "calculatedData.thirdPickAbility", false);
        }

        @Override
        public Intent getTeamDetailsActivityIntent() {
            return new Intent(context, TeamDetailsActivity.class);
        }
    }

    @Override
    public Intent getTeamDetailsActivityIntent() {
        return new Intent(getActivity(), TeamDetailsActivity.class);
    }
}
