package com.example.evan.androidviewertemplates.graphing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.evan.androidviewertemplates.MainActivity;
import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.drawer_fragments.data_comparison.DataComparisonHorizontalGraphingActivityTIMD;
import com.example.evan.androidviewertemplates.drawer_fragments.data_comparison.DataComparisonTIMDTabbedActivity;
import com.example.evan.androidviewertemplates.team_in_match_details.TeamInMatchDetailsActivity;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;
import com.example.evan.androidviewertools.ViewerActivity;

public class RankingsActivity extends AppCompatActivity {
    public Integer teamNumber;
    public static Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        teamNumber = getIntent().getIntExtra("team", 0);
        Intent intent = new Intent(context, DataComparisonTIMDTabbedActivity.class);
        intent.putExtra("selectedDatapoint", (getIntent().getStringExtra("field")));
        intent.putExtra("teamOne", String.valueOf(teamNumber));
        intent.putExtra("teamTwo", String.valueOf(teamNumber));
        intent.putExtra("teamThree", "?");
        intent.putExtra("teamFour", "?");
        intent.putExtra("isTIMD", "true");
        startActivity(intent);
    }

}
