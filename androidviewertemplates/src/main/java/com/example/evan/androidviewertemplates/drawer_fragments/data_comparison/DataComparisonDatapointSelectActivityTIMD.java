package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;

import java.util.ArrayList;
import java.util.List;

public class DataComparisonDatapointSelectActivityTIMD extends AppCompatActivity {

    ArrayList<String> datapointsList = new ArrayList<>();
    ArrayList<String> descriptionList = new ArrayList<>();

    Button nextButton;
    TextView datapointTextView;
    ListView datapointListView;
    Boolean onSelectMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_comparison_datapoint_select);
        setTitle("TIMD Datapoint Selection");
        getDatapoints();
        Log.e("datapoint",datapointsList.toString());
        Log.e("description",descriptionList.toString());

        initXml();
        initListView();
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
    public void initListView() {
        Parcelable state = datapointListView.onSaveInstanceState();
        DataComparisonDatapointSelectAdapter dataComparisonAdapter = new DataComparisonDatapointSelectAdapter(getApplicationContext(), datapointsList, descriptionList);
        datapointListView.setAdapter(dataComparisonAdapter);
        dataComparisonAdapter.notifyDataSetChanged();
        datapointListView.onRestoreInstanceState(state);
    }

}

