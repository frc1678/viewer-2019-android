
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
    public Integer lemonsSpilled;
    public Integer lemonsPlacedSandstorm;
    public Integer orangesPlacedSandstorm;
    public Integer lvl_1_lemons_teleop;
    public Integer lvl_2_lemons_teleop;
    public Integer lvl_3_lemons_teleop;
    public Integer lvl_1_oranges_teleop;
    public Integer lvl_2_oranges_teleop;
    public Integer lvl_3_oranges_teleop;
    public Integer selfClimbLevel;
    public Integer robot1ClimbLevel;
    public Integer robot2ClimbLevel;
    public Integer orangesScoredL1;

    public Float lemonCycleAll;
    public Float lemonCycleL1;
    public Float lemonCycleL2;
    public Float lemonCycleL3;
    public Float orangeCycleAll;
    public Float orangeCycleL1;
    public Float orangeCycleL2;
    public Float orangeCycleL3;
    public Float timeIncap;
    public Float timeImpaired;
    public Float timeClimbing;

    public Boolean isIncapEntireMatch;
}