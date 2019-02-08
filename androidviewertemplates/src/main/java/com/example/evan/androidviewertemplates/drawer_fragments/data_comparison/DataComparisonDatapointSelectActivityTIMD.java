package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import java.util.ArrayList;
import java.util.List;

public class DataComparisonDatapointSelectActivityTIMD extends AppCompatActivity {

    ArrayList<String> datapointsList = new ArrayList<>();
    ArrayList<String> descriptionList = new ArrayList<>();

    Button nextButton;
    TextView datapointTextView;
    ListView datapointListView;
    Boolean onSelectMode = false;

    String teamOne;
    String teamTwo;
    String teamThree;
    String teamFour;
    public static String selectedDatapoint;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_comparison_datapoint_select);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("TIMD Datapoint Selection");
        getDatapoints();
        getExtras();
        initXml();
        initListView();
        datapointChosenListener();
    }

    public void getDatapoints() {
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TIMD.keySet()) {
            datapointsList.add(datapoint);
        }
            for (String datapoint : SpecificConstants.DATA_COMPARISON_TIMD.keySet()) {
                String value = SpecificConstants.DATA_COMPARISON_TIMD.get(datapoint);
                descriptionList.add(value);
            }
    }
    public void getExtras() {
        Intent previous = getIntent();
        Bundle bundle = previous.getExtras();
        if (bundle != null) {
            teamOne = getIntent().getStringExtra("teamOne");
            teamTwo = getIntent().getStringExtra("teamTwo");
            teamThree = getIntent().getStringExtra("teamThree");
            teamFour = getIntent().getStringExtra("teamFour");
        }
    }

    public void initXml() {
        nextButton = (Button) findViewById(R.id.nextButton);
        datapointTextView = (TextView) findViewById(R.id.datapointTextView);
        datapointListView = (ListView) findViewById(R.id.datapointListView);

        updateButtonColor();
        disableButton();
    }

    public void updateButtonColor() {
        if (onSelectMode) {
            nextButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.LightGreen));
        } else if (!onSelectMode) {
            nextButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.LightGrey));
        }
    }
    public void disableButton() {
        nextButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.LightGrey));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NULL
            }
        });
    }
    public void activateButton() {
        nextButton.setBackgroundColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateGraphingIntent();
            }
        });

    }


    public void initListView() {
        Parcelable state = datapointListView.onSaveInstanceState();
        DataComparisonDatapointSelectAdapter dataComparisonAdapter = new DataComparisonDatapointSelectAdapter(getApplicationContext(), datapointsList, descriptionList);
        datapointListView.setAdapter(dataComparisonAdapter);
        dataComparisonAdapter.notifyDataSetChanged();
        datapointListView.onRestoreInstanceState(state);
    }
    public void datapointChosenListener() {
        datapointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String datapoint = datapointsList.get(i);
                    selectedDatapoint = datapoint;
                    datapointTextView.setText(selectedDatapoint);
                    updateButtonColor();
                    initListView();
                    activateButton();
            }
        });
    }
    public void initiateGraphingIntent() {
        Intent GraphingActivity = new Intent(DataComparisonDatapointSelectActivityTIMD.this, DataComparisonTIMDTabbedActivity.class);
        GraphingActivity.putExtra("teamOne", teamOne);
        GraphingActivity.putExtra("teamTwo", teamTwo);
        GraphingActivity.putExtra("teamThree", teamThree);
        GraphingActivity.putExtra("teamFour", teamFour);
        GraphingActivity.putExtra("selectedDatapoint",selectedDatapoint);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(DataComparisonDatapointSelectActivityTIMD.this, R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(GraphingActivity, options.toBundle());

    }
}


