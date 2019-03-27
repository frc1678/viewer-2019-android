package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.MainActivity;
import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.team_details.TeamDetailsActivity;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.firebase_classes.TeamInMatchData;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;

import java.util.ArrayList;
import java.util.List;


public class DataComparisonGraphingActivityTEAMS extends DemoBase {

    private BarChart chart;

    String teamOne;
    String teamTwo;
    String teamThree;
    String teamFour;
    String selectedDatapoint;
    String selectedDatapointName;

    ArrayList<String> teamsList = new ArrayList<>();

    TextView teamOneFourTeamTV, teamTwoFourTeamTV, teamThreeFourTeamTV, teamFourFourTeamTV;
    TextView teamOneThreeTeamTV, teamTwoThreeTeamTV, teamThreeThreeTeamTV;
    TextView teamOneTwoTeamTV, teamTwoTwoTeamTV;


    ArrayList<BarEntry> barChartDataList = new ArrayList<>();
    List<String> barChartLabels = new ArrayList<>();
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    BarDataSet set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.graphing_data_comparison_teams);
        //forces portrait mode for graphing
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getExtras();
        initChart();
        initBarLabels();
        initBarChart();
        //sets title to the datapoint name followed by the harcoded "Comparison Graph"
        setTitle(selectedDatapointName + " Comparison Graph");

    }

    public void initChart() {

        chart = (BarChart) findViewById(R.id.chart1);
        //sets the max value count to four (four teams can be shown)
        chart.setMaxVisibleValueCount(4);
        //turns off chart description
        chart.getDescription().setEnabled(false);
        //turns off ability to double tap to zoom
        chart.setDoubleTapToZoomEnabled(false);
        //enables touch
        chart.setTouchEnabled(true);

        //turns off zoom ability
        chart.setPinchZoom(false);
        //turns off bar shadows
        chart.setDrawBarShadow(false);
        //turns off grid in the background
        chart.setDrawGridBackground(false);

        //turns off drawn left side y axis scale drawing
        chart.getAxisLeft().setDrawGridLines(false);
        //set text size of left scale
        chart.getAxisLeft().setTextSize(18);
        //set text size of right scale
        chart.getAxisRight().setTextSize(18);
        //adds slick animation to the bars (1 second)
        chart.animateY(1000);
        //turns off legend because there's an xml added legend
        chart.getLegend().setEnabled(false);

        //inits xAxis
        XAxis xAxis = chart.getXAxis();
        //makes xAxis stay on the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //turns off the background grid
        xAxis.setDrawGridLines(false);
        //makes the labels centered
        xAxis.setCenterAxisLabels(true);
        //adds the labels using the barChartLabels list
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barChartLabels));


    }

    public void getExtras() {
        //gets data from previous activity
        Intent previous = getIntent();
        Bundle bundle = previous.getExtras();
        if (bundle != null) {
            if (!getIntent().getStringExtra("teamOne").equals("?")) {
                teamOne = getIntent().getStringExtra("teamOne");
                teamsList.add(teamOne);
            } else {
                teamOne = "null";
            }
            if (!getIntent().getStringExtra("teamTwo").equals("?")) {
                teamTwo = getIntent().getStringExtra("teamTwo");
                teamsList.add(teamTwo);
            } else {
                teamTwo = "null";
            }
            if (!getIntent().getStringExtra("teamThree").equals("?")) {
                teamThree = getIntent().getStringExtra("teamThree");
                teamsList.add(teamThree);
            } else {
                teamThree = "null";
            }
            if (!getIntent().getStringExtra("teamFour").equals("?")) {
                teamFour = getIntent().getStringExtra("teamFour");
                teamsList.add(teamFour);
            } else {
                teamFour = "null";
            }

            selectedDatapoint = getIntent().getStringExtra("selectedDatapoint");
            selectedDatapointName = getIntent().getStringExtra("selectedDatapointName");
        }

    }

    public void initBarChart() {
        //initializes barChartData

        if (!teamThree.equals("null") && !teamFour.equals("null")) {
            //adds data from teamOne
            barChartDataList.add(new BarEntry(1, getDatapointValue(selectedDatapoint, teamOne)));
            //adds data from teamTwo
            barChartDataList.add(new BarEntry(2, getDatapointValue(selectedDatapoint, teamTwo)));
            //adds data from teamThree
            barChartDataList.add(new BarEntry(3, getDatapointValue(selectedDatapoint, teamThree)));
            //adds data from teamFour
            barChartDataList.add(new BarEntry(4, getDatapointValue(selectedDatapoint, teamFour)));
        } else if (!teamThree.equals("null") && teamFour.equals("null")) {
            //adds data from teamOne
            barChartDataList.add(new BarEntry(1, getDatapointValue(selectedDatapoint, teamOne)));
            //adds data from teamTwo
            barChartDataList.add(new BarEntry(2, getDatapointValue(selectedDatapoint, teamTwo)));
            //adds data from teamThree
            barChartDataList.add(new BarEntry(3, getDatapointValue(selectedDatapoint, teamThree)));
        } else if (teamThree.equals("null") && teamFour.equals("null")) {
            //adds data from teamOne
            barChartDataList.add(new BarEntry(1, getDatapointValue(selectedDatapoint, teamOne)));
            //adds data from teamTwo
            barChartDataList.add(new BarEntry(2, getDatapointValue(selectedDatapoint, teamTwo)));
        }

        //adds color to each bar
        List<GradientColor> gradientColors = new ArrayList<>();
        //PRIMARY is red, all others are gray
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.Rausch), ContextCompat.getColor(this, R.color.Rausch)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));

        //creates initial BarDatSet set using the data
        set = new BarDataSet(barChartDataList, "Viewer");

        //makes the set have the colors previously defined
        set.setGradientColors(gradientColors);

        //adds set to dataSets
        dataSets.add(set);

        //creates the BarData using dataSets
        BarData data = new BarData(dataSets);

        //adds personalization to the data
        data.setValueTextSize(18f);
        data.setValueTypeface(tfLight);
        data.setBarWidth(0.9f);

        addClickListener(chart);
        chart.setData(data);
        // set the data and list of lables into chart<br />


    }

    public void initBarLabels() {
        //adds bar labels
        barChartLabels.add(teamOne);
        barChartLabels.add(teamTwo);
        barChartLabels.add(teamThree);
        barChartLabels.add(teamFour);


        //todo
        //inits the xml to its according xml elements
        teamOneFourTeamTV = (TextView) findViewById(R.id.teamOneFourTeam);
        teamTwoFourTeamTV = (TextView) findViewById(R.id.teamTwoFourTeam);
        teamThreeFourTeamTV = (TextView) findViewById(R.id.teamThreeFourTeam);
        teamFourFourTeamTV = (TextView) findViewById(R.id.teamFourFourTeam);

        teamOneThreeTeamTV = (TextView) findViewById(R.id.teamOneThreeTeam);
        teamTwoThreeTeamTV = (TextView) findViewById(R.id.teamTwoThreeTeam);
        teamThreeThreeTeamTV = (TextView) findViewById(R.id.teamThreeThreeTeam);

        teamOneTwoTeamTV = (TextView) findViewById(R.id.teamOneTwoTeam);
        teamTwoTwoTeamTV = (TextView) findViewById(R.id.teamTwoTwoTeam);

        if (!teamThree.equals("null") && !teamFour.equals("null")) {
            teamOneFourTeamTV.setText(teamOne);
            teamTwoFourTeamTV.setText(teamTwo);
            teamThreeFourTeamTV.setText(teamThree);
            teamFourFourTeamTV.setText(teamFour);
        } else if (!teamThree.equals("null") && teamFour.equals("null")) {
            teamOneThreeTeamTV.setText(teamOne);
            teamTwoThreeTeamTV.setText(teamTwo);
            teamThreeThreeTeamTV.setText(teamThree);
        } else if (teamThree.equals("null") && teamFour.equals("null")) {
            teamOneTwoTeamTV.setText(teamOne);
            teamTwoTwoTeamTV.setText(teamTwo);
        }

    }

    //gets the value of the given datapoint
    public Float getDatapointValue(String selectedDatapoint, String teamNumber) {
        Log.e("teamnumberr", String.valueOf(teamNumber));
        if (!teamNumber.equals("null")) {
            Team team = FirebaseLists.teamsList.getFirebaseObjectByKey(teamNumber);
            //if datapoint is null, return "???"
            String datapoint = (Utils.fieldIsNotNull(team, "calculatedData." + selectedDatapoint)
                    ? Utils.roundDataPoint(Utils.getObjectField(team, "calculatedData." + selectedDatapoint),
                    2, "???") : "???");
            if (!datapoint.equals("???")) {
                //if DATAPOINT is NOT "???", return the datapoint
                return Float.valueOf(datapoint);
            } else {
                //if the datapoint IS "???", return 0 as value
                return Float.valueOf(0);
            }
        }
        return Float.valueOf(0);
    }


    @Override
    protected void saveToGallery() {
        saveToGallery(chart
                , "AnotherBarActivity");
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    public void addClickListener(BarChart chart) {
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                float x = e.getX();
                String team = teamsList.get(((int) x) - 1);
                Intent teamDetailsViewIntent = getTeamDetailsActivityIntent();
                teamDetailsViewIntent.putExtra("teamNumber", Integer.valueOf(team));
                teamDetailsViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DataComparisonGraphingActivityTEAMS.this.startActivity(teamDetailsViewIntent);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    public Intent getTeamDetailsActivityIntent() {
        return new Intent(DataComparisonGraphingActivityTEAMS.this, TeamDetailsActivity.class);
    }
}

