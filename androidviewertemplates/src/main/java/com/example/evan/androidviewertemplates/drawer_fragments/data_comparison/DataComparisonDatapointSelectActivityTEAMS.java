package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import java.util.ArrayList;

public class DataComparisonDatapointSelectActivityTEAMS extends AppCompatActivity {

    ArrayList<String> datapointsList_SD = new ArrayList<>();
    ArrayList<String> descriptionList_SD = new ArrayList<>();
    ArrayList<String> datapointsList_LFM = new ArrayList<>();
    ArrayList<String> descriptionList_LFM = new ArrayList<>();
    ArrayList<String> datapointsList_P75 = new ArrayList<>();
    ArrayList<String> descriptionList_P75 = new ArrayList<>();
    ArrayList<String> datapointsList_NORMAL = new ArrayList<>();
    ArrayList<String> descriptionList_NORMAL = new ArrayList<>();

    String teamOne;
    String teamTwo;
    String teamThree;
    String teamFour;

    Button nextButton;
    TextView datapointTextView;
    ListView datapointListView;
    Spinner typeSelectionSpinner;
    Boolean onSelectedMode = false;
    String typeSelection;
    public static String selectedDatapoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_comparison_teams);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("TEAMS Datapoint Selection");
        selectedDatapoint = "";
        getExtras();
        getDatapoints();
        initXml();
        runSpinnerListener();
        datapointChosenListener();
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

    public void getDatapoints() {
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_NORMAL.keySet()) {
            datapointsList_NORMAL.add(datapoint);
        }
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_NORMAL.keySet()) {
            String value = SpecificConstants.DATA_COMPARISON_TEAMS_NORMAL.get(datapoint);
            descriptionList_NORMAL.add(value);
        }
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_SD.keySet()) {
            datapointsList_SD.add(datapoint);
        }
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_SD.keySet()) {
            String value = SpecificConstants.DATA_COMPARISON_TEAMS_SD.get(datapoint);
            descriptionList_SD.add(value);
        }
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_LFM.keySet()) {
            datapointsList_LFM.add(datapoint);
        }
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_LFM.keySet()) {
            String value = SpecificConstants.DATA_COMPARISON_TEAMS_LFM.get(datapoint);
            descriptionList_LFM.add(value);
        }
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_P75.keySet()) {
            datapointsList_P75.add(datapoint);
        }
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_P75.keySet()) {
            String value = SpecificConstants.DATA_COMPARISON_TEAMS_P75.get(datapoint);
            descriptionList_P75.add(value);
        }
    }

    public void initXml() {
        nextButton = (Button) findViewById(R.id.nextButton);
        datapointTextView = (TextView) findViewById(R.id.datapointTextView);
        datapointListView = (ListView) findViewById(R.id.datapointListView);
        typeSelectionSpinner = (Spinner) findViewById(R.id.typeSelectionSpinner);

        updateButtonColor();
        disableButton();
        initSpinner();
    }

    public void updateButtonColor() {
        if (onSelectedMode) {
            nextButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.LightGreen));
        } else if (!onSelectedMode) {
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

    public void initListView(String type) {
        typeSelection="";
        onSelectedMode = false;
        updateButtonColor();
        Parcelable state = datapointListView.onSaveInstanceState();
        if (type.equals("Normal")) {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_NORMAL, descriptionList_NORMAL);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection="Normal";
        }
        else if (type.equals("Last Four Matches")) {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_LFM, descriptionList_LFM);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection="Last Four Matches";
        }
        else if (type.equals("Standard Deviation")) {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_SD, descriptionList_SD);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection="Standard Deviation";
        }
        else if (type.equals("75th Percentile")) {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_P75, descriptionList_P75);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection="75th Percentile";
        }
        else {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_NORMAL, descriptionList_NORMAL);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection="Normal";
        }
        datapointListView.onRestoreInstanceState(state);
    }

    public void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SpecificConstants.CATEGORY_LIST);
        Spinner typeSelectionSpinner = (Spinner)
                findViewById(R.id.typeSelectionSpinner);
        typeSelectionSpinner.setAdapter(adapter);
    }

    public void runSpinnerListener() {
        typeSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                initListView(SpecificConstants.CATEGORY_LIST.get(position));
                SuperActivityToast.create(DataComparisonDatapointSelectActivityTEAMS.this, new Style(), Style.TYPE_STANDARD)
                        .setText("In type '" + SpecificConstants.CATEGORY_LIST.get(position) + "'")
                        .setDuration(Style.DURATION_VERY_SHORT)
                        .setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE))
                        .setAnimations(Style.ANIMATIONS_POP).show();

                disableButton();
                datapointTextView.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
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

    public void datapointChosenListener() {
        datapointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (typeSelection.equals("Normal")) {
                    initListView("Normal");
                    String datapoint = datapointsList_NORMAL.get(i);
                    selectedDatapoint = datapoint;
                    datapointTextView.setText(selectedDatapoint);
                }
                if (typeSelection.equals("Last Four Matches")) {
                    initListView("Last Four Matches");
                    String datapoint = datapointsList_LFM.get(i);
                    selectedDatapoint = datapoint;
                    datapointTextView.setText(selectedDatapoint);
                }
                if (typeSelection.equals("Standard Deviation")) {
                    initListView("Standard Deviation");
                    String datapoint = datapointsList_SD.get(i);
                    selectedDatapoint = datapoint;
                    datapointTextView.setText(selectedDatapoint);
                }
                if (typeSelection.equals("75th Percentile")) {
                    initListView("75th Percentile");
                    String datapoint = datapointsList_P75.get(i);
                    selectedDatapoint = datapoint;
                    datapointTextView.setText(selectedDatapoint);
                }
                onSelectedMode = true;
                updateButtonColor();
                initListView(typeSelection);
                activateButton();
            }
        });
    }
    public void initiateGraphingIntent() {
        Intent GraphingActivity = new Intent(DataComparisonDatapointSelectActivityTEAMS.this, DataComparisonGraphingActivityTEAMS.class);
        GraphingActivity.putExtra("teamOne", teamOne);
        GraphingActivity.putExtra("teamTwo", teamTwo);
        GraphingActivity.putExtra("teamThree", teamThree);
        GraphingActivity.putExtra("teamFour", teamFour);
        GraphingActivity.putExtra("selectedDatapoint",selectedDatapoint);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(DataComparisonDatapointSelectActivityTEAMS.this, R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(GraphingActivity, options.toBundle());

    }

 class DataComparisonDatapointSelectAdapterTEAMS extends BaseAdapter {

     private Context mContext;
     private ArrayList<String> datapointList;
     private ArrayList<String> datapointDescriptionList;

     public DataComparisonDatapointSelectAdapterTEAMS(Context context, ArrayList<String> datapointList, ArrayList<String> datapointDescriptionList) {
         mContext = context;
         this.datapointList = datapointList;
         this.datapointDescriptionList = datapointDescriptionList;

     }

     @Override
     public int getCount() {
         return datapointList.size(); //returns total of items in the list
     }

     @Override
     public Object getItem(int position) {
         return datapointList.get(position); //returns list item at the specified position
     }

     @Override
     public long getItemId(int position) {
         return position;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         if (convertView == null) {
             convertView = LayoutInflater.from(mContext).
                     inflate(R.layout.data_comparison_team_select_cell, parent, false);
         }

         String datapoint = (String) getItem(position);

         TextView datapointName = (TextView)
                 convertView.findViewById(R.id.teamNumberTextView);
         TextView datapointDescription = (TextView)
                 convertView.findViewById(R.id.teamName);
         TextView teamPosition = (TextView)
                 convertView.findViewById(R.id.rankPosition);

         datapointName.setTextSize(20);
         datapointDescription.setTextSize(14);
         datapointName.setText(datapoint);
         datapointDescription.setText(datapointDescriptionList.get(position));
         teamPosition.setText(String.valueOf(position + 1));

         if (datapoint.equals(DataComparisonDatapointSelectActivityTEAMS.selectedDatapoint)) {
             convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.MediumSpringGreen));
         } else {
             convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.White));
         }


         return convertView;
     }

 }

}


