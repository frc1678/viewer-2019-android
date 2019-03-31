
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
    public Integer pitWeight;
    public Integer number;
    public Integer actualRPs;
    public Integer matchesPlayed;
    public Integer actualSeed;
    public Integer pitNumDriveTrainMotors;
    public Integer pitDriveTrainMotorType;
    public Integer pitSEALsRampRanking;

    public String pitWheelDiameter;
    public String pitDrivetrain;
    public String pitProgrammingLanguage;
    public String pitSandstormNavigationType;
    public String pitSEALsNotes;

    public Map<Object, Integer> pitClimbType;

    public Boolean pitHasCamera;
    public Boolean pitHasVision;
    public Boolean pitHasPid;
    public Boolean pitHasGyro;
    public Boolean pitHasEncoders;
    public Boolean pitHasOrangeShooter;
    public Boolean pitIsLemonSpecialist;
}
