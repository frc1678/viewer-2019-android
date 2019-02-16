package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.firebase_classes.TeamInMatchData;
import com.example.evan.androidviewertools.utils.ObjectFieldComparator;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

import static com.example.evan.androidviewertools.utils.Utils.getMatchNumbersForTeamNumber;
import static com.example.evan.androidviewertools.utils.Utils.getTeamInMatchDatasForTeamNumber;


public class DataComparisonGraphingActivityTIMD extends DemoBase {

    private BarChart chart;

    String teamOne;
    String teamTwo;
    String teamThree;
    String teamFour;
    String selectedDatapoint;

    List<Integer> teamOneMatches = new ArrayList<>();
    List<Integer> teamTwoMatches = new ArrayList<>();
    List<Integer> teamThreeMatches = new ArrayList<>();
    List<Integer> teamFourMatches = new ArrayList<>();

    ArrayList<BarEntry> barChartDataList = new ArrayList<>();
    public static List<String> barChartLabels = new ArrayList<>();
    BarDataSet set;
    ArrayList<String> teamsList = new ArrayList<>();
    public static ArrayList<Float> mAllDatapointValues = new ArrayList<>();

    Button condensedGraphingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.data_comparison_graphing_timd);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //forces portrait mode for graphing
        //inits button xml with according xml element
        condensedGraphingButton = (Button) findViewById(R.id.condensedGraphingButton);
        getExtras();
        createTeamsList();
        createButtonListener();
        initTeamMatches();
        initChart();
        initBarLabels();
        initBarChart();
        //sets title to selectedDatapoint followed by the hardcoded in "Comparison Graph"
        setTitle(selectedDatapoint + " Comparison Graph");

    }

    public void createButtonListener() {
        //on button click, start intent
        condensedGraphingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateGraphingIntent();
            }
        });
    }



    public void initiateGraphingIntent() {
        //creates intent to activity "DataComparisonHorizontalGraphingActivityTIMD".
        //starts the horizontal graphing activity
        Intent GraphingActivity = new Intent(DataComparisonGraphingActivityTIMD.this, DataComparisonHorizontalGraphingActivityTIMD.class);
        GraphingActivity.putExtra("teamOne", teamOne);
        GraphingActivity.putExtra("teamTwo", teamTwo);
        GraphingActivity.putExtra("teamThree", teamThree);
        GraphingActivity.putExtra("teamFour", teamFour);
        GraphingActivity.putExtra("selectedDatapoint",selectedDatapoint);
        //adds a slick animation
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(DataComparisonGraphingActivityTIMD.this, R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(GraphingActivity, options.toBundle());

    }
    public void initTeamMatches() {
        //creates the list of each team's matches
        teamOneMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamOne));
        teamTwoMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamTwo));
        teamThreeMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamThree));
        teamFourMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamFour));

    }

    public void createTeamsList() {
        //creates the list of all the selected teams
        teamsList.add(teamOne);
        teamsList.add(teamTwo);
        teamsList.add(teamThree);
        teamsList.add(teamFour);
    }

    public void initChart() {

        //inits xml with according xml element
        ListView lv = (ListView) findViewById(R.id.listView1);
        ArrayList<BarData> list = new ArrayList<>();

        //creates 12 different list cells
        for (int i = 0; i < 12; i++) {
            list.add(generateData(i + 1));
        }

        //inits adapter for list view using the list of data
        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);
    }

    public void getExtras() {
        //gets data from previous activity
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

    public void initBarChart() {
        //creates DataSet using the data list
        set = new BarDataSet(barChartDataList, "Viewer");

        //sets the colors for the bar
        List<GradientColor> gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.Rausch), ContextCompat.getColor(this, R.color.Rausch)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        set.setGradientColors(gradientColors);


    }

    public void initBarLabels() {
        //creates the label bar
        barChartLabels.add(teamOne);
        barChartLabels.add(teamTwo);
        barChartLabels.add(teamThree);
        barChartLabels.add(teamFour);

    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "Viewer");
    }

    public List<Float> getValues(Integer teamNumber, String field) {
        //gets the value of the team's datapoint
        List<Float> dataValues = new ArrayList<>();
        for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(teamNumber)) {
            Object value = Utils.getObjectField(teamInMatchData, field);
            //if integer, return value
            if (value instanceof Integer) {
                dataValues.add(((Integer) value).floatValue());
            }
            //if boolean, return 1 for true and 0 for false
            else if (value instanceof Boolean) {
                dataValues.add((Boolean) value ? 1f : 0f);
            }
            //if null, return value "0.0"
            else if (value == (null)) {
                dataValues.add((float) 0.0);
            }
        }

        return dataValues;
    }

    public Float getTeamInMatchDatapointValue(String team, Integer match) {
        //gets the datapoint value for a team in a match
        List<Float> values;
        List<TeamInMatchData> teamMatches = getTeamInMatchDatasForTeamNumber(Integer.parseInt(team));
        if (teamMatches.isEmpty()) {
            //if teamMatches is empty, return 0.0
            return (float) 0.0;
        } else {
            //if not, values is then equal to the getValues(team, datapoint)
            values = getValues(Integer.valueOf(team), "calculatedData." + selectedDatapoint);
            if (values.isEmpty()) {
                //if values is empty (datapoint is null), return 0.0
                return (float) 0.0;
            } else {
                //else, if teamMatches' size is less than or equal to match - 1, return 0.0
                if (teamMatches.size() <= match-1) {
                    return (float) 0.0;
                } else {
                    //else, return values.get(match-1)
                    return values.get(match - 1);
                }
            }
        }
    }

    private BarData generateData(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        //inits all datapoints for each possible match
        ArrayList<Float> matchOneNumbers = new ArrayList<>();
        ArrayList<Float> matchTwoNumbers = new ArrayList<>();
        ArrayList<Float> matchThreeNumbers = new ArrayList<>();
        ArrayList<Float> matchFourNumbers = new ArrayList<>();
        ArrayList<Float> matchFiveNumbers = new ArrayList<>();
        ArrayList<Float> matchSixNumbers = new ArrayList<>();
        ArrayList<Float> matchSevenNumbers = new ArrayList<>();
        ArrayList<Float> matchEightNumbers = new ArrayList<>();
        ArrayList<Float> matchNineNumbers = new ArrayList<>();
        ArrayList<Float> matchTenNumbers = new ArrayList<>();
        ArrayList<Float> matchElevenNumbers = new ArrayList<>();
        ArrayList<Float> matchTwelveNumbers = new ArrayList<>();
        ArrayList<Float> allDatapointValues = new ArrayList<>();


        if (cnt == 1) {
            //if cell is 1, get data for cell 1 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchOneNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match one
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchOneNumbers.get(p)));
            }
        } else if (cnt == 2) {
            //if cell is 2, get data for cell 2 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchTwoNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match two
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchTwoNumbers.get(p)));
            }
        } else if (cnt == 3) {
            //if cell is 3, get data for cell 3 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchThreeNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match three
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchThreeNumbers.get(p)));
            }
        } else if (cnt == 4) {
            //if cell is 4, get data for cell 4 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchFourNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match four
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchFourNumbers.get(p)));
            }
        } else if (cnt == 5) {
            //if cell is 5, get data for cell 5 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchFiveNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match five
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchFiveNumbers.get(p)));
            }
        } else if (cnt == 6) {
            //if cell is 6, get data for cell 6 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchSixNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match six
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchSixNumbers.get(p)));
            }
        } else if (cnt == 7) {
            //if cell is 7, get data for cell 7 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchSevenNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match seven
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchSevenNumbers.get(p)));
            }
        } else if (cnt == 8) {
            //if cell is 8, get data for cell 8 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchEightNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match eight
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchEightNumbers.get(p)));
            }
        } else if (cnt == 9) {
            //if cell is 9, get data for cell 9 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchNineNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match nine
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchNineNumbers.get(p)));
            }
        } else if (cnt == 10) {
            //if cell is 10, get data for cell 10 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchTenNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match ten
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchTenNumbers.get(p)));
            }
        } else if (cnt == 11) {
            //if cell is 11, get data for cell 11 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchElevenNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match eleven
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchElevenNumbers.get(p)));
            }
        } else if (cnt == 12) {
            //if cell is 12, get data for cell 12 for each team
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchTwelveNumbers.add(datapointValue);
                allDatapointValues.add(datapointValue);
            }
            //adds all the data to entries for match twelve
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchTwelveNumbers.get(p)));
            }
        }

        //creates BarDataSet using values of entries
        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        //makes colors be vordiplom colors
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        //creates BarDataSet sets and adds dataset to sets
        ArrayList<IBarDataSet> sets = new ArrayList<>();
        sets.add(d);

        //makes BarData cd using sets' values
        BarData cd = new BarData(sets);
        //adds all datapoint values to mAllDatapointValues
        mAllDatapointValues.addAll(allDatapointValues);
        //sets width to the bars
        cd.setBarWidth(0.9f);
        return cd;
    }
}

//in file adapter
class ChartDataAdapter extends ArrayAdapter<BarData> {

    //uses the list of bar data
    ChartDataAdapter(Context context, List<BarData> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        BarData data = getItem(position);

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_barchart, null);
            holder.chart = (BarChart) convertView.findViewById(R.id.chart);
            holder.matchNumber = (TextView) convertView.findViewById(R.id.matchNumber);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //if data isn't null, set the text color to black (from white)
        if (data != null) {
            data.setValueTextColor(Color.BLACK);
        }


        //turns off description for chart
        holder.chart.getDescription().setEnabled(false);
        //turns off background grid
        holder.chart.setDrawGridBackground(false);
        //turns off legend
        holder.chart.getLegend().setEnabled(false);

        //inits xAxis
        XAxis xAxis = holder.chart.getXAxis();
        //forces xAxis to be at bottom
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //turns off grid lines
        xAxis.setDrawGridLines(false);
        //allows 4 labels for 4 teams
        xAxis.setLabelCount(4);
        //adds labels using barChartLabels
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return DataComparisonGraphingActivityTIMD.barChartLabels.get((int)value);
            }
        });


        //inits the left axis
        YAxis leftAxis = holder.chart.getAxisLeft();
        //personalizes the left axis
        leftAxis.setLabelCount(0, false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setDrawLabels(false);
        // no axis labels
        leftAxis.setDrawAxisLine(false);
        // no axis line
        leftAxis.setDrawGridLines(false);
        // no grid lines
        leftAxis.setDrawZeroLine(false);
        // draw a zero line

        //inits the right axis
        YAxis rightAxis = holder.chart.getAxisRight();
        //personalizes the right axis
        rightAxis.setLabelCount(0, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setDrawLabels(false);
        // no axis labels
        rightAxis.setDrawAxisLine(false);
        // no axis line
        rightAxis.setDrawGridLines(false);
        // no grid lines
        rightAxis.setDrawZeroLine(false);
        // draw a zero line

        YAxis yAxis = holder.chart.getAxisLeft();

        //makes counter be the largest value out of all datapoints (used to set the max size of the bars to set the scale)
        Float counter = (float) 0.0;
        for (int i = 0; i < DataComparisonGraphingActivityTIMD.mAllDatapointValues.size(); i++) {
            if (DataComparisonGraphingActivityTIMD.mAllDatapointValues.get(i)>counter) {
                counter = DataComparisonGraphingActivityTIMD.mAllDatapointValues.get(i);
            }
        }
        //sets maximum y value using the largest datapoint value
        yAxis.setAxisMaximum(counter);

        //turns grid lines off for left axis
        holder.chart.getAxisLeft().setDrawGridLines(false);
        //turns grid lines off
        holder.chart.getXAxis().setDrawGridLines(false);
        //turns on granularity
        holder.chart.getXAxis().setGranularityEnabled(true);
        //sets the granularity level to 1
        holder.chart.getXAxis().setGranularity(1);
        //turns off double tap to zoom effect
        holder.chart.setDoubleTapToZoomEnabled(false);

        // set data
        holder.chart.setData(data);
        holder.chart.setFitBars(false);

        // do not forget to refresh the chart
        //adds a pretty epic animation
        holder.chart.animateY(700);

        //adds the match number to the position + 1
        //(1,2,3) instead of (0,1,2)
        holder.matchNumber.setText(String.valueOf(position+1));

        return convertView;
    }

    private class ViewHolder {

        BarChart chart;
        TextView matchNumber;
    }
}


