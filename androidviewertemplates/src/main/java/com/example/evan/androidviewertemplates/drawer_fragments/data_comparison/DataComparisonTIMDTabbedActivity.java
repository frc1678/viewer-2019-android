package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.evan.androidviewertemplates.R;

public class DataComparisonTIMDTabbedActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public ViewPager viewPager;

    public static String teamOne;
    public static String teamTwo;
    public static String teamThree;
    public static String teamFour;
    public static String selectedDatapoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_comparison_tabbed_activity);
        initXml(); getExtras();
        //Sets title to hardcoded "Comparison Graphing"
        setTitle("Comparison Graphing");

        //Using viewPager, uses tabs and its adapter to switch between two fragments.
        DataComparisonTIMDTabbedAdapter adapter = new DataComparisonTIMDTabbedAdapter(getSupportFragmentManager());
        //Adding the Bar Graph to the viewPager
        adapter.AddFragment(new DataComparisonHorizontalGraphingActivityTIMD(), "Bar Graph");
        //Adding the Trend Graph to the viewPager
        adapter.AddFragment(new DataComparisonTrendLineGraphingActivityTIMD(), "Trend Graph");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void setActionBarTitle(String title) {
        //Sets the title of the header.
        getSupportActionBar().setTitle(title);
    }

    public void initXml() {
        //Inits xml with according xml elements
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
    }

    public void getExtras() {
        //Gets data from previous activity
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

}