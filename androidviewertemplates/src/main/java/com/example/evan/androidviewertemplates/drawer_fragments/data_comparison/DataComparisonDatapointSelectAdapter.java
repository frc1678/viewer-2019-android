package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.Utils;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DataComparisonDatapointSelectAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> datapointList;
    private ArrayList<String> datapointDescriptionList;

    //asks for context, datapointList and descriptionList,
    public DataComparisonDatapointSelectAdapter(Context context, ArrayList<String> datapointList, ArrayList<String> datapointDescriptionList) {
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
        if (datapoint.equals(DataComparisonDatapointSelectActivityTIMD.selectedDatapoint)) {
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.MediumSpringGreen));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.White));
        }

        return convertView;
    }


}
