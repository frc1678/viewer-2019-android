package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertools.firebase_classes.TeamInMatchData;
import com.example.evan.androidviewertools.utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.evan.androidviewertools.utils.Utils.getMatchNumbersForTeamNumber;

public class DataComparisonTrendLineGraphingActivityTIMD extends Fragment {

    String teamOne;
    String teamTwo;
    String teamThree;
    String teamFour;
    String selectedDatapoint;

    List<Integer> teamOneMatches = new ArrayList<>();
    List<Integer> teamTwoMatches = new ArrayList<>();
    List<Integer> teamThreeMatches = new ArrayList<>();
    List<Integer> teamFourMatches = new ArrayList<>();
    ArrayList<String> teamsList = new ArrayList<>();

    private final LineChart[] charts = new LineChart[4];



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.data_comparison_trend_line, container, false);
        getExtras();
        createTeamsList();
        initTeamMatches();
        initChart(rootView);

        //sets the header name
        ((DataComparisonTIMDTabbedActivity) getActivity())
                .setActionBarTitle(selectedDatapoint + " Comparison");

        return rootView;
    }

    public void getExtras() {
        //gets static data from the initial activity
        teamOne = DataComparisonTIMDTabbedActivity.teamOne;
        teamTwo = DataComparisonTIMDTabbedActivity.teamTwo;
        teamThree = DataComparisonTIMDTabbedActivity.teamThree;
        teamFour = DataComparisonTIMDTabbedActivity.teamFour;
        selectedDatapoint = DataComparisonTIMDTabbedActivity.selectedDatapoint;
    }
    public void createTeamsList() {
        //creates the teams list by adding each team to teamsList
        teamsList.add(teamOne);
        teamsList.add(teamTwo);
        teamsList.add(teamThree);
        teamsList.add(teamFour);
    }
    public void initTeamMatches() {
        //gets the matches each team has played using getMatchNumbersForTeamNumber();
        teamOneMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamOne));
        teamTwoMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamTwo));
        teamThreeMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamThree));
        teamFourMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamFour));
    }
    public void initChart(View layout) {
        //inits the chart layouts

        charts[0] = (LineChart) layout.findViewById(R.id.chart1);
        charts[1] = (LineChart) layout.findViewById(R.id.chart2);
        charts[2] = (LineChart) layout.findViewById(R.id.chart3);
        charts[3] = (LineChart) layout.findViewById(R.id.chart4);

        for (int i = 0; i < charts.length; i++) {
            //gets the data of each chart using the getData() method.
            LineData data = getData(teamsList.get(i));

            // add some transparency to the color with "& 0x90FFFFFF"
            if (data != null) {
                setupChart(charts[i], data, colors[i % colors.length]);
            }
        }
    }
    private final int[] colors = new int[] {
            //overrides background color with hardcoded white
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff")
    };
    private final int[] lineColors = new int[] {
            //sets the colors of the lines
            //blue
            Color.rgb(51,102,204),
            //red
            Color.rgb(220,57,18),
            //orange
            Color.rgb(255,153,0),
            //green
            Color.rgb(16,150,24)
    };


    private void setupChart(LineChart chart, LineData data, int color) {
        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);

        // no description text
        chart.getDescription().setEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        // disable grid background
        chart.setDrawGridBackground(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        // set custom chart offsets (automatic offset calculation is hereby disabled)
        chart.setViewPortOffsets(10, 0, 10, 0);

        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(false);

        // animate calls invalidate()...
        chart.animateX(1000);

        //make the chart beautiful
        chart.setBackgroundColor(color);

    }
    private LineData getData(String teamNumber) {

        if (teamNumber.equals(teamsList.get(0))) {
            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(lineEntryOne(), teamNumber);
            for (int i = 0; i < 4; i ++) {
                if (teamNumber.equals(teamsList.get(i))) {
                    set1.setColor(lineColors[i % lineColors.length]);
                    set1.setCircleColor(lineColors[i % lineColors.length]);
                }
            }
            //set specifics to the data sets
            set1.setLineWidth(10f);
            set1.setCircleRadius(8f);
            set1.setDrawCircleHole(false);
            set1.setHighLightColor(Color.BLACK);
            set1.setDrawValues(false);
            // create a data object with the data sets
            return new LineData(set1);
        } else if (teamNumber.equals(teamsList.get(1))) {
            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(lineEntryTwo(), teamNumber);
            for (int i = 0; i < 4; i ++) {
                if (teamNumber.equals(teamsList.get(i))) {
                    set1.setColor(lineColors[i % lineColors.length]);
                    set1.setCircleColor(lineColors[i % lineColors.length]);
                }
            }
            //set specifics to the data sets
            set1.setLineWidth(10f);
            set1.setCircleRadius(8f);
            set1.setDrawCircleHole(false);
            set1.setHighLightColor(Color.BLACK);
            set1.setDrawValues(false);
            // create a data object with the data sets
            return new LineData(set1);
        } else if (teamNumber.equals(teamsList.get(2))) {
            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(lineEntryThree(), teamNumber);
            for (int i = 0; i < 4; i ++) {
                if (teamNumber.equals(teamsList.get(i))) {
                    set1.setColor(lineColors[i % lineColors.length]);
                    set1.setCircleColor(lineColors[i % lineColors.length]);
                }
            }
            //set specifics to the data sets
            set1.setLineWidth(10f);
            set1.setCircleRadius(8f);
            set1.setDrawCircleHole(false);
            set1.setHighLightColor(Color.BLACK);
            set1.setDrawValues(false);
            // create a data object with the data sets
            return new LineData(set1);
        } else if (teamNumber.equals(teamsList.get(3))) {
            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(lineEntryFour(), teamNumber);
            for (int i = 0; i < 4; i ++) {
                if (teamNumber.equals(teamsList.get(i))) {
                    set1.setColor(lineColors[i % lineColors.length]);
                    set1.setCircleColor(lineColors[i % lineColors.length]);
                }
            }
            //set specifics to the data sets
            set1.setLineWidth(10f);
            set1.setCircleRadius(8f);
            set1.setDrawCircleHole(false);
            set1.setHighLightColor(Color.BLACK);
            set1.setDrawValues(false);
            // create a data object with the data sets
            return new LineData(set1);
        }
       return null;
    }
    public ArrayList<Entry> lineEntryOne() {
        //first data set entry
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamOne);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            //if value is null or 0.0 (empty), make the line be at 0.1 y value
            if (String.valueOf(values.get(p)).equals("0.0")) {
                lineEntries.add(new Entry(p+1, (float) 0.1));
            //else, make the line value the y value
            } else {
                lineEntries.add(new Entry(p+1, (float) values.get(p)));
            }
        }
        //reverses the line entries
        Collections.reverse(lineEntries);
        //if under 10 values, add the remaining values as 0.1 (under 10 means less than 10 matches played)
        if (lineEntries.size() < 10);
        Integer lineEntriesSize = lineEntries.size();
        Integer counter = 10 - lineEntriesSize;
        for (int i = 0; i < counter; i++) {
            lineEntries.add(new Entry(i+lineEntriesSize, (float) 0.1));
        }
        Collections.sort(lineEntries, new EntryXComparator());
        return lineEntries;
    }
    public ArrayList<Entry> lineEntryTwo() {
        //second data set entry
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamTwo);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            //if value is null or 0.0 (empty), make the line be at 0.1 y value
            if (String.valueOf(values.get(p)).equals("0.0")) {
                lineEntries.add(new Entry(p+1, (float) 0.1));
                //else, make the line value the y value
            } else {
                lineEntries.add(new Entry(p+1, (float) values.get(p)));
            }
        }
        //reverses the line entries
        Collections.reverse(lineEntries);
        //if under 10 values, add the remaining values as 0.1 (under 10 means less than 10 matches played)
        if (lineEntries.size() < 10);
        Integer lineEntriesSize = lineEntries.size();
        Integer counter = 10 - lineEntriesSize;
        for (int i = 0; i < counter; i++) {
            lineEntries.add(new Entry(i+lineEntriesSize, (float) 0.1));
        }
        Collections.sort(lineEntries, new EntryXComparator());
        return lineEntries;
    }
    public ArrayList<Entry> lineEntryThree() {
        //third data set entry
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamThree);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            //if value is null or 0.0 (empty), make the line be at 0.1 y value
            if (String.valueOf(values.get(p)).equals("0.0")) {
                lineEntries.add(new Entry(p + 1, (float) 0.1));
                //else, make the line value the y value
            } else {
                lineEntries.add(new Entry(p + 1, (float) values.get(p)));
            }
        }
        //reverses the line entries
        Collections.reverse(lineEntries);
        //if under 10 values, add the remaining values as 0.1 (under 10 means less than 10 matches played)
        if (lineEntries.size() < 10) ;
        Integer lineEntriesSize = lineEntries.size();
        Integer counter = 10 - lineEntriesSize;
        for (int i = 0; i < counter; i++) {
            lineEntries.add(new Entry(i + lineEntriesSize, (float) 0.1));
        }
        Collections.sort(lineEntries, new EntryXComparator());
        return lineEntries;
    }
    public ArrayList<Entry> lineEntryFour() {
        //fourth data set entry
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamFour);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            //if value is null or 0.0 (empty), make the line be at 0.1 y value
            if (String.valueOf(values.get(p)).equals("0.0")) {
                lineEntries.add(new Entry(p + 1, (float) 0.1));
                //else, make the line value the y value
            } else {
                lineEntries.add(new Entry(p + 1, (float) values.get(p)));
            }
        }
        //reverses the line entries
        Collections.reverse(lineEntries);
        //if under 10 values, add the remaining values as 0.1 (under 10 means less than 10 matches played)
        if (lineEntries.size() < 10) ;
        Integer lineEntriesSize = lineEntries.size();
        Integer counter = 10 - lineEntriesSize;
        for (int i = 0; i < counter; i++) {
            lineEntries.add(new Entry(i + lineEntriesSize, (float) 0.1));
        }
        Collections.sort(lineEntries, new EntryXComparator());
        return lineEntries;
    }

    public List<Float> getValues(Integer teamNumber, String field) {
        //creates the initial dataValues list
        List<Float> dataValues = new ArrayList<>();
        for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(teamNumber)) {
            //gets value of datapoint (field)
            Object value = Utils.getObjectField(teamInMatchData, field);
            //checks for integer
            if (value instanceof Integer) {
                dataValues.add(((Integer) value).floatValue());
            //checks for boolean. if, then true = 1f, false = 0f (binary)
            } else if (value instanceof Boolean) {
                dataValues.add((Boolean) value ? 1f : 0f);
            //checks for null. If null, value returns "0.0"
            } else if (value == (null)) {
                dataValues.add((float) 0.0);
            }
        }

        return dataValues;
    }

    public List<Float> getTeamInMatchDatapointValue(String team) {
        //returns values of selected datapoint in certain match of team
        List<Float> values;
        values = getValues(Integer.valueOf(team), "calculatedData." + selectedDatapoint);
        return values;
    }

}


