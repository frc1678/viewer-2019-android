package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

import static com.example.evan.androidviewertools.utils.Utils.getMatchNumbersForTeamNumber;

public class DataComparisonHorizontalGraphingActivityTIMD extends Fragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.data_comparison_condensed_graphing, container, false);
        getExtras(rootView);
        createTeamsList();
        initTeamMatches();
        initChart(rootView);

        //makes the title be the selectedDatapoint followed by the hardcoded "Comparison"
        ((DataComparisonTIMDTabbedActivity) getActivity())
                .setActionBarTitle(selectedDatapoint + " Comparison");

        return rootView;
    }

    public void getExtras(View layout) {
        //gets data from previous activity
        teamOne = DataComparisonTIMDTabbedActivity.teamOne;
        teamTwo = DataComparisonTIMDTabbedActivity.teamTwo;
        teamThree = DataComparisonTIMDTabbedActivity.teamThree;
        teamFour = DataComparisonTIMDTabbedActivity.teamFour;
        selectedDatapoint = DataComparisonTIMDTabbedActivity.selectedDatapoint;
    }

    public void createTeamsList() {
        //adds all the selected teams to teamsList
        teamsList.add(teamOne);
        teamsList.add(teamTwo);
        teamsList.add(teamThree);
        teamsList.add(teamFour);
    }

    public void initTeamMatches() {
        //retrieves all the matches of each selected team
        teamOneMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamOne));
        teamTwoMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamTwo));
        teamThreeMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamThree));
        teamFourMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamFour));

    }

    public void initChart(View layout) {
        //inits xml with according xml element
        barChart = (HorizontalBarChart) layout.findViewById(R.id.chart);

        //makes barDataSet1 be of barEntryOne() with the label being the first team
        BarDataSet barDataSet1 = new BarDataSet(barEntryData(teamOne), teamOne);
        //sets the color of barDataSet1 to blue
        barDataSet1.setColor(ContextCompat.getColor(getActivity(), R.color.SuperBlue));
        //makes barDataSet2 be of barEntryTwo() with the label being the second team
        BarDataSet barDataSet2 = new BarDataSet(barEntryData(teamTwo), teamTwo);
        //sets the color of barDataSet2 to red
        barDataSet2.setColor(ContextCompat.getColor(getActivity(), R.color.SuperRed));
        //makes barDataSet3 be of barEntryThree() with the label being the third team
        BarDataSet barDataSet3 = new BarDataSet(barEntryData(teamThree), teamThree);
        //sets the color of barDataSet3 to orange
        barDataSet3.setColor(ContextCompat.getColor(getActivity(), R.color.SuperOrange));
        //makes barDataSet4 be of barEntryFour() with the label being the fourth team
        BarDataSet barDataSet4 = new BarDataSet(barEntryData(teamFour), teamFour);
        //sets the color of barDataSet4 to green
        barDataSet4.setColor(ContextCompat.getColor(getActivity(), R.color.SuperGreen));

        //creates BarData using all the datasets
        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4);
        //makes barChart use the data
        barChart.setData(data);

        //labels list
        String[] Matches = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
        //converting to List<String> type
        List<String> tempMatches = Arrays.asList(Matches);
        //reversing the List<String> type
        Collections.reverse(tempMatches);
        //changing it back to a String[] type
        Matches = (String[]) tempMatches.toArray();
        //allows scrolling
        barChart.setDragEnabled(true);
        //allows 10 cells to be viewed on the screen
        barChart.setVisibleXRangeMaximum(10);
        //sets axis minimum to 0 per each axis
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setAxisMinimum(0f);
        //disables double tap to zoom
        barChart.setDoubleTapToZoomEnabled(false);

        //hardcoded in PERCENT sizes
        float groupSpace = 0.125f;
        float barSpace = 0.0f;
        data.setBarWidth(0.22f);
        //has the number value be displayed at end of bar IF not 0.1 (refer to ValueFormatter specifications)
        data.setDrawValues(true);
        //change size of value next to the bar
        data.setValueTextSize(18);
        //sets the value formatter to class ValueFormatter
        data.setValueFormatter(new ValueFormatter());
        //sets axis minimum to 0
        barChart.getXAxis().setAxisMinimum(0);

        //sets the bars as GROUPS using the previously defined spacing
        barChart.groupBars(0, groupSpace, barSpace);

        //makes the description become disabled
        barChart.getDescription().setEnabled(false);
        //turns off grid background
        barChart.setDrawGridBackground(false);
        //turns off drawn grid lines
        barChart.getAxisLeft().setDrawGridLines(false);
        //turns off x axis drawn grid lines
        barChart.getXAxis().setDrawGridLines(false);

        //adds a legend
        barChart.getLegend().setEnabled(true);
        Legend legend = barChart.getLegend();
        //sets legend text size to 32
        legend.setTextSize(32);
        //sets the icon of the color to be a circle
        legend.setForm(Legend.LegendForm.CIRCLE);
        //sets the circle size to be 18
        legend.setFormSize(18);
        //sets the spacing between each value be 36
        legend.setXEntrySpace(36);
        //sets the y spacing between each value to be 20
        legend.setYEntrySpace(20);
        //center horizontally in parent
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);


        //creates x axis
        XAxis xAxis = barChart.getXAxis();
        //forces x axis to stick to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //disables drawn grid lines
        xAxis.setDrawGridLines(false);
        //sets the label count to 10
        xAxis.setLabelCount(10);
        //makes the labels be set to the center of the group bar
        xAxis.setCenterAxisLabels(true);
        //adds granularity
        xAxis.setGranularityEnabled(true);
        //sets level of granularity to 1
        xAxis.setGranularity(1);

        //creates the labels be the String[] Matches
        xAxis.setValueFormatter(new IndexAxisValueFormatter(Matches));
        //makes the text size of the labels to be 18
        xAxis.setTextSize(18);
        //makes the labels centered
        xAxis.setCenterAxisLabels(true);
        //sets the x axis at the bottom of the view
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //inits the left axis
        YAxis leftAxis = barChart.getAxisLeft();
        //adds no labels to the left axis
        leftAxis.setLabelCount(0, false);
        leftAxis.setDrawLabels(true);
        // no axis labels
        leftAxis.setDrawAxisLine(true);
        // no axis line
        leftAxis.setDrawGridLines(true);
        // no grid lines
        leftAxis.setDrawZeroLine(false);
        // draw a zero line
        leftAxis.setTextSize(18);

        //inits right axis
        YAxis rightAxis = barChart.getAxisRight();
        //adds no labels to the right axis
        rightAxis.setLabelCount(0, false);
        rightAxis.setDrawLabels(true);
        // no axis labels
        rightAxis.setDrawAxisLine(true);
        // no axis line
        rightAxis.setDrawGridLines(false);
        // no grid lines
        rightAxis.setDrawZeroLine(false);
        // draw a zero line
        rightAxis.setTextSize(18);

        barChart.moveViewTo(0,13, YAxis.AxisDependency.LEFT);
        barChart.invalidate();

    }

    //calculates and returns the values for the team
    public ArrayList<BarEntry> barEntryData(String team) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        //gets the datapointvalues of the team
        List<Float> datapointValues = getTeamInMatchDatapointValue(team);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            //if the value is 0.0, return 0.1 to be shown on graph
            if (String.valueOf(values.get(p)).equals("0.0")) {
                barEntries.add(new BarEntry(p + 1, (float) 0.1));
            } else {
                //else, add the value to barEntries
                barEntries.add(new BarEntry(p + 1, (float) values.get(p)));
            }
        }

        if (barEntries.size() < 13) {
            Integer barSize = barEntries.size();
            Integer counter = 13 - barSize;
            //if the size of the entries is under 13 (under 13 matches), add a 0.1 value
            for (int i = 0; i < counter; i++) {
                barEntries.add(new BarEntry(i + barSize + 1, (float) 0.1));
            }
        }

        //reverses the entries
        Collections.reverse(barEntries);

        return barEntries;
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

    public List<Float> getTeamInMatchDatapointValue(String team) {
        List<Float> values;
        //returns value of datapoint per team
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

