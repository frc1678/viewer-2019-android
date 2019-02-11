package com.example.evan.androidviewertools.match_listing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evan.androidviewertools.ViewerActivity;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.firebase_classes.TeamInMatchData;
import com.example.evan.androidviewertools.services.RedFlags;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.firebase.FirebaseList;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;
import com.example.evan.androidviewertools.utils.ObjectFieldComparator;
import com.example.evan.androidviewertools.R;
import com.example.evan.androidviewertools.search_view.SearchableFirebaseListAdapter;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.firebase_classes.Match;
import com.example.evan.androidviewertools.services.StarManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public abstract class MatchesAdapter extends SearchableFirebaseListAdapter<Match> {
    public static Context context;

    public MatchesAdapter(Context context, boolean isNotReversed) {
        super(context, new ObjectFieldComparator("number", isNotReversed));
        this.context = context;

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return filteredValues.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        updateHighlightedTeams();
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.schedule_cell, parent, false);
        }
        try {
            Match match = (Match) getItem(position);

            if (StarManager.isImportantMatch(match.matchNumber) && !Constants.highlightTeamSchedule) {
                rowView.setBackgroundColor(Constants.STAR_COLOR);
            } else {
                rowView.setBackgroundColor(Color.TRANSPARENT);
            }

            TextView matchTextView = (TextView) rowView.findViewById(R.id.matchNumber);
            if (selectedScope.equals("Match")) {
                Integer matchNumber = Integer.valueOf((Integer) Utils.getObjectField(match, "matchNumber"));
                matchTextView.setText(Utils.highlightTextInString(String.valueOf(matchNumber), searchString));
            } else {
                Integer matchNumber = Integer.valueOf((Integer) Utils.getObjectField(match, "matchNumber"));
                matchTextView.setText(String.valueOf(matchNumber));
            }
            List<Object> redTeams = Arrays.asList(Utils.getObjectField(match, "redTeams"));
            List<Object> blueTeams = Arrays.asList(Utils.getObjectField(match, "blueTeams"));
            List<Integer> tempRedAllianceTeams = (List<Integer>) (Object) redTeams.get(0);
            List<Integer> tempBlueAllianceTeams = (List<Integer>) (Object) blueTeams.get(0);
            List<Integer> teamsInMatch = new ArrayList<>();
            for (int i = 0; i < tempRedAllianceTeams.size(); i++) {
                teamsInMatch.add(tempRedAllianceTeams.get(i));
            }
            for (int i = 0; i < tempBlueAllianceTeams.size(); i++) {
                teamsInMatch.add(tempBlueAllianceTeams.get(i));
            }

            int[] teamTextViewIDs = {R.id.teamOne, R.id.teamTwo, R.id.teamThree, R.id.teamFour, R.id.teamFive, R.id.teamSix};
            int teamTextViewIDsSize = teamTextViewIDs.length;
            for (int i = 0; i < teamTextViewIDsSize; i++) {
                TextView teamTextView = (TextView) rowView.findViewById(teamTextViewIDs[i]);

                if (selectedScope.equals("Team")) {
                    teamTextView.setText(Utils.highlightTextInString(teamsInMatch.get(i).toString(), searchString));
                } else {
                    teamTextView.setText(teamsInMatch.get(i).toString());
                }

                Integer team = Integer.parseInt(teamTextView.getText().toString());
                //todo Add

                GradientDrawable gd = new GradientDrawable();

                //Only on Highlight:
                if (onHighlightedTeams(team) && !onStarredMatches(team) && !onTeamPicklist(team) && isRedFlag(team)) {
                    //teamTextView.setBackgroundColor(Color.parseColor("#b8d4fc"));
                    gd.setColor(0xFFb8d4fc);
                    gd.setStroke(1, 0xFFbf1212);
                    teamTextView.setBackground(gd);
                    //Only on Starred:
                } else if (onStarredMatches(team) && !onHighlightedTeams(team) && !onTeamPicklist(team) && isRedFlag(team)) {
                    //teamTextView.setBackgroundColor(Color.parseColor("#e2f442"));
                    gd.setColor(0xFFe2f442);
                    gd.setStroke(1, 0xFFbf1212);
                    teamTextView.setBackground(gd);
                    //Only on Picklist:
                } else if (!onStarredMatches(team) && !onHighlightedTeams(team) && onTeamPicklist(team) && isRedFlag(team)) {
                    //teamTextView.setBackgroundColor(Color.parseColor("#f98181"));
                    gd.setColor(0xFFf98181);
                    gd.setStroke(1, 0xFFbf1212);
                    teamTextView.setBackground(gd);
                    //On ALL:
                } else if (onStarredMatches(team) && onHighlightedTeams(team) && onTeamPicklist(team) && isRedFlag(team)) {
                    //teamTextView.setBackgroundColor(Color.parseColor("#e572e1"));
                    gd.setColor(0xFFe572e1);
                    gd.setStroke(1, 0xFFbf1212);
                    teamTextView.setBackground(gd);
                    //On Highlight && Starred
                } else if (onStarredMatches(team) && onHighlightedTeams(team) && !onTeamPicklist(team) && isRedFlag(team)) {
                    //teamTextView.setBackgroundColor(Color.parseColor("#808000"));
                    gd.setColor(0xFF808000);
                    gd.setStroke(1, 0xFFbf1212);
                    teamTextView.setBackground(gd);
                    //Only Starred && Picklist
                } else if (onStarredMatches(team) && !onHighlightedTeams(team) && onTeamPicklist(team) && isRedFlag(team)) {
                    //teamTextView.setBackgroundColor(Color.parseColor("#f4a142"));
                    gd.setColor(0xFFf4a142);
                    gd.setStroke(1, 0xFFbf1212);
                    teamTextView.setBackground(gd);
                    //On RedFlag:
                } else if (!onStarredMatches(team) && !onHighlightedTeams(team) && !onTeamPicklist(team) && isRedFlag(team)) {
                    //teamTextView.setBackgroundColor(Color.parseColor("#f4a142"));
                    gd.setStroke(1, 0xFFbf1212);
                    teamTextView.setBackground(gd);
                    //On Picklist && Highlight:
                } else if (!onStarredMatches(team) && onHighlightedTeams(team) && onTeamPicklist(team) && isRedFlag(team)) {
                    //teamTextView.setBackgroundColor(Color.parseColor("#fcb8e7"));
                    gd.setColor(0xFFfcb8e7);
                    gd.setStroke(1, 0xFFbf1212);
                    teamTextView.setBackground(gd);

                } else if (onHighlightedTeams(team) && !onStarredMatches(team) && !onTeamPicklist(team) && !isRedFlag(team)) {
                    teamTextView.setBackgroundColor(Color.parseColor("#b8d4fc"));
                    //Only on Starred:
                } else if (onStarredMatches(team) && !onHighlightedTeams(team) && !onTeamPicklist(team) && !isRedFlag(team)) {
                    teamTextView.setBackgroundColor(Color.parseColor("#e2f442"));
                    //Only on Picklist:
                } else if (!onStarredMatches(team) && !onHighlightedTeams(team) && onTeamPicklist(team) && !isRedFlag(team)) {
                    teamTextView.setBackgroundColor(Color.parseColor("#f98181"));
                    //On ALL:
                } else if (onStarredMatches(team) && onHighlightedTeams(team) && onTeamPicklist(team) && !isRedFlag(team)) {
                    teamTextView.setBackgroundColor(Color.parseColor("#e572e1"));
                    //On Highlight && Starred
                } else if (onStarredMatches(team) && onHighlightedTeams(team) && !onTeamPicklist(team) && !isRedFlag(team)) {
                    teamTextView.setBackgroundColor(Color.parseColor("#808000"));
                    //Only Starred && Picklist:
                } else if (onStarredMatches(team) && !onHighlightedTeams(team) && onTeamPicklist(team) && !isRedFlag(team)) {
                    teamTextView.setBackgroundColor(Color.parseColor("#f4a142"));
                    //On Picklist && Highlight:
                } else if (!onStarredMatches(team) && onHighlightedTeams(team) && onTeamPicklist(team) && !isRedFlag(team)) {
                    teamTextView.setBackgroundColor(Color.parseColor("#fcb8e7"));
                } else {
                    teamTextView.setBackgroundColor(Color.TRANSPARENT);
                }
            }




                TextView redScoreTextView = (TextView) rowView.findViewById(R.id.redScore);
                TextView blueScoreTextView = (TextView) rowView.findViewById(R.id.blueScore);

                //these are created in order to get Red/Blue scores using a key and value instead of value (match.redScore/blueScore)
                Integer redScore = Integer.parseInt(Utils.getMatchDisplayValue(match, "redActualScore"));
                Integer blueScore = Integer.parseInt(Utils.getMatchDisplayValue(match,"blueActualScore"));
        if (redScore != null || blueScore != null) {
            redScoreTextView.setText((redScore != null) ? redScore.toString() : "???");
            blueScoreTextView.setText((blueScore != null) ? blueScore.toString() : "???");
            redScoreTextView.setTextColor(Color.argb(255, 255, 0, 0));
            blueScoreTextView.setTextColor(Color.argb(255, 0, 0, 255));
        } else {
            redScoreTextView.setTextColor(Color.argb(75, 255, 0, 0));
            blueScoreTextView.setTextColor(Color.argb(75, 0, 0, 255));
            redScoreTextView.setText((Utils.fieldIsNotNull(match, "calculatedData.redPredictedScore")) ? Utils.roundDataPoint(Utils.getObjectField(match, "calculatedData.redPredictedScore"), 2, "???") : "???");
            blueScoreTextView.setText((Utils.fieldIsNotNull(match, "calculatedData.bluePredictedScore")) ? Utils.roundDataPoint(Utils.getObjectField(match, "calculatedData.bluePredictedScore"), 2, "???") : "???");
        }
                TextView rankingPointDisplayBlue = (TextView) rowView.findViewById(R.id.rankingPointDisplayBlue);
                TextView rankingPointDisplayRed = (TextView) rowView.findViewById(R.id.rankingPointDisplayRed);

//         Boolean blueDidAutoQuest = Boolean.valueOf(Utils.getObjectField(match, "blueDidAutoQuest").toString());
//         Boolean blueDidFaceBoss = Boolean.valueOf(Utils.getObjectField(match, "blueDidFaceBoss").toString());
//         Boolean redDidAutoQuest = Boolean.valueOf(Utils.getObjectField(match, "redDidAutoQuest").toString());
//         Boolean redDidFaceBoss = Boolean.valueOf(Utils.getObjectField(match, "redDidFaceBoss").toString());
//
////todo Add predicted RPs?
//            if (blueDidAutoQuest && blueDidFaceBoss) {
//             rankingPointDisplayBlue.setText("● ●");
//         } if (blueDidAutoQuest && !blueDidFaceBoss) {
//             rankingPointDisplayBlue.setText("●  ");
//         } if (!blueDidAutoQuest && blueDidFaceBoss) {
//             rankingPointDisplayBlue.setText("    ●");
//         }
//
//        if (redDidAutoQuest && redDidFaceBoss) {
//            rankingPointDisplayRed.setText("● ●");
//        } if (redDidAutoQuest && !redDidFaceBoss) {
//            rankingPointDisplayRed.setText("●  ");
//        } if (!redDidAutoQuest && redDidFaceBoss) {
//            rankingPointDisplayRed.setText("    ●");
//        }

        }catch (NullPointerException NPE){
        }

        if (Constants.highlightTeamSchedule) {
            rowView.setOnLongClickListener(new HighlightTeamListener());

        } else {
            rowView.setOnLongClickListener(new StarLongClickListener());
        }
        rowView.setOnClickListener(new MatchClickListener());

        return rowView;
    }



    @Override
    public boolean filter(Match value, String scope) {
        List<Integer> teamsInMatch = new ArrayList<>();
        try {
            List<Object> redTeams = Arrays.asList(Utils.getObjectField(value, "redTeams"));
            List<Object> blueTeams = Arrays.asList(Utils.getObjectField(value, "blueTeams"));
            List<Integer> tempRedAllianceTeams = (List<Integer>) (Object) redTeams.get(0);
            List<Integer> tempBlueAllianceTeams = (List<Integer>) (Object) blueTeams.get(0);
            for (int i = 0; i < tempRedAllianceTeams.size(); i++) {
                teamsInMatch.add(tempRedAllianceTeams.get(i));
            }
            for (int i = 0; i < tempBlueAllianceTeams.size(); i++) {
                teamsInMatch.add(tempBlueAllianceTeams.get(i));
            }
            //Log.e("Please Reach", "Here");
        }catch (NullPointerException NPE){

        }
        boolean found = false;
        try{
            //Log.e("Value", value.toString());
        }catch (NullPointerException NPE){
            Log.e("secondaryFilter", "NULL");
        }
        if (secondaryFilter(value)) {
            if (searchString.length() == 0) {
                found = true;
            } else if (scope.equals("Team")) {
                for (Integer team : teamsInMatch) {
                    if (team.toString().indexOf(searchString) == 0) {
                        found = true;
                    }
                }
            } else if (scope.equals("Match")) {
                found = value.matchNumber.toString().indexOf(searchString) == 0;
            }
        }

        return found;
    }

    @Override
    public String getBroadcastAction() {
        return Constants.MATCHES_UPDATED_ACTION;
    }

    @Override
    public List<Match> getFirebaseList() {
        return FirebaseLists.matchesList.getValues();
    }

    public static boolean isRedFlag(Integer teamNumber) {
        ArrayList<String> teamsList = new ArrayList<>();
        ArrayList<String> datapointList = new ArrayList<>();
        List<String> datapointValuesTemp = new ArrayList<>();
        List<String> datapointValues = new ArrayList<>();
        List<String> datapointValuesReqs = new ArrayList<>();
        ArrayList<String> sinCounter = new ArrayList<>();


        for (String team : FirebaseLists.teamsList.getKeys()) {
            teamsList.add(team);
        }
        for (String datapoint : RedFlags.RED_FLAG_DATAPOINT_NAMES) {
            datapointList.add(datapoint);
        }
        for (String datapoint : RedFlags.RED_FLAG_DATAPOINTS_RED_VALUE) {
            datapointValuesReqs.add(datapoint);
        }
        if (teamsList.contains(String.valueOf(teamNumber))) {
            for (int i = 0; i < datapointList.size(); i++) {
                Team team = FirebaseLists.teamsList.getFirebaseObjectByKey(String.valueOf(teamNumber));
                String datapoint = (Utils.fieldIsNotNull(team, datapointList.get(i))
                        ? Utils.roundDataPoint(Utils.getObjectField(team, datapointList.get(i)),
                        2, "1000.55") : "1000.55");
                datapointValuesTemp.add(datapoint);
            }
        } else {
            for (int count = 0; count < 5; count ++) {
                datapointValuesTemp.add("1000.55");
            }
        }

        for (int i = 0; i < datapointValuesTemp.size(); i ++ ){
            if (datapointValuesTemp.get(i).contains(".")) {
                datapointValues.add(datapointValuesTemp.get(i));
            } else
            if (datapointValuesTemp.get(i).contains("LabVIEW")) {
                datapointValues.add(datapointValuesTemp.get(i));
            } else
            if (datapointValuesTemp.get(i).contains("C++")) {
                datapointValues.add(datapointValuesTemp.get(i));
            } else
            if (datapointValuesTemp.get(i).contains("Java")) {
                datapointValues.add(datapointValuesTemp.get(i));
            } else
            if (datapointValuesTemp.get(i).contains("Other")) {
                datapointValues.add(datapointValuesTemp.get(i));
            }
             else {
                Integer datapointInt = Integer.valueOf(datapointValuesTemp.get(i));
                datapointValues.add(String.valueOf(datapointInt)+".0");
            }
        }
        if (datapointValues.contains("1000.55")) {
            //todo ADD NULL COUNTER
        } else {
            for (int i = 0; i < datapointValuesReqs.size(); i++) {
                if (datapointValuesReqs.get(i).equals("moreThanZero")) {
                    if (Float.valueOf(datapointValues.get(i)) > 0.0) {
                        sinCounter.add(RedFlags.RED_FLAG_DATAPOINT_NAMES[i]);
                    }
                } else if (datapointValuesReqs.get(i).equals("moreThanOne")) {
                    if (Float.valueOf(datapointValues.get(i)) > 1.0) {
                        sinCounter.add(RedFlags.RED_FLAG_DATAPOINT_NAMES[i]);
                    }
                } else if (datapointValuesReqs.get(i).equals("moreThanTwo")) {
                    if (Float.valueOf(datapointValues.get(i)) > 2.0) {
                        sinCounter.add(RedFlags.RED_FLAG_DATAPOINT_NAMES[i]);
                    }
                } else if (datapointValuesReqs.get(i).equals("moreThanThree")) {
                    if (Float.valueOf(datapointValues.get(i)) > 3.0) {
                        sinCounter.add(RedFlags.RED_FLAG_DATAPOINT_NAMES[i]);
                    }
                } else if (datapointValuesReqs.get(i).equals("moreThanFour")) {
                    if (Float.valueOf(datapointValues.get(i)) > 4.0) {
                        sinCounter.add(RedFlags.RED_FLAG_DATAPOINT_NAMES[i]);
                    }
                } else if (datapointValuesReqs.get(i).equals("LabVIEW")) {
                    if (datapointValues.get(i).equals("LabVIEW")) {
                        sinCounter.add(RedFlags.RED_FLAG_DATAPOINT_NAMES[i]);
                    }
                }
            }
        }
        if (!sinCounter.isEmpty()) {
            Constants.redFlagsPerTeam.put(String.valueOf(teamNumber), sinCounter);
        }

        if (sinCounter.size() > 0) {
            return true;
        }
        return false;

    }
    public List<Float> getValues(Integer teamNumber, String field) {
        List<Float> dataValues = new ArrayList<>();
        //gets the datapoint values of the given team
        for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(teamNumber)) {
            Object value = Utils.getObjectField(teamInMatchData, field);

            //if integer
            if (value instanceof Integer) {
                dataValues.add(((Integer) value).floatValue());
            }
            //if boolean, return 1 if true, 0 if false
            else if (value instanceof Boolean) {
                dataValues.add((Boolean) value ? 1f : 0f);
            }
            //if null, return 0.0
            else if (value == (null)) {
                dataValues.add((float) 0.0);
            }
        }

        return dataValues;
    }
    public List<Float> getTeamInMatchDatapointValue(String team, String selectedDatapoint) {
        List<Float> values;
        //returns value of datapoint per team
        values = getValues(Integer.valueOf(team), selectedDatapoint);
        return values;
    }


    public boolean onStarredMatches(Integer team) {
        for(int i = 0; i < StarManager.starredTeams.size(); i++) {
            if(team.equals(StarManager.starredTeams.get(i))) {
                return  true;
            }
        }
        return false;
    }
    public boolean onHighlightedTeams(Integer team) {
        for (int i = 0; i < Constants.highlightedTeams.size(); i++) {
            if (team.equals(Constants.highlightedTeams.get(i))) {
                return true;
            }
        }
        return false;
    }
    public boolean onTeamPicklist(Integer team) {
        for (int i = 0; i < Constants.teamsFromPicklist; i++) {
            if (team.toString().equals(Constants.picklistMap.get(i))) {
                return true;
            }
        }
        return false;
    }
    public void updateHighlightedTeams() {
        Constants.highlightedTeams.clear();
        for (int p = 0; p < Constants.highlightedMatches.size(); p++) {
            Match match = (Match) FirebaseLists.matchesList.getFirebaseObjectByKey(Constants.highlightedMatches.get(p).toString());
            List<Integer> teamsInMatch = new ArrayList<>();
            List<Object> redTeams = Arrays.asList(Utils.getObjectField(match, "redTeams"));
            List<Object> blueTeams = Arrays.asList(Utils.getObjectField(match, "blueTeams"));
            List<Integer> tempRedAllianceTeams = (List<Integer>) (Object) redTeams.get(0);
            List<Integer> tempBlueAllianceTeams = (List<Integer>) (Object) blueTeams.get(0);
            for (int i = 0; i < tempRedAllianceTeams.size(); i++) {
                teamsInMatch.add(tempRedAllianceTeams.get(i));
                if (!Constants.highlightedTeams.contains(tempRedAllianceTeams.get(i))) {
                    Constants.highlightedTeams.add(tempRedAllianceTeams.get(i));
                }
                for (int g = 0; g < tempBlueAllianceTeams.size(); g++) {
                    teamsInMatch.add(tempBlueAllianceTeams.get(g));
                    if (!Constants.highlightedTeams.contains(tempBlueAllianceTeams.get(g))) {
                        Constants.highlightedTeams.add(tempBlueAllianceTeams.get(g));
                    }
                }
            }
        }

        saveToSharedHighlightedTeams();
    }

    public boolean onOpponentAlliance(Integer team) {
        String matchNumber = Constants.matchNumber.toString();
        Match match = (Match) FirebaseLists.matchesList.getFirebaseObjectByKey(matchNumber);
        List<Object> redTeams = Arrays.asList(Utils.getObjectField(match, "redTeams"));
        List<Object> blueTeams = Arrays.asList(Utils.getObjectField(match, "blueTeams"));
        List<Integer> tempRedAllianceTeams = (List<Integer>) (Object) redTeams.get(0);
        List<Integer> tempBlueAllianceTeams = (List<Integer>) (Object) blueTeams.get(0);
        for (int i = 0; i < tempRedAllianceTeams.size(); i++) {
            if (tempRedAllianceTeams.get(i).equals(1678)) {
                if (!tempRedAllianceTeams.contains(team)) {
                    return true;
                }
            }
        }
        for (int i = 0; i < tempBlueAllianceTeams.size(); i++) {
            if (tempBlueAllianceTeams.get(i).equals(1678)) {
                if (!tempBlueAllianceTeams.contains(team)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean onOurAllianceList(Integer team) {
            for (int i = 0; i < Constants.onOurAllianceList.size(); i++) {
                if (team.equals(Constants.onOurAllianceList.get(i))) {
                    return true;
                }
        }
        return false;
    }
    public boolean onOpponentAllianceList(Integer team) {
            for (int i = 0; i < Constants.onOpponentAllianceList.size(); i++) {
                if (team.equals(Constants.onOpponentAllianceList.get(i))) {
                    return true;
                }
            }
        return false;
    }


    public  void saveToSharedHighlightedTeams() {
        Gson gson = new Gson();
        String jsonText = gson.toJson(Constants.highlightedMatches);
        ViewerActivity.myEditor.putString("highlightedTeams", jsonText);
        ViewerActivity.myEditor.apply();
    }
    public static ArrayList<Integer> getFromSharedHighlightedTeams() {
        Gson gson = new Gson();
        String jsonText = ViewerActivity.myPref.getString("highlightedTeams", null);
        List<String> textList = Arrays.asList(gson.fromJson(jsonText, String[].class));
        ArrayList<Integer> matches = new ArrayList<>();
        for (int i = 0; i < textList.size(); i++) {
            matches.add(Integer.valueOf(textList.get(i)));
        }
        return matches;
    }

    public void updateAllianceTeams(List<Integer> tempBlueAllianceTeams, List<Integer> tempRedAllianceTeams) {
        for (int i = 0; i < tempBlueAllianceTeams.size(); i++) {
            if (onOpponentAlliance(tempBlueAllianceTeams.get(i))) {
                if (!Constants.onOpponentAllianceList.contains(tempBlueAllianceTeams.get(i))) {
                    Constants.onOpponentAllianceList.add(tempBlueAllianceTeams.get(i));
                }
            } else {
                if (!Constants.onOurAllianceList.add(tempBlueAllianceTeams.get(i))) {
                    Constants.onOurAllianceList.add(tempBlueAllianceTeams.get(i));
                }
            }
        }
        for (int i = 0; i < tempRedAllianceTeams.size(); i++) {
            if (onOpponentAlliance(tempRedAllianceTeams.get(i))) {
                if (!Constants.onOpponentAllianceList.contains(tempRedAllianceTeams.get(i))) {
                    Constants.onOpponentAllianceList.add(tempRedAllianceTeams.get(i));
                }
            } else {
                if (!Constants.onOurAllianceList.contains(tempRedAllianceTeams.get(i))) {
                    Constants.onOurAllianceList.add(tempRedAllianceTeams.get(i));
                }
            }
        }
    }


    public abstract boolean secondaryFilter (Match value);

    private class StarLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {

            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            //vibrator.vibrate(75);
            TextView matchNumberTextView = (TextView)v.findViewById(R.id.matchNumber);
            if (StarManager.isImportantMatch(Integer.parseInt(matchNumberTextView.getText().toString()))) {
                StarManager.removeImportantMatch(Integer.parseInt(matchNumberTextView.getText().toString()));
            } else {
                StarManager.addImportantMatch(Integer.parseInt(matchNumberTextView.getText().toString()));
            }
            notifyDataSetChanged();
            return true;
        }

    }
    private class HighlightTeamListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {

            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

            TextView matchNumberTextView = (TextView)v.findViewById(R.id.matchNumber);
            Constants.matchNumber = Integer.parseInt(matchNumberTextView.getText().toString());
            Match match = (Match) FirebaseLists.matchesList.getFirebaseObjectByKey(matchNumberTextView.getText().toString());
            List<Integer> teamsInMatch = new ArrayList<>();
            List<Object> redTeams = Arrays.asList(Utils.getObjectField(match, "redTeams"));
            List<Integer> tempRedAllianceTeams = (List<Integer>) (Object) redTeams.get(0);
            List<Object> blueTeams = Arrays.asList(Utils.getObjectField(match, "blueTeams"));
            List<Integer> tempBlueAllianceTeams = (List<Integer>) (Object) blueTeams.get(0);

            for (int i = 0; i < tempRedAllianceTeams.size(); i++) {
                teamsInMatch.add(tempRedAllianceTeams.get(i));
            }
            for (int i = 0; i < tempBlueAllianceTeams.size(); i++) {
                teamsInMatch.add(tempBlueAllianceTeams.get(i));
            }

            if (!Constants.highlightedMatches.contains(Integer.parseInt(matchNumberTextView.getText().toString()))) {
                Constants.highlightedMatches.add(Integer.parseInt(matchNumberTextView.getText().toString()));
                updateHighlightedTeams();
            } else {
                Constants.highlightedMatches.remove(Constants.highlightedMatches.indexOf(Integer.parseInt(matchNumberTextView.getText().toString())));
                updateHighlightedTeams();
            }

            updateAllianceTeams(tempBlueAllianceTeams, tempRedAllianceTeams);

            notifyDataSetChanged();
            return true;
        }

    }

    private class MatchClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            TextView matchNumberTextView = (TextView)v.findViewById(R.id.matchNumber);
            Integer matchNumberClicked = Integer.parseInt((matchNumberTextView.getText()).toString());

            Intent matchDetailsActivityIntent = getMatchDetailsActivityIntent();
            matchDetailsActivityIntent.putExtra("matchNumber", matchNumberClicked);
            matchDetailsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(matchDetailsActivityIntent);
        }
    }

    public abstract boolean shouldHighlightTextViewWithText(String text);

    public abstract Intent getMatchDetailsActivityIntent();


}
