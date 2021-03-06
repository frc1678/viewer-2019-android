package com.example.evan.androidviewertools.search_view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.evan.androidviewertools.ViewerActivity;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.team_ranking.TeamRankingsFragment;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public abstract class SearchableFirebaseListAdapter<T> extends BaseAdapter {
    public String searchString;
    public String selectedScope;
    public List<T> filteredValues = new ArrayList<>();
    Comparator<T> filterComparator;
    public Context context;
 //   public boolean sortByNumber;
    private BroadcastReceiver broadcastReceiver;

    public SearchableFirebaseListAdapter(Context context, Comparator<T> filterComparator) {
        this.filterComparator = filterComparator;
        this.context = context;
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onFirebaseDataChanged();
                searchWithTextInScope(searchString, selectedScope);
                notifyDataSetChanged();
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, new IntentFilter(getBroadcastAction()));

        BroadcastReceiver starReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                notifyDataSetChanged();
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(starReceiver, new IntentFilter(Constants.STARS_MODIFIED_ACTION));
    }

    public void searchWithTextInScope(String searchString, String scope) {
        this.searchString = searchString;
        this.selectedScope = scope;
        filterValues();
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        filterValues();

        super.notifyDataSetChanged();
    }

    public void filterValues() {
        filteredValues.clear();
        for (T value : getFirebaseList()) {
            if(filter(value, selectedScope)) {
                filteredValues.add(value);
            }
        }
        if(Constants.sortByTeamNumber) {
            sortByTeamNumber();

        }else if(Constants.sortByRank){
            sortByTeamRank();

        }else if(Constants.sortByFirstPick){
            sortByFirstPick();
        }else if(Constants.sortBySecondPick){
            sortBySecondPick();
        }else if(Constants.sortByThirdPick){
            sortByThirdPick();
        }else if(Constants.sortByLfm){
            sortByLfm();
        }
        else{
            sortByTeamRank();
        }
    }
    public void sortByTeamNumber(){
        Collections.sort(filteredValues, new Comparator<T>() {
            public int compare(T obj1, T obj2) {
                // ## Ascending order
                Integer teamNumberOne = (Integer) Utils.getObjectField(obj1, "teamNumber");
                Integer teamNumberTwo = (Integer) Utils.getObjectField(obj2, "teamNumber");
                return (teamNumberOne).compareTo(teamNumberTwo);// To compare string values
                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            }
        });
    }
    public void sortByTeamRank(){
        Collections.sort(filteredValues, filterComparator);
        Constants.sortByRank = false;
    }
    public void sortByFirstPick(){
        Collections.sort(filteredValues, new Comparator<T>() {
            public int compare(T obj1, T obj2) {
                // ## Ascending order
                Float teamNumberOne = (Float) Utils.getObjectField(obj1, "calculatedData.firstPickAbility");
                Float teamNumberTwo = (Float) Utils.getObjectField(obj2, "calculatedData.firstPickAbility");
                return (teamNumberTwo).compareTo(teamNumberOne);// To compare string values
                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            }
        });
    }
    public void sortBySecondPick(){
        Collections.sort(filteredValues, new Comparator<T>() {
            public int compare(T obj1, T obj2) {
                // ## Ascending order
                Float teamNumberOne = (Float) Utils.getObjectField(obj1, "calculatedData.secondPickAbility");
                Float teamNumberTwo = (Float) Utils.getObjectField(obj2, "calculatedData.secondPickAbility");
                return (teamNumberTwo).compareTo(teamNumberOne);// To compare string values
                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            }
        });
    }
    public void sortByThirdPick(){
        Collections.sort(filteredValues, new Comparator<T>() {
            public int compare(T obj1, T obj2) {
                // ## Ascending order
                Float teamNumberOne = (Float) Utils.getObjectField(obj1, "calculatedData.thirdPickAbility");
                Float teamNumberTwo = (Float) Utils.getObjectField(obj2, "calculatedData.thirdPickAbility");
                return (teamNumberTwo).compareTo(teamNumberOne);// To compare string values
                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            }
        });
    }
    public void sortByLfm(){
        String modified = Constants.rankFilterName.substring(0, 1).toUpperCase() + Constants.rankFilterName.substring(1);
        final String dataName = "calculatedData." + "lfm" + modified;
        if(checkIfLfmIsViable(dataName)) {
            Collections.sort(filteredValues, new Comparator<T>() {
                public int compare(T obj1, T obj2) {
                    // ## Ascending order
                    Float teamNumberOne = (Float) Utils.getObjectField(obj1, dataName);
                    Float teamNumberTwo = (Float) Utils.getObjectField(obj2, dataName);
                    return (teamNumberTwo).compareTo(teamNumberOne);// To compare string values
                    // ## Descending order
                    // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                }
            });
        }
    }

    public Boolean checkIfLfmIsViable(String dataName){
        Boolean viable = false;
        for(int i = 0; i < Constants.viableLFM.size(); i++){
            if(dataName.equals(Constants.viableLFM.get(i))){
                viable = true;
            }
        }
        return viable;
    }

    public abstract String getBroadcastAction();

    public abstract boolean filter(T value, String scope);

    public abstract List<T> getFirebaseList();

    public void onFirebaseDataChanged() { }

    public void cleanup() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }
}
