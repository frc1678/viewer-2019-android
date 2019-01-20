
package com.example.evan.androidviewertemplates.firebase_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Teo on 1/10/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamTemplate extends com.example.evan.androidviewertools.firebase_classes.Team {
public CalculatedTeamData calculatedData;
    //Make sure that all variables are public
    public Integer pitWidth;
    public Integer pitLength;
    public Integer pitMinHeight;
    public Integer pitMaxHeight;
    public Integer pitWeight;
    public Integer number;
    public Integer actualRPs;
    public Integer matchesPlayed;

    public String pitWheelDiameter;
    public String pitDriveTrain;
    public String pitProgrammingLanguage;
    public String pitSandstormNavigationType;
    public String name;
    public String pitSEALsNotes;

    public Map<Integer, Integer> pitClimbType;

    public Boolean pitHasCamera;
    public Boolean pitHasVision;
    public Boolean pitHasPid;
    public Boolean pitHasGyro;
    public Boolean pitHasEncoders;
    public Boolean pitCaBuddyStartLevel2;
    public Boolean pitHasOrangeShooter;
}
