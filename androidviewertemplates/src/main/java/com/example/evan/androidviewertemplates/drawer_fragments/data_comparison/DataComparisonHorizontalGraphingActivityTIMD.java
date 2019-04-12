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
import com.example.evan.androidviewertemplates.team_details.TeamDetailsActivity;
import com.example.evan.androidviewertemplates.utils.Util;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.firebase_classes.TeamInMatchData;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.evan.androidviewertools.utils.Utils.getMatchNumbersForTeamNumber;
import static com.example.evan.androidviewertools.utils.Utils.getTeamDatasForTeamNumber;

public class DataComparisonHorizontalGraphingActivityTIMD extends Fragment {

    String teamOne = "null";
    String teamTwo = "null";
    String teamThree = "null";
    String teamFour = "null";
    String selectedDatapoint;
    String selectedDatapointName;
    Boolean isTIMD;

    HorizontalBarChart barChart;

    BarDataSet barDataSet1;
    BarDataSet barDataSet2;
    BarDataSet barDataSet3;
    BarDataSet barDataSet4;
    BarDataSet emptyBarData;
    BarData data;

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
                .setActionBarTitle(selectedDatapointName + " Comparison");

        return rootView;
    }

    public void getExtras(View layout) {
        //gets data from previous activity
        if (!DataComparisonTIMDTabbedActivity.teamOne.equals("null")) {
            teamOne = DataComparisonTIMDTabbedActivity.teamOne;
        }
        if (!DataComparisonTIMDTabbedActivity.teamTwo.equals("null")) {
            teamTwo = DataComparisonTIMDTabbedActivity.teamTwo;
        }
        if (!DataComparisonTIMDTabbedActivity.teamThree.equals("null")) {
            teamThree = DataComparisonTIMDTabbedActivity.teamThree;
        }
        if (!DataComparisonTIMDTabbedActivity.teamFour.equals("null")) {
            teamFour = DataComparisonTIMDTabbedActivity.teamFour;
        }
        selectedDatapoint = DataComparisonTIMDTabbedActivity.selectedDatapoint;
        selectedDatapointName = DataComparisonTIMDTabbedActivity.selectedDatapointName;
        isTIMD = DataComparisonTIMDTabbedActivity.isTIMD;
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
        if (!teamOne.equals("null")) {
            teamOneMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamOne));
        }
        if (!teamTwo.equals("null")) {
            teamTwoMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamTwo));
        }
        if (!teamThree.equals("null")) {
            teamThreeMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamThree));
        }
        if (!teamFour.equals("null")) {
            teamFourMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamFour));
        }
    }

    public void initChart(View layout) {
        //inits xml with according xml element
        barChart = (HorizontalBarChart) layout.findViewById(R.id.chart);

        //makes barDataSet1 be of barEntryOne() with the label being the first team
        if (!teamOne.equals("null")) {
            barDataSet1 = new BarDataSet(barEntryData(teamOne), teamOne);
            //sets the color of barDataSet1 to blue
            barDataSet1.setColor(ContextCompat.getColor(getActivity(), R.color.SuperBlue));

        }
        if (!teamTwo.equals("null")) {
            //makes barDataSet2 be of barEntryTwo() with the label being the second team if timd, else, make label ""
            if (!isTIMD) {
                barDataSet2 = new BarDataSet(barEntryData(teamTwo), teamTwo);
            } else if (isTIMD) {
                barDataSet2 = new BarDataSet(barEntryData(teamTwo), "");
            }
            //sets the color of barDataSet2 to red if datacomparison. else, sets color to same as previous
            if (!isTIMD) {
                barDataSet2.setColor(ContextCompat.getColor(getActivity(), R.color.SuperRed));
            } else if (isTIMD) {
                barDataSet2.setColor(ContextCompat.getColor(getActivity(), R.color.SuperBlue));
            }
        }
        if (!teamThree.equals("null")) {
            //makes barDataSet3 be of barEntryThree() with the label being the third team
            barDataSet3 = new BarDataSet(barEntryData(teamThree), teamThree);
            //sets the color of barDataSet3 to orange
            barDataSet3.setColor(ContextCompat.getColor(getActivity(), R.color.SuperOrange));
        }
        if (!teamFour.equals("null")) {
            //makes barDataSet4 be of barEntryFour() with the label being the fourth team
            barDataSet4 = new BarDataSet(barEntryData(teamFour), teamFour);
            //sets the color of barDataSet4 to green
            barDataSet4.setColor(ContextCompat.getColor(getActivity(), R.color.SuperGreen));
        }

        emptyBarData = new BarDataSet(emptyBarData(), "");

        //creates BarData using all the datasets
        if (barDataSet2 != null && barDataSet3 != null && barDataSet4 != null) {
            data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4);
            barChart.setData(data);
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
        } else if (barDataSet2 != null && barDataSet3 != null && barDataSet4 == null) {
            data = new BarData(barDataSet1, barDataSet2, barDataSet3);
            barChart.setData(data);
            //hardcoded in PERCENT sizes
            float groupSpace = 0.125f;
            float barSpace = 0.0f;
            data.setBarWidth(0.2925f);
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
        } else if (barDataSet2 != null && barDataSet3 == null && barDataSet4 == null) {
            data = new BarData(barDataSet1, barDataSet2);
            barChart.setData(data);
            //hardcoded in PERCENT sizes
            float groupSpace = 0.125f;
            float barSpace = 0.0f;
            data.setBarWidth(0.44f);
            //has the number value be displayed at end of bar IF not 0.1 (refer to ValueFormatter specifications)
            if (!isTIMD) {
                data.setDrawValues(true);
            } else {
                data.setDrawValues(false);
            }
            //change size of value next to the bar
            data.setValueTextSize(18);
            //sets the value formatter to class ValueFormatter
            data.setValueFormatter(new ValueFormatter());
            //sets axis minimum to 0
            barChart.getXAxis().setAxisMinimum(0);
            //sets the bars as GROUPS using the previously defined spacing
            barChart.groupBars(0, groupSpace, barSpace);
        }
        if (barDataSet2 == null && barDataSet3 == null && barDataSet4 == null) {
            data = new BarData(barDataSet1);
            barChart.setData(data);
            //hardcoded in PERCENT sizes
            data.setBarWidth(0.80f);
            //has the number value be displayed at end of bar IF not 0.1 (refer to ValueFormatter specifications)
            data.setDrawValues(true);
            //change size of value next to the bar
            data.setValueTextSize(18);
            //sets the value formatter to class ValueFormatter
            data.setValueFormatter(new ValueFormatter());
            //sets axis minimum to 0
            barChart.getXAxis().setAxisMinimum(0);
            //sets the bars as GROUPS using the previously defined spacing
        }

        data.setHighlightEnabled(false);

        //allows scrolling
        barChart.setDragEnabled(true);
        //allows 10 cells to be viewed on the screen
        barChart.setVisibleXRangeMaximum(10);
        //sets axis minimum to 0 per each axis
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setAxisMinimum(0f);
        //disables double tap to zoom
        barChart.setDoubleTapToZoomEnabled(false);
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
        //sets the circle size to be 18 if timd, else, make 0 (invisible)
        if (!isTIMD) {
            legend.setFormSize(18);
        } else if (isTIMD) {
            legend.setFormSize(0);
        }
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
        if (isTIMD) {
            List<String> tempMatches = new ArrayList<>();
            for (int i = 0; i < teamOneMatches.size(); i++) {
                tempMatches.add(String.valueOf(teamOneMatches.get(i)));
            }
            Collections.reverse(tempMatches);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(tempMatches.toArray(new String[tempMatches.size()])));
        } else {
            int topMatchCount = 0;
            if (topMatchCount < teamOneMatches.size()) topMatchCount = teamOneMatches.size();
            if (topMatchCount < teamTwoMatches.size()) topMatchCount = teamTwoMatches.size();
            if (topMatchCount < teamThreeMatches.size()) topMatchCount = teamThreeMatches.size();
            if (topMatchCount < teamFourMatches.size()) topMatchCount = teamFourMatches.size();
            List<String> tempMatches = new ArrayList<>();
            for (int i = 0; i < topMatchCount; i++) {
                tempMatches.add(String.valueOf(i + 1));
            }
            Collections.reverse(tempMatches);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(tempMatches.toArray(new String[tempMatches.size()])));
        }
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
        // yes axis line
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

        barChart.moveViewTo(0, 13, YAxis.AxisDependency.LEFT);
        barChart.invalidate();

    }

    //calculates and returns the values for the timd team data
    public ArrayList<BarEntry> barEntryData(String team) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        //gets the datapointvalues of the team
        List<Float> datapointValues = getTeamInMatchDatapointValues(team);
        if (datapointValues.isEmpty()) { barEntries.add(new BarEntry(1, (float) 0.00)); return barEntries; }
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            //if the value is 0.0, return 0.1 to be shown on graph
            if (String.valueOf(values.get(p)).equals("5000.0")) {
                barEntries.add(new BarEntry(p + 1, (float) 0.04));
            } else if (String.valueOf(values.get(p)).equals("0.0")) {
                barEntries.add(new BarEntry(p + 1, (float) 0.2));
            } else if (String.valueOf(values.get(p)).equals("10000.0")) {
                barEntries.add(new BarEntry(p + 1, (float) 0.0));
            } else {
                //else, add the value to barEntries
                barEntries.add(new BarEntry(p + 1, (float) values.get(p)));
            }
        }
        //creates a map to check how many times a null or zero value appears in datavalues
        Map<Float, Integer> hm = new HashMap<Float, Integer>();

        for (Float i : datapointValues) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        int zeroCount = 0;
        // displaying the occurrence of elements in the list
        for (Map.Entry<Float, Integer> val : hm.entrySet()) {
            if (val.getKey() == 0.0 || val.getKey() == 5000.0 || val.getKey() == 10000.0) zeroCount += val.getValue();
        }
        //if the amount of null values or 0.0 values is the same amount as the number of values in the team ...
        //AKA: if all of the values are 0
        //then fill the data with only 0 values (0.04 so it displays something) instead of some 0.04 and some 0.2 values messing up the scale
        if (zeroCount == datapointValues.size()) {
	        barEntries.clear();
	        for (int i = 0; i < datapointValues.size(); i++) barEntries.add(new BarEntry(i + 1, (float) 0.04));
        }

        //reverses the entries
        Collections.reverse(barEntries);

        return barEntries;
    }

    public ArrayList<BarEntry> emptyBarData() {
        //first data set entry
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            barEntries.add(new BarEntry(i + 1, (float) 0));
        }

        return barEntries;
    }

    public List<Float> getValues(Integer teamNumber, String field) {
        String datapoint = field;
        List<Float> dataValues = new ArrayList<>();
        //gets the datapoint values of the given team

        if (field.equals("calculatedData.habLineAttemptsL1")) {
            for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(teamNumber)) {
            	if ((int) Utils.getObjectField(teamInMatchData, "startingLevel") == 2) {
            		dataValues.add(10000.0f);
	            } else {
		            dataValues.add((Boolean) Utils.getObjectField(teamInMatchData, "crossedHabLine") ? 5f : 1f);
	            }
            }
            return dataValues;
        }
	    if (field.equals("calculatedData.habLineAttemptsL2")) {
		    for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(teamNumber)) {
			    if ((int) Utils.getObjectField(teamInMatchData, "startingLevel") == 1) {
				    dataValues.add(10000.0f);
			    } else {
				    dataValues.add((Boolean) Utils.getObjectField(teamInMatchData, "crossedHabLine") ? 5f : 1f);
			    }
		    }
		    return dataValues;
	    }

        for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(teamNumber)) {
            Object value = Utils.getObjectField(teamInMatchData, datapoint);

            //if integer
            if (value instanceof Integer) {
                dataValues.add(((Integer) value).floatValue());
            }
            //if boolean, return 1 if true, 0 if false
            else if (value instanceof Boolean) {
                dataValues.add((Boolean) value ? 1f : 0f);
            }
            //if float
            else if (value instanceof Float) {
                dataValues.add((Float) value);
            }
            //if "null", return 0.0
            else if (value == (null)) {
                dataValues.add((float) 5000.0);
            }
        }
        return dataValues;
    }

    public List<Float> getTeamInMatchDatapointValues(String team) {
        List<Float> values;
        //returns value of datapoint per team
        values = getValues(Integer.valueOf(team), "calculatedData." + selectedDatapoint);
        return values;
    }
}

