package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.content.Intent;
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
import com.example.evan.androidviewertemplates.team_in_match_details.TeamInMatchDetailsActivity;
import com.example.evan.androidviewertools.firebase_classes.TeamInMatchData;
import com.example.evan.androidviewertools.utils.Utils;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.evan.androidviewertools.utils.Utils.getMatchNumbersForTeamNumber;

public class DataComparisonTrendLineGraphingActivityTIMD extends Fragment {

    String teamOne = "null";
    String teamTwo = "null";
    String teamThree = "null";
    String teamFour = "null";
    String selectedDatapoint;
    String selectedDatapointName;
    Boolean isTIMD;

    LineData data;
    public static String teamNumber;
    public static String matchNumber;

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
        getExtras(rootView);
        createTeamsList();
        initTeamMatches();
        initChart(rootView);

        //sets the header name
        if (!isTIMD) {
            ((DataComparisonTIMDTabbedActivity) getActivity())
                    .setActionBarTitle(selectedDatapointName + " Comparison");
        } else {
            ((DataComparisonTIMDTabbedActivity) getActivity())
                    .setActionBarTitle(selectedDatapointName + " breakdown for " + teamOne);
        }

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
        //creates the teams list by adding each team to teamsList
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
        //inits the chart layouts

        if (!teamTwo.equals("null") && !teamThree.equals("null") && !teamFour.equals("null")) {
            charts[0] = (LineChart) layout.findViewById(R.id.teamOneChart);
            charts[1] = (LineChart) layout.findViewById(R.id.teamTwoChart);
            charts[2] = (LineChart) layout.findViewById(R.id.teamThreeChart);
            charts[3] = (LineChart) layout.findViewById(R.id.teamFourChart);
        } else if (!teamTwo.equals("null") && !teamThree.equals("null") && teamFour.equals("null")) {
            charts[0] = (LineChart) layout.findViewById(R.id.teamOneChart);
            charts[1] = (LineChart) layout.findViewById(R.id.teamTwoChart);
            charts[2] = (LineChart) layout.findViewById(R.id.teamThreeChart);
            charts[3] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
        } else if (!teamTwo.equals("null") && teamThree.equals("null") && teamFour.equals("null")) {
            charts[0] = (LineChart) layout.findViewById(R.id.teamOneChart);
            charts[1] = (LineChart) layout.findViewById(R.id.teamTwoChart);
            charts[2] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
            charts[3] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
        } else if (teamTwo.equals("null") && teamThree.equals("null") && teamFour.equals("null")) {
            charts[0] = (LineChart) layout.findViewById(R.id.teamOneChart);
            charts[1] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
            charts[2] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
            charts[3] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
        }
        if (isTIMD) {
            charts[0] = (LineChart) layout.findViewById(R.id.teamOneChart);
            charts[1] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
            charts[2] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
            charts[3] = (LineChart) layout.findViewById(R.id.emptyTeamChart);
        }
        for (int i = 0; i < charts.length; i++) {
            //gets the data of each chart using the getData() method.
            if (!teamsList.get(i).equals("null")) {
                data = getData(teamsList.get(i));
            }

            // add some transparency to the color with "& 0x90FFFFFF"
            if (data != null) {
                setupChart(charts[i], data, colors[i % colors.length]);
            }
        }
    }

    private final int[] colors = new int[]{
            //overrides background color with hardcoded white
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff")
    };
    private final int[] lineColors = new int[]{
            //sets the colors of the lines
            //blue
            Color.rgb(51, 102, 204),
            //red
            Color.rgb(220, 57, 18),
            //orange
            Color.rgb(255, 153, 0),
            //green
            Color.rgb(16, 150, 24)
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

        addClickListener(chart);

    }

    private LineData getData(String teamNumber) {
        // create a dataset and give it a type
        LineDataSet set = new LineDataSet(lineEntryData(teamNumber), teamNumber);
        for (int i = 0; i < 4; i++) {
            if (teamNumber.equals(teamsList.get(i))) {
                set.setColor(lineColors[i % lineColors.length]);
                set.setCircleColor(lineColors[i % lineColors.length]);
            }
        }
        addPersonalization(set);
        // create a data object with the data sets
        return new LineData(set);
    }

    public ArrayList<Entry> lineEntryData(String teamNumber) {
        //first data set entry
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Float> values = new ArrayList<>();
        List<Float> datapointValues = getTeamInMatchDatapointValue(teamNumber);
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
        Collections.sort(lineEntries, new EntryXComparator());
        return lineEntries;
    }

    public ArrayList<Entry> emptyLineData() {
        //first data set entry
        ArrayList<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            lineEntries.add(new Entry(i + 1, (float) 0));
        }

        return lineEntries;
    }

    public List<Float> getValues(Integer teamNumber, String field) {
        String datapoint = field;
        if (isTIMD) {
            if (field.contains("avg")) {
                datapoint = convertFromAvg(field);
            }
        }
        //creates the initial dataValues list
        List<Float> dataValues = new ArrayList<>();
        for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(teamNumber)) {
            //gets value of datapoint
            Object value = Utils.getObjectField(teamInMatchData, datapoint);
            //checks for integer
            if (value instanceof Integer) {
                dataValues.add(((Integer) value).floatValue());
                //checks for boolean. if, then true = 1f, false = 0f (binary)
            } else if (value instanceof Boolean) {
                dataValues.add((Boolean) value ? 1f : 0f);
                //checks for null. If null, value returns "0.0"
            } else if (value instanceof Float) {
                dataValues.add((Float) value);
                //if float
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

    public LineDataSet addPersonalization(LineDataSet set) {
        //set specifics to the data sets
        set.setLineWidth(10f);
        set.setCircleRadius(8f);
        set.setDrawCircleHole(false);
        set.setHighLightColor(Color.BLACK);
        set.setDrawValues(false);
        return set;
    }

    public void addClickListener(final LineChart chart) {
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {


            @Override
            public void onValueSelected(Entry e, Highlight h) {

                String xValue = String.valueOf(e.getX());
                Integer matchNumber = Integer.valueOf(xValue.substring(0, xValue.indexOf(".")));
                String chartString = String.valueOf(chart);
                String actualChart = String.valueOf(chartString.substring(chartString.lastIndexOf("team"), chartString.lastIndexOf("}")));
                if (actualChart.equals("teamOneChart")) {
                    if (teamOneMatches.size() >= matchNumber) {
                        trendLineClickContinuation(teamOne, String.valueOf(teamOneMatches.get(matchNumber - 1)));
                    } else {
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_STANDARD).setText(teamOne + " doesn't have a " + addNumberPrefix(matchNumber) + " match!")
                                .setDuration(Style.DURATION_VERY_SHORT).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_FLY).show();
                    }
                } else if (actualChart.equals("teamTwoChart")) {
                    if (teamTwoMatches.size() >= matchNumber) {
                        trendLineClickContinuation(teamTwo, String.valueOf(teamTwoMatches.get(matchNumber - 1)));
                    } else {
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_STANDARD).setText(teamTwo + " doesn't have a " + addNumberPrefix(matchNumber) + " match!")
                                .setDuration(Style.DURATION_VERY_SHORT).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_FLY).show();
                    }
                } else if (actualChart.equals("teamThreeChart")) {
                    if (teamThreeMatches.size() >= matchNumber) {
                        trendLineClickContinuation(teamThree, String.valueOf(teamThreeMatches.get(matchNumber - 1)));
                    } else {
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_STANDARD).setText(teamThree + " doesn't have a " + addNumberPrefix(matchNumber) + " match!")
                                .setDuration(Style.DURATION_VERY_SHORT).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_FLY).show();
                    }
                } else if (actualChart.equals("teamFourChart")) {
                    if (teamFourMatches.size() >= matchNumber) {
                        trendLineClickContinuation(teamFour, String.valueOf(teamFourMatches.get(matchNumber - 1)));
                    } else {
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_STANDARD).setText(teamFour + " doesn't have a " + addNumberPrefix(matchNumber) + " match!")
                                .setDuration(Style.DURATION_VERY_SHORT).setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED)).setAnimations(Style.ANIMATIONS_FLY).show();
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    public void trendLineClickContinuation(String teamNumber, String matchNumber) {
        Intent teamInMatchDataIntent = getTeamInMatchDetailsIntent();
        teamInMatchDataIntent.putExtra("team", Integer.valueOf(teamNumber));
        teamInMatchDataIntent.putExtra("match", Integer.valueOf(matchNumber));
        getActivity().startActivity(teamInMatchDataIntent);
    }

    public Intent getTeamInMatchDetailsIntent() {
        return new Intent(getContext(), TeamInMatchDetailsActivity.class);
    }

    public String addNumberPrefix(int i) {
        String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }

    public String convertFromAvg(String avg) {
        String avgString = "";
        String str;
        String trimmedString;
        String capString = "";
        //turns 'calculatedData.avgSomethingScored' into 'calculatedData.somethingScored'
        if (avg != null && avg.contains("calculatedData.")) {
            avgString = avg.substring(avg.lastIndexOf(".") + 1);
        }
        if (avgString != null && avgString.contains("sd")) {
            str = avgString.replaceFirst("sd", "");
            capString = str.substring(0, 1).toLowerCase() + str.substring(1);
        }
        if (avgString != null && (avgString.contains("avg") || avgString.contains("Avg"))) {
            if (avgString.contains("avg")) {
                str = avgString.replaceFirst("avg", "");
                capString = str.substring(0, 1).toLowerCase() + str.substring(1);
            } else if (avgString.contains("Avg")) {
                str = avgString.replaceFirst("Avg", "");
                capString = str.substring(0, 1).toLowerCase() + str.substring(1);
            }
        }
        trimmedString = "calculatedData." + capString;
        return trimmedString;
    }

}


