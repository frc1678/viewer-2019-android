package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;

import java.util.ArrayList;
import java.util.List;


public class DataComparisonGraphingActivityTEAMS extends DemoBase  {

    private BarChart chart;

    String teamOne;
    String teamTwo;
    String teamThree;
    String teamFour;
    String selectedDatapoint;

    TextView teamOneTV;
    TextView teamTwoTV;
    TextView teamThreeTV;
    TextView teamFourTV;

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
        setTitle( selectedDatapoint + " Comparison Graph");

    }

    public void initChart() {

        chart = (BarChart) findViewById(R.id.chart1);
        //sets the max value count to four (four teams can be shown)
        chart.setMaxVisibleValueCount(4);
        //turns off chart description
        chart.getDescription().setEnabled(false);
        //turns off ability to double tap to zoom
        chart.setDoubleTapToZoomEnabled(false);

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
            } else {
                teamOne = "null";
            }
            if (!getIntent().getStringExtra("teamTwo").equals("?")) {
                teamTwo = getIntent().getStringExtra("teamTwo");
            } else {
                teamTwo = "null";
            }
            if (!getIntent().getStringExtra("teamThree").equals("?")) {
                teamThree = getIntent().getStringExtra("teamThree");
            } else {
                teamThree = "null";
            }
            if (!getIntent().getStringExtra("teamFour").equals("?")) {
                teamFour = getIntent().getStringExtra("teamFour");
            } else {
                teamFour = "null";
            }

            selectedDatapoint = getIntent().getStringExtra("selectedDatapoint");
        }

    }

    public void initBarChart() {
        //initializes barChartData

        //adds data from teamOne
        barChartDataList.add(new BarEntry(1, getDatapointValue(selectedDatapoint, teamOne)));
        //adds data from teamTwo
        barChartDataList.add(new BarEntry(2, getDatapointValue(selectedDatapoint, teamTwo)));
        //adds data from teamThree
        barChartDataList.add(new BarEntry(3, getDatapointValue(selectedDatapoint, teamThree)));
        //adds data from teamFour
        barChartDataList.add(new BarEntry(4, getDatapointValue(selectedDatapoint, teamFour)));

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
        data.setValueTextSize(10f);
        data.setValueTypeface(tfLight);
        data.setBarWidth(0.9f);

        chart.setData(data);
        // set the data and list of lables into chart<br />


    }
    public void initBarLabels() {
        //adds bar labels
        barChartLabels.add(teamOne);
        barChartLabels.add(teamTwo);
        barChartLabels.add(teamThree);
        barChartLabels.add(teamFour);

        //inits the xml to its according xml elements
        teamOneTV = (TextView) findViewById(R.id.teamOne);
        teamTwoTV = (TextView) findViewById(R.id.teamTwo);
        teamThreeTV = (TextView) findViewById(R.id.teamThree);
        teamFourTV = (TextView) findViewById(R.id.teamFour);


        /*if (getDatapointValue(selectedDatapoint, teamOne).toString().equals(Float.valueOf(0).toString())) {
            teamOne = teamOne + "(!)";
            teamOneTV.setTextColor(ContextCompat.getColor(this, R.color.Rausch));
        }
        if (getDatapointValue(selectedDatapoint, teamTwo).toString().equals(Float.valueOf(0).toString())) {
            teamTwo = teamTwo + "(!)";
            teamTwoTV.setTextColor(ContextCompat.getColor(this, R.color.Rausch));
        }
        if (getDatapointValue(selectedDatapoint, teamThree).toString().equals(Float.valueOf(0).toString())) {
            teamThree = teamThree + "(!)";
            teamThreeTV.setTextColor(ContextCompat.getColor(this, R.color.Rausch));
        }
        if (getDatapointValue(selectedDatapoint, teamFour).toString().equals(Float.valueOf(0).toString())) {
            teamFour = teamFour + "(!)";
            teamFourTV.setTextColor(ContextCompat.getColor(this, R.color.Rausch));
        }*/ //todo CARL empty team value = red


        //sets the text of each label
        if (!teamOne.equals("null")) {
            teamOneTV.setText(teamOne);
        } else {
            teamOneTV.setText("");
        }
        if (!teamTwo.equals("null")) {
            teamTwoTV.setText(teamTwo);
        } else {
            teamTwoTV.setText("");
        }
        if (!teamThree.equals("null")) {
            teamThreeTV.setText(teamThree);
        } else {
            teamThreeTV.setText("");
        }
        if (!teamFour.equals("null")) {
            teamFourTV.setText(teamFour);
        } else {
            teamFourTV.setText("");
        }
    }

    //gets the value of the given datapoint
    public Float getDatapointValue(String selectedDatapoint, String teamNumber) {
        Log.e("teamnumberr",String.valueOf(teamNumber));
        if (!teamNumber.equals("null")) {
            Team team = FirebaseLists.teamsList.getFirebaseObjectByKey(teamNumber);
            //if datapoint is null, return "???"
            String datapoint = (Utils.fieldIsNotNull(team, "calculatedData."+selectedDatapoint)
                    ? Utils.roundDataPoint(Utils.getObjectField(team, "calculatedData."+selectedDatapoint),
                    2, "???") : "???");
            if (!datapoint.equals("???")) {
                //if DATAPOINT is NOT "???", return the datapoint
                return Float.valueOf(datapoint);
            }
            else {
                //if the datapoint IS "???", return 0 as value
                return Float.valueOf(0);
            }
        }
        return Float.valueOf(0);
    }


    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "AnotherBarActivity");
    }


}

