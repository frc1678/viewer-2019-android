
package com.example.evan.androidviewertemplates.firebase_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculatedTeamInMatchData extends Object {
    //make sure all data points are public
    public Integer lemonLoadSuccess;
    public Integer lemonsScored;
    public Integer lemonSuccessAll;
    public Integer lemonSuccessDefended;
    public Integer lemonSuccessUndefended;
    public Float pointsPrevented;
    public Float orangePointsPrevented;
    public Float lemonPointsPrevented;
    public Integer lemonSuccessL1;
    public Integer lemonSuccessL2;
    public Integer lemonSuccessL3;
    public Integer orangeFouls;
    public Integer orangesScored;
    public Integer orangeSuccessAll;
    public Integer orangeSuccessDefended;
    public Integer orangeSuccessUndefended;
    public Integer orangeSuccessL1;
    public Integer orangeSuccessL2;
    public Integer orangeSuccessL3;
    public Integer pinningFouls;
    public Integer lemonsPlacedSandstorm;
    public Integer orangesPlacedSandstorm;
    public Integer lemonsScoredTeleL1;
    public Integer lemonsScoredTeleL2;
    public Integer lemonsScoredTeleL3;
    public Integer orangesScoredTeleL1;
    public Integer orangesScoredTeleL2;
    public Integer orangesScoredTeleL3;
    public Integer selfClimbLevel;
    public Integer robot1ClimbLevel;
    public Integer robot2ClimbLevel;
    public Integer orangesScoredL1;
    public Integer lemonsScoredSandstorm;
    public Integer orangesScoredSandstorm;
    public Integer totalFailedCyclesCaused;

    public Float lemonCycleAll;
    public Float lemonCycleL1;
    public Float lemonCycleL2;
    public Float lemonCycleL3;
    public Float orangeCycleAll;
    public Float orangeCycleL1;
    public Float orangeCycleL2;
    public Float orangeCycleL3;
    public Float timeIncap;
    public Float timeClimbing;
    public Float timeDefending;
    public Float pointsPrevented;

    public Boolean isIncapEntireMatch;
}