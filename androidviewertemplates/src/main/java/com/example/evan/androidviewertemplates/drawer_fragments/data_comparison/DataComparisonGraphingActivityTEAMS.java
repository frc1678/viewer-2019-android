package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.evan.androidviewertemplates.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


public class DataComparisonGraphingActivityTEAMS extends DemoBase  {

    private BarChart chart;

    String teamOne;
    String teamTwo;
    String teamThree;
    String teamFour;
    String selectedDatapoint;

    ArrayList<BarEntry> barChartDataList = new ArrayList<>();
    ArrayList<String> barChartLabels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.graphing_data_comparison_teams);
        getExtras();
        initBarLabels();
        initBarChart();
        setTitle( selectedDatapoint + " Comparison Graph");


        chart = (BarChart) findViewById(R.id.chart1);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        chart.getAxisLeft().setDrawGridLines(false);

        // add a nice and smooth animation
        chart.animateY(1500);

        chart.getLegend().setEnabled(false);
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
                barChartDataList.add(new BarEntry(4f, 0));
                barChartDataList.add(new BarEntry(8f, 1));
                barChartDataList.add(new BarEntry(6f, 2));
                barChartDataList.add(new BarEntry(12f, 3));
                barChartDataList.add(new BarEntry(18f, 4));
                barChartDataList.add(new BarEntry(9f, 5));

    }
    public void initBarLabels() {
        barChartLabels.add(teamOne);
        barChartLabels.add(teamTwo);
        barChartLabels.add(teamThree);
        barChartLabels.add(teamFour);
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
