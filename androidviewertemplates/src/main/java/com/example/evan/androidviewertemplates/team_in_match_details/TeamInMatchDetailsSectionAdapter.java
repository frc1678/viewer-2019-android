package com.example.evan.androidviewertemplates.team_in_match_details;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import com.example.evan.androidviewertemplates.graphing.RankingsActivity;
import com.example.evan.androidviewertemplates.team_details.TeamSectionAdapter;
import com.example.evan.androidviewertemplates.team_ranking.TeamRankingsActivity;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;
import com.example.evan.androidviewertemplates.utils.ViewerDataPoints;
import com.example.evan.androidviewertools.team_details.MultitypeRankingsSectionAdapter;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by colinunger on 1/31/16.
 */
public class TeamInMatchDetailsSectionAdapter extends TeamSectionAdapter {

    private Integer teamNumber;

    private Integer matchNumber;

    //todo
    public TeamInMatchDetailsSectionAdapter(Context context, Integer teamNumber, Integer matchNumber) {
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
        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
    }

    static String[] sectionTitles = {
            //May need to change depending on game
            "Information",
            "Sandstorm",
            "Tele",
            "End Game",
            "Status",
            "Super Data",

    };

    static String[][] fieldsToDisplay = {
            //Each {}, below correlates to its sectionTitles above
            {"teamNumber", "matchNumber", "blueFoulPoints", "redFoulPoints"},
            {"startingLevel", "crossedHabLine", "startingLocation", "preload"},
            {"calculatedData.orangeSuccessDefended","calculatedData.totalFailedCyclesCaused","calculatedData.orangesScored", "calculatedData.lemonsScored", "calculatedData.orangeFouls", "calculatedData.lemonLoadSuccess",
                    "calculatedData.orangeSuccessDefended", "calculatedData.orangeSuccessL2", "calculatedData.orangeSuccessL3",
                    "calculatedData.lemonSuccessDefended", "calculatedData.lemonSuccessL2", "calculatedData.lemonSuccessL3", "calculatedData.pinningFouls",
                    "calculatedData.lemonsScoredTeleL1", "calculatedData.lemonsScoredTeleL2", "calculatedData.lemonsScoredTeleL3",
                    "calculatedData.orangesScoredTeleL1", "calculatedData.orangesScoredTeleL1", "calculatedData.orangesScoredTeleL1",},
            {"calculatedData.selfClimbLevel", "calculatedData.robot1ClimbLevel", "calculatedData.robot2ClimbLevel","calculatedData.timeDefending"},
            {"calculatedData.timeIncap", "calculatedData.timeClimbing"},
            {"rankDefense","notes"},
    };

    static String[] shouldDisplayAsPercentage = {

    };

    static String[] displayAsUnranked = {
            "teamNumber",
            "matchNumber",
            "startingLevel",
            "crossedHabLine",
            "startingLocation",
            "preload"
    };

    static String[] shouldDisplayAsLongText = {
            "notes",
    };
    static String[] notClickableFields = {};

    static String[] createListOnClick = {};

    static String[] rankInsteadOfGraph = {};

    static String[] shouldDisplayAsFurtherInformation = {};


    @Override
    public void handleNonDefaultClick(int section, int row) {
        String key = (String) getRowItem(section, row);
    }

    @Override
    public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
    }

    @Override
    public String getUpdatedAction() {
        return SpecificConstants.TEAM_IN_MATCH_DATAS_UPDATED_ACTION;
    }

    @Override
    public Object getObject() {
        Log.e("TIMD TeamNumber", teamNumber.toString());
        Log.e("TIMD MatchNumber", matchNumber.toString());

        return FirebaseLists.teamInMatchDataList.getFirebaseObjectByKey(teamNumber.toString() + "Q" + matchNumber.toString());
    }

    @Override
    public List<Object> getObjectList() {
        List<Object> objects = new ArrayList<>();
        Log.e("team NUMBER", String.valueOf(teamNumber));
        objects.addAll(Utils.getTeamInMatchDatasForTeamNumber(teamNumber));
        return objects;
    }

    @Override
    public boolean isRowEnabled(int section, int row) {
        return false;
    }

    @Override
    public Intent getGraphActivityIntent() {
        return new Intent(context, RankingsActivity.class);
    }

    @Override
    public Map<String, String> getKeysToTitles() {
        return SpecificConstants.KEYS_TO_TITLES;
    }

    @Override
    public Class<?> getViewerDataPointsClass() {
        return ViewerDataPoints.class;
    }
}
