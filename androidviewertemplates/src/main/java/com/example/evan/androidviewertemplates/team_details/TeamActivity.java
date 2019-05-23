package com.example.evan.androidviewertemplates.team_details;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.preference.PreferenceActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applidium.headerlistview.HeaderListView;
import com.example.evan.androidviewertemplates.*;
import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.drawer_fragments.PicklistCell;
import com.example.evan.androidviewertemplates.team_ranking.TeamRankingsActivity;
import com.example.evan.androidviewertools.*;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.services.RedFlags;
import com.example.evan.androidviewertools.services.StarManager;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Katherine on 3/27/2018.
 */

public class TeamActivity extends com.example.evan.androidviewertools.ViewerActivity {


    @Override
    public Intent getMainActivityIntent() {
        return new Intent(this, MainActivity.class);
    }

    Integer teamNumber;
    BroadcastReceiver teamUpdatedReceiver;
    BroadcastReceiver starReceiver;
    View teamDetailsHeaderView;
    LinearLayout headerPhotoLinearLayout;
    HeaderListView teamDetailsHeaderListView;


    @Override
    public void onCreate() {
        setContentView(com.example.evan.androidviewertemplates.R.layout.activity_section_listview);
        setActionBarColor();
        teamNumber = getIntent().getIntExtra("teamNumber", 1678);


        teamDetailsHeaderListView = (HeaderListView) findViewById(R.id.teamDetailsHeaderListView);

        teamDetailsHeaderListView.setAdapter(new TeamDetailsSectionAdapter(this, teamNumber));
        teamDetailsHeaderView = getLayoutInflater().inflate(R.layout.team_details_header, null);
        teamDetailsHeaderListView.getListView().addHeaderView(teamDetailsHeaderView, null, false);
        headerPhotoLinearLayout = (LinearLayout) teamDetailsHeaderView.findViewById(R.id.teamDetailsTeamPhotoLinearLayout);

        teamUpdatedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                reload();
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(teamUpdatedReceiver, new IntentFilter(Constants.TEAMS_UPDATED_ACTION));

        starReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                reload();
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(starReceiver, new IntentFilter(Constants.STARS_MODIFIED_ACTION));
    }

    public void setActionBarColor() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#65C423"));
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(colorDrawable);
        }
    }

    private class StarLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(75);
            TextView teamNumberTextView = (TextView) v;
            Integer teamNumber = Integer.parseInt(teamNumberTextView.getText().toString());
            if (StarManager.isStarredTeam(teamNumber)) {
                StarManager.removeStarredTeam(teamNumber);
            } else {
                StarManager.addStarredTeam(teamNumber);
            }
            reload();
            return true;
        }
    }

    public void reload() {
        HeaderListView teamDetailsHeaderListView = (HeaderListView) findViewById(R.id.teamDetailsHeaderListView);
        View teamDetailsHeaderView = teamDetailsHeaderListView.getChildAt(0);
        if (StarManager.isStarredTeam(teamNumber)) {
            teamDetailsHeaderView.setBackgroundColor(Constants.STAR_COLOR);
        } else {
            teamDetailsHeaderView.setBackgroundColor(Color.TRANSPARENT);
        }

        final Team team = FirebaseLists.teamsList.getFirebaseObjectByKey(teamNumber.toString());

        TextView teamDetailsTeamNumberTextView = (TextView) teamDetailsHeaderView.findViewById(R.id.teamDetailsTeamNumberTextView);

        teamDetailsTeamNumberTextView.setText(String.valueOf(teamNumber));

        teamDetailsTeamNumberTextView.setOnLongClickListener(new StarLongClickListener());

        TextView teamDetailsTeamNameTextView = (TextView) teamDetailsHeaderView.findViewById(R.id.teamDetailsTeamNameTextView);
        teamDetailsTeamNameTextView.setText(Utils.getDisplayValueForField(team, "name"));

        TextView teamDetailsSeedingTextView = (TextView) teamDetailsHeaderListView.findViewById(R.id.teamDetailsSeeding);
        teamDetailsSeedingTextView.setText((Utils.fieldIsNotNull(team, "actualSeed")) ? Utils.roundDataPoint(Utils.getObjectField(team, "actualSeed"), 2, "???") : "???");

        TextView teamDetailsPredictedSeedingTextView = (TextView) teamDetailsHeaderListView.findViewById(R.id.teamDetailsPredictedSeeding);
        teamDetailsPredictedSeedingTextView.setText((Utils.fieldIsNotNull(team, "calculatedData.predictedSeed")) ? Utils.roundDataPoint(Utils.getObjectField(team, "calculatedData.predictedSeed"), 2, "???") : "???");

        Button redFlagButton = (Button) teamDetailsHeaderListView.findViewById(R.id.redFlagButton);
        if (Constants.redFlagsPerTeam.get(String.valueOf(teamNumber)) != null) {
            if (Constants.redFlagsPerTeam.get(String.valueOf(teamNumber)).size() > 0) {
                redFlagButton.setVisibility(View.VISIBLE);
                setTextRedFlags(redFlagButton);
                redFlagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(TeamActivity.this);
                        dialog.setContentView(R.layout.red_flags_dialog);
                        TextView red_flag_title = (TextView) dialog.findViewById(R.id.red_flags_title);
                        ListView red_flag_listview = (ListView) dialog.findViewById(R.id.red_flags_lv);
                        red_flag_title.setText(String.valueOf(teamNumber) + "'s Red Flags");
                        RedFlagsAdapter adapter = new RedFlagsAdapter(TeamActivity.this, Constants.redFlagsPerTeam, String.valueOf(teamNumber));
                        red_flag_listview.setAdapter(adapter);
                        dialog.show();
                    }
                });
            }
        }
    }

    public void setTextRedFlags(Button redFlagsButton) {
        String text = "";
        for (int i = 0; i < Constants.redFlagsPerTeam.get(String.valueOf(teamNumber)).size(); i++) {
            text = text + "!";
        }
        redFlagsButton.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(teamUpdatedReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(starReceiver);
    }
}

class RedFlagsAdapter extends BaseAdapter {
    private Context context;
    private Map<String, ArrayList<String>> red_flags;
    private String teamNumber;

    public RedFlagsAdapter(Context context, Map<String, ArrayList<String>> red_flags, String teamNumber) {

        super();
        this.context = context;
        this.red_flags = red_flags;
        this.teamNumber = teamNumber;


    }


    @Override
    public int getCount() {
        return red_flags.get(teamNumber).size();
    }


    @Override
    public Object getItem(int position) {
        return getItem(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.red_flags_cell, parent, false);
        }
        String currentRedFlag = String.valueOf(red_flags.get(teamNumber).get(position));

        //initializing the xml elements
        TextView redFlagDescription = (TextView)
                convertView.findViewById(R.id.redFlagDescription);
        TextView redFlagName = (TextView)
                convertView.findViewById(R.id.redFlagName);
        TextView redFlagValue = (TextView)
                convertView.findViewById(R.id.redFlagValue);

        redFlagDescription.setText(RedFlags.RED_FLAG_DATAPOINTS.get(currentRedFlag));
        redFlagName.setText(currentRedFlag);
        redFlagValue.setText(String.valueOf(getDataValue(teamNumber, currentRedFlag)));


        return convertView;
    }

    public String getDataValue(String teamNumber, String field) {
        Team team = FirebaseLists.teamsList.getFirebaseObjectByKey(teamNumber);
        String datapointValue = (Utils.fieldIsNotNull(team, field)
                ? Utils.roundDataPoint(Utils.getObjectField(team, field),
                2, "?") : "?");
        return datapointValue;
    }

}
