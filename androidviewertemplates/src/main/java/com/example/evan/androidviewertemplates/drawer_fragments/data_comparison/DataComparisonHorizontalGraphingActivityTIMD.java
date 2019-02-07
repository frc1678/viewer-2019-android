package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertools.firebase_classes.TeamInMatchData;
import com.example.evan.androidviewertools.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.example.evan.androidviewertools.utils.Utils.getMatchNumbersForTeamNumber;
import static com.example.evan.androidviewertools.utils.Utils.getTeamInMatchDatasForTeamNumber;

public class DataComparisonHorizontalGraphingActivityTIMD extends AppCompatActivity {

    String teamOne;
    String teamTwo;
    String teamThree;
    String teamFour;
    String selectedDatapoint;

    HorizontalBarChart barChart;

    List<Integer> teamOneMatches = new ArrayList<>();
    List<Integer> teamTwoMatches = new ArrayList<>();
    List<Integer> teamThreeMatches = new ArrayList<>();
    List<Integer> teamFourMatches = new ArrayList<>();

    ArrayList<String> teamsList = new ArrayList<>();
    public static ArrayList<Float> mAllDatapointValues = new ArrayList<>();





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_comparison_condensed_graphing);
        getExtras();
        createTeamsList();
        initTeamMatches();
        initChart();
        //initBarLabels();
        //initBarChart();
        setTitle(selectedDatapoint + " Condensed Graph");
    }

    public void getExtras() {
        Intent previous = getIntent();
        Bundle bundle = previous.getExtras();
        if (bundle != null) {
            teamOne = getIntent().getStringExtra("teamOne");
            teamTwo = getIntent().getStringExtra("teamTwo");
            teamThree = getIntent().getStringExtra("teamThree");
            teamFour = getIntent().getStringExtra("teamFour");
            selectedDatapoint = getIntent().getStringExtra("selectedDatapoint");
        }
    }
    public void createTeamsList() {
        teamsList.add(teamOne);
        teamsList.add(teamTwo);
        teamsList.add(teamThree);
        teamsList.add(teamFour);
    }
    public void initTeamMatches() {
        teamOneMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamOne));
        teamTwoMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamTwo));
        teamThreeMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamThree));
        teamFourMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamFour));

    }
    public void initChart() {

        barChart = (HorizontalBarChart) findViewById(R.id.chart);


        BarDataSet barDataSet1 = new BarDataSet(barEntryOne(), teamOne);
        barDataSet1.setColor(ContextCompat.getColor(DataComparisonHorizontalGraphingActivityTIMD.this, R.color.SuperBlue));
        BarDataSet barDataSet2 = new BarDataSet(barEntryTwo(), teamTwo);
        barDataSet2.setColor(ContextCompat.getColor(DataComparisonHorizontalGraphingActivityTIMD.this, R.color.SuperRed));
        BarDataSet barDataSet3 = new BarDataSet(barEntryThree(), teamThree);
        barDataSet3.setColor(ContextCompat.getColor(DataComparisonHorizontalGraphingActivityTIMD.this, R.color.SuperOrange));
        BarDataSet barDataSet4 = new BarDataSet(barEntryFour(), teamFour);
        barDataSet4.setColor(ContextCompat.getColor(DataComparisonHorizontalGraphingActivityTIMD.this, R.color.SuperGreen));

        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4);
        barChart.setData(data);

        String[] Matches = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        List<String> tempMatches = Arrays.asList(Matches);
        Collections.reverse(tempMatches);
        Matches = (String[]) tempMatches.toArray();

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(10);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setAxisMinimum(0f);


        float groupSpace = 0.125f;
        float barSpace = 0.0f;
        data.setBarWidth(0.22f);
        data.setDrawValues(false);
        barChart.getXAxis().setAxisMinimum(0);

        barChart.groupBars(0, groupSpace, barSpace);



        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getLegend().setEnabled(true);
        Legend legend = barChart.getLegend();
        legend.setTextSize(32);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(10);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(Matches));
        xAxis.setTextSize(18);
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(0, false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setDrawLabels(true); // no axis labels
        leftAxis.setDrawAxisLine(true); // no axis line
        leftAxis.setDrawGridLines(true); // no grid lines
        leftAxis.setDrawZeroLine(false); // draw a zero line

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setLabelCount(0, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setDrawLabels(true); // no axis labels
        rightAxis.setDrawAxisLine(true); // no axis line
        rightAxis.setDrawGridLines(false); // no grid lines
        rightAxis.setDrawZeroLine(false); // draw a zero line


        barChart.invalidate();


    }

    public ArrayList<BarEntry> barEntry1() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            barEntries.add(new BarEntry(i+1, (float) Math.random()*7));
        }

        return barEntries;
    }
    public ArrayList<BarEntry> barEntry2() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            barEntries.add(new BarEntry(i+1, (float) Math.random()*7));
        }

        return barEntries;
    }
    public ArrayList<BarEntry> barEntry3() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            barEntries.add(new BarEntry(i+1, (float) Math.random()*7));
        }

        return barEntries;
    }
    public ArrayList<BarEntry> barEntry4() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            barEntries.add(new BarEntry(i+1, (float) Math.random()*7));
        }

        return barEntries;
    }
    public ArrayList<BarEntry> barEntryOne() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamOne);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            if (String.valueOf(values.get(p)).equals("0.0")) {
                barEntries.add(new BarEntry(p+1, (float) 0.1));
            } else {
                barEntries.add(new BarEntry(p+1, (float) values.get(p)));
            }
        }
        Collections.reverse(barEntries);
        if (barEntries.size() < 10);
        Integer barSize = barEntries.size();
        Integer counter = 10 - barSize;
        for (int i = 0; i < counter; i++) {
            barEntries.add(new BarEntry(i+barSize, (float) 0.1));
        }
        return barEntries;
    }
    public ArrayList<BarEntry> barEntryTwo() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamTwo);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            if (String.valueOf(values.get(p)).equals("0.0")) {
                barEntries.add(new BarEntry(p+1, (float) 0.1));
            } else {
                barEntries.add(new BarEntry(p+1, (float) values.get(p)));
            }        }
        Collections.reverse(barEntries);
        if (barEntries.size() < 10);
        Integer barSize = barEntries.size();
        Integer counter = 10 - barSize;
        for (int i = 0; i < counter; i++) {
            barEntries.add(new BarEntry(i+barSize, (float) 0.1));
        }
        return barEntries;
    }
    public ArrayList<BarEntry> barEntryThree() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamThree);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            if (String.valueOf(values.get(p)).equals("0.0")) {
                barEntries.add(new BarEntry(p+1, (float) 0.1));
            } else {
                barEntries.add(new BarEntry(p+1, (float) values.get(p)));
            }        }
        Collections.reverse(barEntries);
        if (barEntries.size() < 10);
        Integer barSize = barEntries.size();
        Integer counter = 10 - barSize;
        for (int i = 0; i < counter; i++) {
            barEntries.add(new BarEntry(i+barSize, (float) 0.1));
        }
        return barEntries;
    }
    public ArrayList<BarEntry> barEntryFour() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamFour);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            if (String.valueOf(values.get(p)).equals("0.0")) {
                barEntries.add(new BarEntry(p+1, (float) 0.1));
            } else {
                barEntries.add(new BarEntry(p+1, (float) values.get(p)));
            }        }
        Collections.reverse(barEntries);
        if (barEntries.size() < 10);
        Integer barSize = barEntries.size();
        Integer counter = 10 - barSize;
        for (int i = 0; i < counter; i++) {
            barEntries.add(new BarEntry(i+barSize, (float) 0.1));
        }
        return barEntries;
    }

    public List<Float> getValues(Integer teamNumber, String field) {
        List<Float> dataValues = new ArrayList<>();
        for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(teamNumber)) {
            Object value = Utils.getObjectField(teamInMatchData, field);

            if (value instanceof Integer) {
                dataValues.add(((Integer) value).floatValue());
            } else if (value instanceof Boolean) {
                dataValues.add((Boolean) value ? 1f : 0f);
            } else if (value == (null)) {
                dataValues.add((float) 0.0);
            }
        }

        return dataValues;
    }

    public List<Float> getTeamInMatchDatapointValue(String team) {
        List<Float> values;
        values = getValues(Integer.valueOf(team), "calculatedData." + selectedDatapoint);
        return values;
    }
    private BarDataSet generateData(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        ArrayList<Float> allDatapointValues = new ArrayList<>();


        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        sets.add(d);

        mAllDatapointValues.addAll(allDatapointValues);
        return d;
    }



}