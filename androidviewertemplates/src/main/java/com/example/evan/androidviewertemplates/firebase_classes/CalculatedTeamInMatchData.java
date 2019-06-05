
package com.example.evan.androidviewertemplates.firebase_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculatedTeamInMatchData extends Object {
    //make sure all data points are public
    public Integer panelLoadSuccess;
    public Integer panelsScored;
    public Integer panelSuccessAll;
    public Integer panelSuccessDefended;
    public Integer panelSuccessUndefended;
    public Integer panelSuccessL1;
    public Integer panelSuccessL2;
    public Integer panelSuccessL3;
    public Integer cargoFouls;
    public Integer cargoScored;
    public Integer cargoSuccessAll;
    public Integer cargoSuccessDefended;
    public Integer cargoSuccessUndefended;
    public Integer cargoSuccessL1;
    public Integer cargoSuccessL2;
    public Integer cargoSuccessL3;
    public Integer pinningFouls;
    public Integer panelsPlacedSandstorm;
    public Integer cargoPlacedSandstorm;
    public Integer panelsScoredTeleL1;
    public Integer panelsScoredTeleL2;
    public Integer panelsScoredTeleL3;
    public Integer cargoScoredTeleL1;
    public Integer cargoScoredTeleL2;
    public Integer cargoScoredTeleL3;
    public Integer selfClimbLevel;
    public Integer robot1ClimbLevel;
    public Integer robot2ClimbLevel;
    public Integer cargoScoredL1;
    public Integer panelsScoredSandstorm;
    public Integer cargoScoredSandstorm;
    public Integer totalFailedCyclesCaused;

    public Float pointsPrevented;
    public Float cargoPointsPrevented;
    public Float panelPointsPrevented;
    public Float panelCycleAll;
    public Float panelCycleL1;
    public Float panelCycleL2;
    public Float panelCycleL3;
    public Float cargoCycleAll;
    public Float cargoCycleL1;
    public Float cargoCycleL2;
    public Float cargoCycleL3;
    public Float timeIncap;
    public Float timeClimbing;
    public Float timeDefending;

    public Boolean isIncapEntireMatch;
}
