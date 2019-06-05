
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
    public Integer cargoFouls;
    public Integer cargoScored;
    public Integer cargoSuccessAll;
    public Integer cargoSuccessDefended;
    public Integer cargoSuccessUndefended;
    public Integer cargoSuccessL1;
    public Integer cargoSuccessL2;
    public Integer cargoSuccessL3;
    public Integer pinningFouls;
    public Integer lemonsPlacedSandstorm;
    public Integer cargoPlacedSandstorm;
    public Integer lemonsScoredTeleL1;
    public Integer lemonsScoredTeleL2;
    public Integer lemonsScoredTeleL3;
    public Integer cargoScoredTeleL1;
    public Integer cargoScoredTeleL2;
    public Integer cargoScoredTeleL3;
    public Integer selfClimbLevel;
    public Integer robot1ClimbLevel;
    public Integer robot2ClimbLevel;
    public Integer cargoScoredL1;
    public Integer lemonsScoredSandstorm;
    public Integer cargoScoredSandstorm;
    public Integer totalFailedCyclesCaused;

    public Float pointsPrevented;
    public Float cargoPointsPrevented;
    public Float lemonPointsPrevented;
    public Float lemonCycleAll;
    public Float lemonCycleL1;
    public Float lemonCycleL2;
    public Float lemonCycleL3;
    public Float cargoCycleAll;
    public Float cargoCycleL1;
    public Float cargoCycleL2;
    public Float cargoCycleL3;
    public Float timeIncap;
    public Float timeClimbing;
    public Float timeDefending;

    public Boolean isIncapEntireMatch;
}
