package com.example.evan.androidviewertemplates.team_details;

import android.content.Context;
import android.content.Intent;

import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;

import org.jcodec.common.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Katherine on 3/27/2018.
 */

public class LastFourMatchesSectionAdapter extends TeamSectionAdapter {

    public LastFourMatchesSectionAdapter(Context context, Integer teamNumber) {
        super(context, teamNumber,
                rankInsteadOfGraph,
                createListOnClick,
                notClickableFields,
                shouldDisplayAsLongText,
                shouldDisplayAsFurtherInformation,
                displayAsUnranked,
                shouldDisplayAsPercentage,
                sectionTitles,
                fieldsToDisplay);
    }

    static String[] rankInsteadOfGraph = {
            "calculatedData.lfmHabLineSuccessL1", "calculatedData.lfmHabLineSuccessL2",
            "calculatedData.lfmAvgOrangesScored", "calculatedData.lfmAvgLemonsScored", "calculatedData.lfmAvgOrangesFouls", "calculatedData.lfmLemonLoadSuccess",
            "calculatedData.lfmOrangeCycleAll", "calculatedData.lfmLemonCycleAll", "calculatedData.lfmOrangeSuccessDefended", "calculatedData.lfmOrangeSuccessL2",
            "calculatedData.lfmOrangeSuccessL3", "calculatedData.lfmLemonSuccessDefended", "calculatedData.lfmLemonSuccessL2", "calculatedData.lfmLemonSuccessL3",
            "calculatedData.lfmAvgTimeClimbing",
            "calculatedData.lfmPercentIncap", "calculatedData.lfmAvgTimeIncap", "calculatedData.lfmPercentNoShow",
    };

    static String[] createListOnClick = {

    };

    static String[] notClickableFields = {
    };

    static String[] shouldDisplayAsFurtherInformation = {
    };

    static String[] shouldDisplayAsLongText = {
    };

    static String[] displayAsUnranked = {
    };

    static String[] shouldDisplayAsPercentage = {

    };

    static String[] sectionTitles = {
            //May need to change depending on game
            "Sandstorm",
            "Teleop",
            "Endgame",
            "Status"
    };

    static String[][] fieldsToDisplay = {
            //Each {}, below correlates to its sectionTitles above
            {"calculatedData.lfmHabLineSuccessL1", "calculatedData.lfmHabLineSuccessL2",},
            {"calculatedData.lfmAvgOrangesScored", "calculatedData.lfmAvgLemonsScored", "calculatedData.lfmAvgOrangesFouls", "calculatedData.lfmLemonLoadSuccess", "calculatedData.lfmOrangeCycleAll", "calculatedData.lfmLemonCycleAll", "calculatedData.lfmOrangeSuccessDefended", "calculatedData.lfmOrangeSuccessL2", "calculatedData.lfmOrangeSuccessL3", "calculatedData.lfmLemonSuccessDefended", "calculatedData.lfmLemonSuccessL2", "calculatedData.lfmLemonSuccessL3"},
            {"calculatedData.lfmAvgTimeClimbing"},
            {"calculatedData.lfmPercentIncap", "calculatedData.lfmAvgTimeIncap", "calculatedData.lfmPercentNoShow",}
    };


    @Override
    public void handleNonDefaultClick(int section, int row) {
        String key = (String) getRowItem(section, row);
        if (key.equals("fullcomp")) {
            Intent fullcompIntent = new Intent(context, TeamDetailsActivity.class);
            fullcompIntent.putExtra("teamNumber", teamNumber);
            context.startActivity(fullcompIntent);
        }
    }

    @Override
    public String getUpdatedAction() {
        return Constants.TEAMS_UPDATED_ACTION;
    }

    @Override
    public Object getObject() {
        return FirebaseLists.teamsList.getFirebaseObjectByKey(teamNumber.toString());
    }

    @Override
    public List<Object> getObjectList() {
        List<Object> teams = new ArrayList<>();
        teams.addAll(FirebaseLists.teamsList.getValues());
        return teams;
    }
}
