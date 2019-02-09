package com.example.evan.androidviewertools.match_listing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evan.androidviewertools.ViewerActivity;
import com.example.evan.androidviewertools.firebase_classes.Team;
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

                //Only on Highlight:
            if (onHighlightedTeams(team) && !onStarredMatches(team) && !onTeamPicklist(team)) {
                //if (onOurAllianceList(team) && !onOpponentAllianceList(team)) {
                //    teamTextView.setBackgroundColor(Color.parseColor("#a4ff7a"));
                //} if (onOpponentAllianceList(team) && !onOurAllianceList(team)){
                    teamTextView.setBackgroundColor(Color.parseColor("#b8d4fc"));
                //} if (onOpponentAllianceList (team) && onOurAllianceList(team)) {
                //    teamTextView.setBackgroundColor(Color.parseColor("#7df2c5"));
                //}
                Log.e("OpponentList",Constants.onOpponentAllianceList.toString());
                Log.e("OurList",Constants.onOurAllianceList.toString());

                //Only on Starred:
            } else if (onStarredMatches(team) && !onHighlightedTeams(team) && !onTeamPicklist(team)){
                teamTextView.setBackgroundColor(Color.parseColor("#e2f442"));
                //Only on Picklist:
            } else if (!onStarredMatches(team) && !onHighlightedTeams(team) && onTeamPicklist(team)){
                teamTextView.setBackgroundColor(Color.parseColor("#f98181"));
                //On ALL:
            } else if (onStarredMatches(team) && onHighlightedTeams(team) && onTeamPicklist(team)) {
                teamTextView.setBackgroundColor(Color.parseColor("#e572e1"));
                //On Highlight && Starred
            } else if (onStarredMatches(team) && onHighlightedTeams(team) && !onTeamPicklist(team)){
                teamTextView.setBackgroundColor(Color.parseColor("#808000"));
                //Only Starred && Picklist:
            } else if (onStarredMatches(team) && !onHighlightedTeams(team) && onTeamPicklist(team)){
                teamTextView.setBackgroundColor(Color.parseColor("#f4a142"));
                //On Picklist && Highlight:
            } else if (!onStarredMatches(team) && onHighlightedTeams(team) && onTeamPicklist(team)){
                teamTextView.setBackgroundColor(Color.parseColor("#fcb8e7"));
            }else {
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
            TextView rankingPointDisplayBlueRocketRP = (TextView) rowView.findViewById(R.id.rankingPointDisplayBlueRocketRP);
            TextView rankingPointDisplayRedRocketRP = (TextView) rowView.findViewById(R.id.rankingPointDisplayRedRocketRP);
            TextView rankingPointDisplayBlueHabClimb = (TextView) rowView.findViewById(R.id.rankingPointDisplayBlueHabClimb);
            TextView rankingPointDisplayRedHabClimb = (TextView) rowView.findViewById(R.id.rankingPointDisplayRedHabClimb);

         Boolean blueDidRocketRP = Boolean.valueOf(String.valueOf(Utils.getObjectField(match, "blueDidRocketRP")));
         Boolean blueDidHabClimb = Boolean.valueOf(String.valueOf(Utils.getObjectField(match, "blueDidClimbRP")));
         Boolean redDidRocketRP = Boolean.valueOf(String.valueOf(Utils.getObjectField(match, "redDidRocketRP")));
         Boolean redDidHabClimb = Boolean.valueOf(String.valueOf(Utils.getObjectField(match, "redDidClimbRP")));

         //todo Add predicted RPs?

            if (blueDidRocketRP) {
                rankingPointDisplayBlueRocketRP.setText("●");
            }
            if (redDidRocketRP) {
                rankingPointDisplayRedRocketRP.setText("●");
            }
            if (blueDidHabClimb) {
                rankingPointDisplayBlueHabClimb.setText("●");
            }
            if (redDidHabClimb) {
                rankingPointDisplayRedHabClimb.setText("●");
            }


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
