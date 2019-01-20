package com.example.evan.androidviewertemplates.firebase_classes;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Teo on 1/11/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculatedMatchData extends Object {
    //Make sure all variables are public
    public Integer blueChanceRocketRP;
    public Integer redChanceRocketRP;
    public Integer blueChanceClimbRP;
    public Integer redChanceClimbRP;
    public Integer blueChanceWin;
    public Integer redChanceWin;

    public Float bluePredictedScore;
    public Float redPredictedScore;
    public Float redPredictedClimbPoints;
    public Float bluePredictedClimbPoints;
}
