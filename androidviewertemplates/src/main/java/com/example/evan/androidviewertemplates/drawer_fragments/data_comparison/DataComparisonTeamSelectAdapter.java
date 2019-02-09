package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DataComparisonTeamSelectAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mTeamsList;

        public DataComparisonTeamSelectAdapter(Context context, ArrayList<String> teamsList) {
            mContext = context;
            mTeamsList = teamsList;
        }
    @Override
    public int getCount() {
        return mTeamsList.size();
        //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return mTeamsList.get(position);
        //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.data_comparison_team_select_cell, parent, false);
        }

        //makes currentTeam be the team value of the cell
        String currentTeam = (String) getItem(position);

        //inits xml per according xml element
        TextView teamNumber = (TextView)
                convertView.findViewById(R.id.teamNumberTextView);
        TextView teamName = (TextView)
                convertView.findViewById(R.id.teamName);
        TextView teamPosition = (TextView)
                convertView.findViewById(R.id.rankPosition);

        //sets text of the teamNumber to the current team
        teamNumber.setText(currentTeam);
        //sets text of the teamName to the literal name of the team and the rank of the team
        teamName.setText(generateTeamNameAndSeed(currentTeam));
        //sets text of team position to be the cell value
        teamPosition.setText(String.valueOf(position + 1));

        //if there's one team in the selectedTeamsList
        if (String.valueOf(DataComparisonFragment.selectedTeamsList.size()).equals("1")) {
            //if the cell team is equal to the selected team
            if (DataComparisonFragment.selectedTeam.equals(currentTeam)) {
                //turn cell color to red
                convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.CalmRed));
            } else {
                //else, turn it to white
                convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.White));
            }
        }

        //per each team, if it's the 2,3,4 selected, turn green
        for (int i = 0; i < DataComparisonFragment.comparedAgainstTeamsList.size(); i++) {
            if (DataComparisonFragment.comparedAgainstTeamsList.get(i).equals(currentTeam)) {
                convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.MediumSpringGreen));
            }
        }

        return convertView;
    }

    public String generateTeamNameAndSeed(String teamNumber) {
        Team team = FirebaseLists.teamsList.getFirebaseObjectByKey(teamNumber);
        //get value of actualSeed,
        //if null, return "???"
        String teamRank = (Utils.fieldIsNotNull(team, "calculatedData.actualSeed")
                ? Utils.roundDataPoint(Utils.getObjectField(team, "calculatedData.actualSeed"),
                2, "???") : "???");
        String teamName = (Utils.fieldIsNotNull(team, "name")
                ? Utils.roundDataPoint(Utils.getObjectField(team, "name"),
                2, "???") : "???");
        //substrings.
        String finalString = teamName + " | Rank: " + teamRank;
        return finalString;
    }

    }
