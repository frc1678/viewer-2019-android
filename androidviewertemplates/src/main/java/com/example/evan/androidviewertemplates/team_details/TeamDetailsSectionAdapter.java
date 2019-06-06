package com.example.evan.androidviewertemplates.team_details;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import com.example.evan.androidviewertemplates.firebase_classes.CalculatedTeamData;
import com.example.evan.androidviewertemplates.graphing.RankingsActivity;
import com.example.evan.androidviewertemplates.team_ranking.TeamRankingsActivity;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;
import com.example.evan.androidviewertemplates.utils.ViewerDataPoints;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.team_details.MultitypeRankingsSectionAdapter;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by citruscircuits on 1/23/16.
 */
public class TeamDetailsSectionAdapter extends TeamSectionAdapter {
    //todo
    static String[] sectionTitles = {
            //May need to change depending on game
            "Matches",
            "Sandstorm",
            "Teleop",
            "Defense",
            "End Game",
            "Status",
            "High Level",
            "Super Data",
            "Pit Data"
    };

    static String[][] fieldsToDisplay = {
            //Each {}, below correlates to its sectionTitles above
            {"matches", "VIEWER.matchesUntilNextMatchForTeam", "lfm"},
            {"calculatedData.habLineSuccessL1", "calculatedData.habLineSuccessL2","calculatedData.habLineAttemptsL1","calculatedData.habLineAttemptsL2", "calculatedData.avgPanelsScoredSandstorm", "calculatedData.avgCargoScoredSandstorm"},
            {"calculatedData.avgPointsPrevented","calculatedData.avgCargoPointsPrevented","calculatedData.avgPanelPointsPrevented", "calculatedData.avgFailedCyclesCaused","calculatedData.avgCargoScored", "calculatedData.avgPanelsScored", "calculatedData.avgPinningFouls", "calculatedData.avgCargoFouls", "calculatedData.panelLoadSuccess",
                    "calculatedData.cargoSuccessDefended", "calculatedData.cargoSuccessL2",
                    "calculatedData.cargoSuccessL3", "calculatedData.panelSuccessDefended", "calculatedData.panelSuccessL2", "calculatedData.panelSuccessL3",
                    "calculatedData.hasCargoGroundIntake", "calculatedData.hasPanelGroundIntake","calculatedData.totalTimeDefending","calculatedData.avgTimeDefending","calculatedData.failedCyclesCaused",
                    "calculatedData.failedCyclesCausedPerSecond"},
            {"calculatedData.avgPanelPointsPrevented","calculatedData.avgCargoPointsPrevented","calculatedData.avgPointsPrevented"},
            {"calculatedData.avgTimeClimbing", "calculatedData.climbSuccessL2", "calculatedData.climbSuccessL3", "pitSEALsRampRanking"},
            {"calculatedData.avgTimeIncap", "calculatedData.percentIncap", "calculatedData.percentNoShow",
                    "calculatedData.percentDysfunctional"},
            {"calculatedData.predictedSoloPoints", "calculatedData.sdAvgCargoScored", "calculatedData.sdAvgPanelsScored"},
            {"calculatedData.avgRankDefense","calculatedData.avgRankCounterDefense","calculatedData.speedZScore", "calculatedData.agilityZScore", "calculatedData.driverAbility","calculatedData.pushAbility"},
            {"pitWeight", "pitWheelDiameter", "pitDrivetrain", "pitProgrammingLanguage", "pitClimbType", "pitHasCamera",
                    "pitSEALsNotes", "pitIsPanelSpecialist","pitDriveTrainMotorType","pitNumDriveTrainMotors"}
    };

    static String[] shouldDisplayAsPercentage = {
    };

    static String[] displayAsUnranked = {
            "matches",
            "lfm",
            "VIEWER.matchesUntilNextMatchForTeam",
            "pitClimbType",
            "pitSEALsNotes",
            "pitWidth",
            "pitWeight",
            "pitWheelDiameter",
            "pitDrivetrain",
            "pitProgrammingLanguage",
            "pitClimbType",
            "pitHasCamera",
            "pitCanBuddyStartLevel2",
            "pitSEALsNotes",
            "totalSuperNotes",
            "pitLength",
            "pitIsPanelSpecialist",
            "pitDriveTrainMotorType",
            "pitNumDriveTrainMotors"
    };

    static String[] shouldDisplayAsLongText = {
            //These variables should always be displayed as long text. These variables are non year specific variables
            "pitNotes",
            "notes",
            "pitClimbType",
            "pitSEALsNotes",
            "pitClimbType",
            "pitSEALsNotes",
            "pitClimbType",
            "pitSEALsNotes",
            "pitIsPanelSpecialist",
    };

    static String[] shouldDisplayAsFurtherInformation = {
            "matches",
            "notes",
            "lfm"
    };

    static String[] notClickableFields = {
            "VIEWER.matchesUntilNextMatchForTeam",
            "pitLength",
            "pitWidth", "pitWeight",
            "pitWheelDiameter",
            "pitDrivetrain",
            "pitProgrammingLanguage",
            "pitClimbType",
            "pitHasCamera",
            "pitCanBuddyStartLevel2",
            "pitSEALsNotes",
            "pitIsPanelSpecialist",
            "calculatedData.hasCargoGroundIntake",
            "calculatedData.hasPanelGroundIntake",
            "pitNumDriveTrainMotors"
    };

    static String[] createListOnClick = {
            "matches",
            "lfm"
    };

    static String[] rankInsteadOfGraph = {
            "calculatedData.predictedSoloPoints",
            "calculatedData.avgGoodDecisions",
            "calculatedData.avgBadDecisions",
            "calculatedData.sdAvgCargoScored",
            "calculatedData.sdAvgPanelsScored",
            "calculatedData.predictedSoloPoints",
            "calculatedData.driverAbility",
            "calculatedData.pushAbility",
            "calculatedData.climbSuccessL2",
            "calculatedData.climbSuccessL3",
            "calculatedData.habLineSuccessL2",
            "calculatedData.habLineSuccessL1",
            "calculatedData.speedZScore",
            "calculatedData.agilityZScore",
            "calculatedData.pushAbility",
            "pitSEALsRampRanking",
    };


    public TeamDetailsSectionAdapter(Context context, Integer teamNumber) {
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


    @Override
    public void handleNonDefaultClick(int section, int row) {
        String key = (String) getRowItem(section, row);
        if (key.equals("matches")) {
            Intent teamMatchesIntent = new Intent(context, MatchesActivity.class);
            teamMatchesIntent.putExtra("teamNumber", teamNumber).putExtra("field", "matches");
            context.startActivity(teamMatchesIntent);
        } else if (key.equals("lfm")) {
            Intent lfmIntent = new Intent(context, LastFourMatchesActivity.class);
            lfmIntent.putExtra("teamNumber", teamNumber);
            context.startActivity(lfmIntent);
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
