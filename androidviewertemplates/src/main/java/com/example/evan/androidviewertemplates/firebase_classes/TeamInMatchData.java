
package com.example.evan.androidviewertemplates.firebase_classes;

import com.example.evan.androidviewertools.firebase_classes.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by evan on 6/18/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamInMatchData extends com.example.evan.androidviewertools.firebase_classes.TeamInMatchData {
public CalculatedTeamInMatchData calculatedData;
    //Make sure that all variables are public
//    public List<Map<Object,Object>> timeline;

    public Integer driverStation;
    public Integer number;
    public Integer startingLevel;

    public Boolean crossedHabLine;
    public Boolean isNoShow;

    public String startingLocation;
    public String preload;
    public String superNotes;
}
