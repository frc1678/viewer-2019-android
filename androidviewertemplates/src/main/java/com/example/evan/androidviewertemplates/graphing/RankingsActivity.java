package com.example.evan.androidviewertemplates.graphing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.example.evan.androidviewertemplates.MainActivity;
import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.drawer_fragments.data_comparison.DataComparisonHorizontalGraphingActivityTIMD;
import com.example.evan.androidviewertemplates.drawer_fragments.data_comparison.DataComparisonTIMDTabbedActivity;
import com.example.evan.androidviewertemplates.team_in_match_details.TeamInMatchDetailsActivity;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;
import com.example.evan.androidviewertools.ViewerActivity;

public class RankingsActivity extends ViewerActivity {
    private boolean isShowingGraph;

    public Integer teamNumber;
    public static Activity context;

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_rankings);
        String field = getIntent().getStringExtra("field");
        setTitle(SpecificConstants.KEYS_TO_TITLES.get(field));
        //Log.e("setTitle", SpecificConstants.KEYS_TO_TITLES.get(field));
        context = this;
        setActionBarColor();
        teamNumber = getIntent().getIntExtra("team", 0);
        Intent intent = new Intent(context, DataComparisonTIMDTabbedActivity.class);
        intent.putExtra("selectedDatapoint",convertToWithoutCD(field));
        intent.putExtra("teamOne", String.valueOf(teamNumber));
	    intent.putExtra("teamTwo", String.valueOf(teamNumber));
	    intent.putExtra("teamThree", "?");
	    intent.putExtra("teamFour", "?");
	    intent.putExtra("isTIMD","false");

	    startActivity(intent);
    }

    public void setActionBarColor() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#65C423"));
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(colorDrawable);
        }
    }

    public String convertToWithoutCD(String datapoint) {
        if (datapoint != null && datapoint.contains("calculatedData.")) {
            return datapoint.substring(datapoint.lastIndexOf(".") + 1);
        }
        return null;
    }


    @Override
    public Intent getMainActivityIntent() {
        return new Intent(this, MainActivity.class);
    }

}
