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
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getExtras();
        initChart();
        initBarLabels();
        initBarChart();
        setTitle( selectedDatapoint + " Comparison Graph");

    }

    public void initChart() {

        chart = (BarChart) findViewById(R.id.chart1);
        chart.setMaxVisibleValueCount(4);
        chart.getDescription().setEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        chart.setPinchZoom(true);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);


        chart.getAxisLeft().setDrawGridLines(false);
        chart.animateY(2500);
        chart.getLegend().setEnabled(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(barChartLabels));



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
        barChartDataList.add(new BarEntry(1, getDatapointValue(selectedDatapoint, teamOne)));
        barChartDataList.add(new BarEntry(2, getDatapointValue(selectedDatapoint, teamTwo)));
        barChartDataList.add(new BarEntry(3, getDatapointValue(selectedDatapoint, teamThree)));
        barChartDataList.add(new BarEntry(4, getDatapointValue(selectedDatapoint, teamFour)));

        List<GradientColor> gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.Rausch), ContextCompat.getColor(this, R.color.Rausch)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));
        gradientColors.add(new GradientColor(ContextCompat.getColor(this, R.color.DarkGray), ContextCompat.getColor(this, R.color.DarkGray)));

        set = new BarDataSet(barChartDataList, "TEST");

        set.setGradientColors(gradientColors);


        dataSets.add(set);

        BarData data = new BarData(dataSets);

                //BarData data = new BarData(dataSets);
        //BarData data = new BarData(barChartLabels(), dataSets);


        data.setValueTextSize(10f);
        data.setValueTypeface(tfLight);
        data.setBarWidth(0.9f);

        chart.setData(data); // set the data and list of lables into chart<br />


    }
    public void initBarLabels() {
        barChartLabels.add(teamOne);
        barChartLabels.add(teamTwo);
        barChartLabels.add(teamThree);
        barChartLabels.add(teamFour);

        teamOneTV = (TextView) findViewById(R.id.teamOne);
        teamTwoTV = (TextView) findViewById(R.id.teamTwo);
        teamThreeTV = (TextView) findViewById(R.id.teamThree);
        teamFourTV = (TextView) findViewById(R.id.teamFour);
/*        if (getDatapointValue(selectedDatapoint, teamOne).toString().equals(Float.valueOf(0).toString())) {
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

        teamOneTV.setText(teamOne);
        teamTwoTV.setText(teamTwo);
        teamThreeTV.setText(teamThree);
        teamFourTV.setText(teamFour);
    }

    public Float getDatapointValue(String selectedDatapoint, String teamNumber) {
        Team team = FirebaseLists.teamsList.getFirebaseObjectByKey(teamNumber);
        String datapoint = (Utils.fieldIsNotNull(team, "calculatedData."+selectedDatapoint)
                ? Utils.roundDataPoint(Utils.getObjectField(team, "calculatedData."+selectedDatapoint),
                2, "???") : "???");
        if (!datapoint.equals("???")) {
            return Float.valueOf(datapoint);
        }
        else {
            return Float.valueOf(0);
        }
    }



    /*@Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < seekBarX.getProgress(); i++) {
            float multi = (seekBarY.getProgress() + 1);
            float val = (float) (Math.random() * multi) + multi / 3;
            values.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            chart.setData(data);
            chart.setFitBars(true);
        }

        chart.invalidate();
    }*/


    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "AnotherBarActivity");
    }


}

