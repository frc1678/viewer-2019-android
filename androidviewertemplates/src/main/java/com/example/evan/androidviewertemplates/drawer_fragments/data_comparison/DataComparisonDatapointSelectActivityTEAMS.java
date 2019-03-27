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
import android.util.Log;
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

    //creates all the lists for each type of data in TEAMS
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
    public static String selectedDatapointName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_comparison_teams);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //hardcoded title
        setTitle("TEAMS Datapoint Selection");
        //resets the selectedDatapoint on the re-creation of the activity (when someone back presses and goes back into it)
        selectedDatapoint = "";
        getExtras();
        getDatapoints();
        initXml();
        runSpinnerListener();
        datapointChosenListener();
    }

    public void getExtras() {
        //gets data from the previous activity
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
        //for each key in the normal datapoints list, the key gets put into the normal list
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_NORMAL.keySet()) {
            datapointsList_NORMAL.add(datapoint);
        }
        //for each key in the normal descriptions list, the key gets put into the normal descriptions list
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_NORMAL.keySet()) {
            String value = SpecificConstants.DATA_COMPARISON_TEAMS_NORMAL.get(datapoint);
            descriptionList_NORMAL.add(value);
        }
        //for each key in the standard deviation datapoints list, the key gets put into the standard deviation list
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_SD.keySet()) {
            datapointsList_SD.add(datapoint);
        }
        //for each key in the standard deviation description list, the key gets put into the standard deviation descriptions list
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_SD.keySet()) {
            String value = SpecificConstants.DATA_COMPARISON_TEAMS_SD.get(datapoint);
            descriptionList_SD.add(value);
        }
        //for each key in the last four matches datapoints list, the key gets put into the last four matches list
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_LFM.keySet()) {
            datapointsList_LFM.add(datapoint);
        }
        //for each key in the last four matches descriptions list, the key gets put into the last four matches descriptions list
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_LFM.keySet()) {
            String value = SpecificConstants.DATA_COMPARISON_TEAMS_LFM.get(datapoint);
            descriptionList_LFM.add(value);
        }
        //for each key in the 75th percentile datapoints list, the key gets put into the 75th percentile list
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_P75.keySet()) {
            datapointsList_P75.add(datapoint);
        }
        //for each key in the 75th percentile descriptions list, the key gets put into the 75th percentile descriptions list
        for (String datapoint : SpecificConstants.DATA_COMPARISON_TEAMS_P75.keySet()) {
            String value = SpecificConstants.DATA_COMPARISON_TEAMS_P75.get(datapoint);
            descriptionList_P75.add(value);
        }
    }

    public void initXml() {
        //initializes all the xml associated elements
        nextButton = (Button) findViewById(R.id.nextButton);
        datapointTextView = (TextView) findViewById(R.id.datapointTextView);
        datapointListView = (ListView) findViewById(R.id.datapointListView);
        typeSelectionSpinner = (Spinner) findViewById(R.id.typeSelectionSpinner);

        //updates the color of the button to its according color
        updateButtonColor();
        //makes the button not do anything
        disableButton();
        //inits the spinner listener
        initSpinner();
    }

    public void updateButtonColor() {
        //if the datapoint was selected, the color will turn light green
        if (onSelectedMode) {
            nextButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.LightGreen));
        }
        //if the datapoint was not selected, the color will be light grey
        else if (!onSelectedMode) {
            nextButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.LightGrey));
        }
    }

    public void disableButton() {
        //sets the color to light grey and makes the on click disabled
        nextButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.LightGrey));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NULL
            }
        });
    }

    public void initListView(String type) {
        //clears the type of selection
        typeSelection = "";
        //makes on selected mode false and updates the button color
        onSelectedMode = false;
        updateButtonColor();
        //saves the state of the list view before changing it (where you are on the scrolling)
        Parcelable state = datapointListView.onSaveInstanceState();
        //if in type normal (you choose "normal"), change the list the adapter is using to the according type
        if (type.equals("Normal")) {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_NORMAL, descriptionList_NORMAL);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection = "Normal";
        }
        //if in type lfm (you choose "last four matches"), change the list the adapter is using to the according type
        else if (type.equals("Last Four Matches")) {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_LFM, descriptionList_LFM);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection = "Last Four Matches";
        }
        //if in type sd (you choose "standard deviation"), change the list the adapter is using to the according type
        else if (type.equals("Standard Deviation")) {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_SD, descriptionList_SD);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection = "Standard Deviation";
        }
        //if in type 75th percentile (you choose "75th percentile"), change the list the adapter is using to the according type
        else if (type.equals("75th Percentile")) {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_P75, descriptionList_P75);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection = "75th Percentile";
        }
        //defaults to type "normal"
        else {
            DataComparisonDatapointSelectAdapterTEAMS dataComparisonAdapter = new DataComparisonDatapointSelectAdapterTEAMS(getApplicationContext(), datapointsList_NORMAL, descriptionList_NORMAL);
            datapointListView.setAdapter(dataComparisonAdapter);
            dataComparisonAdapter.notifyDataSetChanged();
            typeSelection = "Normal";
        }
        datapointListView.onRestoreInstanceState(state);
    }

    public void initSpinner() {
        //initializes the spinner and its adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SpecificConstants.CATEGORY_LIST);
        Spinner typeSelectionSpinner = (Spinner)
                findViewById(R.id.typeSelectionSpinner);
        typeSelectionSpinner.setAdapter(adapter);
    }

    public void runSpinnerListener() {
        //initializes the spinner listener
        typeSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //changes list view to the type of the selected item
                initListView(SpecificConstants.CATEGORY_LIST.get(position));
                //makes toast to notify user of the type that they're in
                SuperActivityToast.create(DataComparisonDatapointSelectActivityTEAMS.this, new Style(), Style.TYPE_STANDARD)
                        .setText("In type '" + SpecificConstants.CATEGORY_LIST.get(position) + "'")
                        .setDuration(Style.DURATION_VERY_SHORT)
                        .setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE))
                        .setAnimations(Style.ANIMATIONS_POP).show();

                //disables button
                disableButton();
                datapointTextView.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // required
            }

        });

    }

    public void activateButton() {
        //activates button by making it green and making the onclick begin an intent to the next activity
        nextButton.setBackgroundColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateGraphingIntent();
            }
        });

    }

    public void datapointChosenListener() {
        //figures out what type the list view is in and then finds the datapoint that was selected according to the type
        datapointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //if type "normal", then it finds the datapoint selected using position in the list of normal datapoints
                if (typeSelection.equals("Normal")) {
                    initListView("Normal");
                    String datapoint = datapointsList_NORMAL.get(i);
                    selectedDatapoint = datapoint;
                    selectedDatapointName = descriptionList_NORMAL.get(i);
                    datapointTextView.setText(selectedDatapoint);
                }
                //if type "last four matches", then it finds the datapoint selected using position in the list of normal datapoints
                if (typeSelection.equals("Last Four Matches")) {
                    initListView("Last Four Matches");
                    String datapoint = datapointsList_LFM.get(i);
                    selectedDatapoint = datapoint;
                    selectedDatapointName = descriptionList_LFM.get(i);
                    datapointTextView.setText(selectedDatapoint);
                }
                //if type "standard deviation", then it finds the datapoint selected using position in the list of normal datapoints
                if (typeSelection.equals("Standard Deviation")) {
                    initListView("Standard Deviation");
                    String datapoint = datapointsList_SD.get(i);
                    selectedDatapoint = datapoint;
                    selectedDatapointName = descriptionList_SD.get(i);
                    datapointTextView.setText(selectedDatapoint);
                }
                //if type "75th percentile", then it finds the datapoint selected using position in the list of normal datapoints
                if (typeSelection.equals("75th Percentile")) {
                    initListView("75th Percentile");
                    String datapoint = datapointsList_P75.get(i);
                    selectedDatapoint = datapoint;
                    selectedDatapointName = descriptionList_P75.get(i);
                    datapointTextView.setText(selectedDatapoint);
                }
                //turns on selected mode
                onSelectedMode = true;
                //updates button according to selectedmode
                updateButtonColor();
                //makes list view change to the type selected
                initListView(typeSelection);
                //makes the button active (allows click)
                activateButton();
            }
        });
    }

    public void initiateGraphingIntent() {
        //creates intent to DataComparisonGraphingActivityTEAMS
        Intent GraphingActivity = new Intent(DataComparisonDatapointSelectActivityTEAMS.this, DataComparisonGraphingActivityTEAMS.class);
        GraphingActivity.putExtra("teamOne", teamOne);
        GraphingActivity.putExtra("teamTwo", teamTwo);
        GraphingActivity.putExtra("teamThree", teamThree);
        GraphingActivity.putExtra("teamFour", teamFour);
        GraphingActivity.putExtra("selectedDatapoint", selectedDatapoint);
        GraphingActivity.putExtra("selectedDatapointName", selectedDatapointName);
        //adds slick animation lol
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(DataComparisonDatapointSelectActivityTEAMS.this, R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(GraphingActivity, options.toBundle());

    }

    //in file secondary adapter class
    class DataComparisonDatapointSelectAdapterTEAMS extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> datapointList;
        private ArrayList<String> datapointDescriptionList;

        //asks for context, datapointList and descriptionList,
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
            //gets the datapoint of click according to position and datapointList
            return datapointList.get(position);
            //returns list item at the specified position
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
            //makes datapoint the selected datapoint
            String datapoint = (String) getItem(position);

            //initializing the xml elements
            TextView datapointDescription = (TextView)
                    convertView.findViewById(R.id.teamNumberTextView);
            TextView datapointName = (TextView)
                    convertView.findViewById(R.id.teamName);
            TextView teamPosition = (TextView)
                    convertView.findViewById(R.id.rankPosition);

            //makes size of datapointname be larger than description
            datapointDescription.setTextSize(20);
            datapointName.setTextSize(14);
            //sets the text of the name and description
            datapointName.setText(datapoint);
            datapointDescription.setText(datapointDescriptionList.get(position));
            //sets the cell position to 1 + position (1,2,3 instead of 0,1,2)
            teamPosition.setText(String.valueOf(position + 1));

            //changes color of selected cell
            if (datapoint.equals(DataComparisonDatapointSelectActivityTEAMS.selectedDatapoint)) {
                convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.MediumSpringGreen));
            } else {
                convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.White));
            }

            return convertView;
        }

    }

}


