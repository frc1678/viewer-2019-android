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

    public static ArrayList<Float> mAllDatapointValues = new ArrayList<>();

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

        ((DataComparisonTIMDTabbedActivity) getActivity())
                .setActionBarTitle(selectedDatapoint + " Comparison");

        return rootView;
    }

    public void getExtras() {
        teamOne = DataComparisonTIMDTabbedActivity.teamOne;
        teamTwo = DataComparisonTIMDTabbedActivity.teamTwo;
        teamThree = DataComparisonTIMDTabbedActivity.teamThree;
        teamFour = DataComparisonTIMDTabbedActivity.teamFour;
        selectedDatapoint = DataComparisonTIMDTabbedActivity.selectedDatapoint;
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
    public void initChart(View layout) {

        charts[0] = (LineChart) layout.findViewById(R.id.chart1);
        charts[1] = (LineChart) layout.findViewById(R.id.chart2);
        charts[2] = (LineChart) layout.findViewById(R.id.chart3);
        charts[3] = (LineChart) layout.findViewById(R.id.chart4);

        for (int i = 0; i < charts.length; i++) {

            LineData data = getData(teamsList.get(i));
            Log.e("data",String.valueOf(data));

            // add some transparency to the color with "& 0x90FFFFFF"
            if (data != null) {
                setupChart(charts[i], data, colors[i % colors.length]);
            }
        }
        Log.e("JAHSEH","JAHHSEH");
    }
    private final int[] colors = new int[] {
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff")
    };
    private final int[] lineColors = new int[] {
            Color.rgb(137, 230, 81),
            Color.rgb(240, 240, 30),
            Color.rgb(89, 199, 250),
            Color.rgb(250, 104, 104)
          /*Color.parseColor("#3366cc"),
          Color.parseColor("#dc3912"),
          Color.parseColor("#ff9900"),
          Color.parseColor("#109618"),*/
    };


    private void setupChart(LineChart chart, LineData data, int color) {

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);
        // no description text
        chart.getDescription().setEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        // chart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false);
//        chart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setBackgroundColor(color);

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

        Log.e("TESTTT",String.valueOf(chart));
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
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamOne);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            if (String.valueOf(values.get(p)).equals("0.0")) {
                lineEntries.add(new Entry(p+1, (float) 0.1));
            } else {
                lineEntries.add(new Entry(p+1, (float) values.get(p)));
            }
        }
        Collections.reverse(lineEntries);
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
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamTwo);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            if (String.valueOf(values.get(p)).equals("0.0")) {
                lineEntries.add(new Entry(p+1, (float) 0.1));
            } else {
                lineEntries.add(new Entry(p+1, (float) values.get(p)));
            }
        }
        Collections.reverse(lineEntries);
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
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamThree);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            if (String.valueOf(values.get(p)).equals("0.0")) {
                lineEntries.add(new Entry(p+1, (float) 0.1));
            } else {
                lineEntries.add(new Entry(p+1, (float) values.get(p)));
            }
        }
        Collections.reverse(lineEntries);
        if (lineEntries.size() < 10);
        Integer lineEntriesSize = lineEntries.size();
        Integer counter = 10 - lineEntriesSize;
        for (int i = 0; i < counter; i++) {
            lineEntries.add(new Entry(i+lineEntriesSize, (float) 0.1));
        }
        Collections.sort(lineEntries, new EntryXComparator());
        return lineEntries;
    }
    public ArrayList<Entry> lineEntryFour() {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamFour);
        values.addAll(datapointValues);
        for (int p = 0; p < datapointValues.size(); p++) {
            if (String.valueOf(values.get(p)).equals("0.0")) {
                lineEntries.add(new Entry(p+1, (float) 0.1));
            } else {
                lineEntries.add(new Entry(p+1, (float) values.get(p)));
            }
        }
        Collections.reverse(lineEntries);
        if (lineEntries.size() < 10);
        Integer lineEntriesSize = lineEntries.size();
        Integer counter = 10 - lineEntriesSize;
        for (int i = 0; i < counter; i++) {
            lineEntries.add(new Entry(i+lineEntriesSize, (float) 0.1));
        }
        Collections.sort(lineEntries, new EntryXComparator());
        return lineEntries;
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

}


