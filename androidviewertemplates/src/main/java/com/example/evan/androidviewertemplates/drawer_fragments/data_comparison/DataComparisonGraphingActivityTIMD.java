package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.firebase_classes.TeamInMatchData;
import com.example.evan.androidviewertools.utils.ObjectFieldComparator;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
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
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    BarDataSet set;
    ArrayList<String> teamsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.data_comparison_graphing_timd);
        getExtras();
        createTeamsList();
        initTeamMatches();
        initChart();
        initBarLabels();
        initBarChart();
        setTitle(selectedDatapoint + " Comparison Graph");

    }

    public void initTeamMatches() {
        teamOneMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamOne));
        teamTwoMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamTwo));
        teamThreeMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamThree));
        teamFourMatches = getMatchNumbersForTeamNumber(Integer.valueOf(teamFour));

        for (TeamInMatchData teamInMatchData : Utils.getTeamInMatchDatasForTeamNumber(Integer.valueOf(teamOne))) {
            Object value = Utils.getObjectField(teamInMatchData, "calculatedData.predictedSoloScore");


        }
    }

    public void createTeamsList() {
        teamsList.add(teamOne);
        teamsList.add(teamTwo);
        teamsList.add(teamThree);
        teamsList.add(teamFour);
    }

    public void initChart() {

        ListView lv = (ListView) findViewById(R.id.listView1);
        ArrayList<BarData> list = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            list.add(generateData(i + 1));
            List<TeamInMatchData> test = getTeamInMatchDatasForTeamNumber(Integer.parseInt(teamOne));

        }

        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);
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

    public void initBarChart() {
        set = new BarDataSet(barChartDataList, "TEST");

        List<GradientColor> gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.Rausch), ContextCompat.getColor(this, R.color.Rausch)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        set.setGradientColors(gradientColors);


    }

    public void initBarLabels() {
        barChartLabels.add(teamOne);
        barChartLabels.add(teamTwo);
        barChartLabels.add(teamThree);
        barChartLabels.add(teamFour);

        /*teamOneTV = (TextView) findViewById(R.id.teamOne);
        teamTwoTV = (TextView) findViewById(R.id.teamTwo);
        teamThreeTV = (TextView) findViewById(R.id.teamThree);
        teamFourTV = (TextView) findViewById(R.id.teamFour);

        teamOneTV.setText(teamOne);
        teamTwoTV.setText(teamTwo);
        teamThreeTV.setText(teamThree);
        teamFourTV.setText(teamFour);*/
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "Viewer");
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

    public Float getTeamInMatchDatapointValue(String team, Integer match) {
        List<Float> values;
        List<TeamInMatchData> teamMatches = getTeamInMatchDatasForTeamNumber(Integer.parseInt(team));
        if (teamMatches.isEmpty()) {
            return (float) 0.0;
        } else {
            values = getValues(Integer.valueOf(team), "calculatedData." + selectedDatapoint);
            if (values.isEmpty()) {
                return (float) 0.0;
            } else {
                if (teamMatches.size() <= match-1) {
                    return (float) 0.0;
                } else {
                    Log.e("valess", String.valueOf(values) + "");
                    Log.e("match", String.valueOf(match - 1) + "");
                    return values.get(match - 1);
                }
            }
        }
    }

    private BarData generateData(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<>();

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


        if (cnt == 1) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchOneNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchOneNumbers.get(p)));
            }
        } else if (cnt == 2) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchTwoNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchTwoNumbers.get(p)));
            }
        } else if (cnt == 3) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchThreeNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchThreeNumbers.get(p)));
            }
        } else if (cnt == 4) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchFourNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchFourNumbers.get(p)));
            }
        } else if (cnt == 5) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchFiveNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchFiveNumbers.get(p)));
            }
        } else if (cnt == 6) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchSixNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchSixNumbers.get(p)));
            }
        } else if (cnt == 7) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchSevenNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchSevenNumbers.get(p)));
            }
        } else if (cnt == 8) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchEightNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchEightNumbers.get(p)));
            }
        } else if (cnt == 9) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchNineNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchNineNumbers.get(p)));
            }
        } else if (cnt == 10) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchTenNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchTenNumbers.get(p)));
            }
        } else if (cnt == 11) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchElevenNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchElevenNumbers.get(p)));
            }
        } else if (cnt == 12) {
            for (int i = 0; i < 4; i++) {
                Float datapointValue = getTeamInMatchDatapointValue(teamsList.get(i), cnt);
                matchTwelveNumbers.add(datapointValue);
            }
            for (int p = 0; p < 4; p++) {
                entries.add(new BarEntry(p, (float) matchTwelveNumbers.get(p)));
            }
        }


        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }
}

class ChartDataAdapter extends ArrayAdapter<BarData> {

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
            holder.chart = convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (data != null) {
            data.setValueTextColor(Color.BLACK);
        }
        holder.chart.getDescription().setEnabled(false);
        holder.chart.setDrawGridBackground(false);
        holder.chart.getLegend().setEnabled(false);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(4);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return DataComparisonGraphingActivityTIMD.barChartLabels.get((int)value);
            }
        });


        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setLabelCount(4, false);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setLabelCount(4, false);
        rightAxis.setSpaceTop(15f);

        // set data
        holder.chart.setData(data);
        holder.chart.setFitBars(true);

        // do not forget to refresh the chart
//            holder.chart.invalidate();
        holder.chart.animateY(700);

        return convertView;
    }

    private class ViewHolder {

        BarChart chart;
    }
}


