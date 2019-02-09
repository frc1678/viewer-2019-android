package com.example.evan.androidviewertemplates.firebase_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Katherine on 1/12/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculatedTeamData extends Object {
    //make sure that all variables are public
    public Integer actualSeed;
    public Integer predictedSeed;
    public Integer lemonLoadSuccess;
    public Integer orangeSuccessAll;
    public Integer orangeSuccessDefended;
    public Integer orangeSuccessUndefended;
    public Integer orangeSuccessL1;
    public Integer orangeSuccessL2;
    public Integer orangeSuccessL3;
    public Integer lemonSuccessAll;
    public Integer lemonSuccessDefended;
    public Integer lemonSuccessUndefended;
    public Integer lemonSuccessL1;
    public Integer lemonSuccessL2;
    public Integer lemonSuccessL3;
    public Integer lemonSuccessFromSide;
    public Integer habLineSuccessL1;
    public Integer habLineSuccessL2;
    public Integer percentIncap;
    public Integer percentImpaired;
    public Integer percentNoShow;
    public Integer lfmLemonLoadSuccess;
    public Integer lfmOrangeSuccessAll;
    public Integer lfmOrangeSuccessDefended;
    public Integer lfmOrangeSuccessUndefended;
    public Integer lfmOrangeSuccessL1;
    public Integer lfmOrangeSuccessL2;
    public Integer lfmOrangeSuccessL3;
    public Integer lfmLemonSuccessAll;
    public Integer lfmLemonSuccessDefended;
    public Integer lfmLemonSuccessUndefended;
    public Integer lfmLemonSuccessL1;
    public Integer lfmLemonSuccessL2;
    public Integer lfmLemonSuccessL3;
    public Integer lfmLemonSuccessFromSide;
    public Integer lfmHabLineSuccessL1;
    public Integer lfmHabLineSuccessL2;
    public Integer lfmPercentIncap;
    public Integer lfmPercentImpaired;
    public Integer lfmPercentNoShow;
    public Integer sdLemonLoadSuccess;
    public Integer sdOrangeSuccessAll;
    public Integer sdOrangeSuccessDefended;
    public Integer sdOrangeSuccessUndefended;
    public Integer sdOrangeSuccessL1;
    public Integer sdOrangeSuccessL2;
    public Integer sdOrnageSuccessL3;
    public Integer sdLemonSuccessAll;
    public Integer sdLemonSuccessDefended;
    public Integer sdLemonSuccessUndefended;
    public Integer sdLemonSuccessL1;
    public Integer sdLemonSuccessL2;
    public Integer sdLemonSuccessL3;
    public Integer sdLemonSuccessFromSide;
    public Integer sdHabLineSuccessL1;
    public Integer sdHabLineSuccessL2;
    public Integer sdPercentIncap;
    public Integer sdPercentImpaired;
    public Integer sdPercentNoShow;
    public Integer p75lemonLoadSuccess;
    public Integer p75orangeSuccessAll;
    public Integer p75orangeSuccessDefended;
    public Integer p75orangeSuccessUndefended;
    public Integer p75orangeSuccessL1;
    public Integer p75orangeSuccessL2;
    public Integer p75orangeSuccessL3;
    public Integer p75lemonSuccessAll;
    public Integer p75lemonSuccessDefended;
    public Integer p75lemonSuccessUndefended;
    public Integer p75lemonSuccessL1;
    public Integer p75lemonSuccessL2;
    public Integer p75lemonSuccessL3;
    public Integer p75lemonSuccesssFromSide;
    public Integer p75habLineSuccessL1;
    public Integer p75habLineSuccessL2;
    public Integer p75percentIncap;
    public Integer p75percentImpaired;
    public Integer p75percentNoShow;

    public Float predictedRPs;
    public Float avgOrangesScored;
    public Float avgLemonsScored;
    public Float avgOrangeFouls;
    public Float orangeCycleAll;
    public Float orangeCycleL1;
    public Float orangeCycleL2;
    public Float orangeCycleL3;
    public Float lemonCycleAll;
    public Float lemonCycleL1;
    public Float lemonCycleL2;
    public Float lemonCycleL3;
    public Float avgGoodDecisions;
    public Float avgBadDecisions;
    public Float avgTimeIncap;
    public Float avgTimeImpaired;
    public Float avgTimeClimbing;
    public Float predictedDedicatedLemonCycles;
    public Float predictedDedicatedOrangeCycles;
    public Float predictedSoloPoints;
    public Float lemonAbility;
    public Float orangeAbility;
    public Float firstPickAbility;
    public Float secondPickAbility;
    public Float avgLemonsSpilled;
    public Float lfmAvgOrangesScored;
    public Float lfmAvgLemonsScored;
    public Float lfmAvgOrangeFouls;
    public Float lfmOrangeCycleAll;
    public Float lfmOrangeCycleL1;
    public Float lfmOrangeCycleL2;
    public Float lfmOrangeCycleL3;
    public Float lfmLemonCycleAll;
    public Float lfmLemonCycleL1;
    public Float lfmLemonCycleL2;
    public Float lfmLemonCycleL3;
    public Float lfmAvgGoodDecisions;
    public Float lfmAvgBadDecisions;
    public Float lfmAvgTimeIncap;
    public Float lfmAvgTimeImpaired;
    public Float lfmAvgTimeClimbing;
    public Float lfmAvgLemonsSpilled;
    public Float sdAvgOrangesScored;
    public Float sdAvgLemonsScored;
    public Float sdAvgOrangeFouls;
    public Float sdOrangeCycleAll;
    public Float sdOrangeCycleL1;
    public Float sdOrangeCycleL2;
    public Float sdOrangeCycleL3;
    public Float sdLemonCycleAll;
    public Float sdLemonCycleL1;
    public Float sdLemonCycleL2;
    public Float sdLemonCycleL3;
    public Float sdAvgGoodDecisions;
    public Float sdAvgBadDecisions;
    public Float sdAvgTimeIncap;
    public Float sdAvgTimeImpaired;
    public Float sdAvgTimeClimbing;
    public Float sdAvgLemonsSpilled;
    public Float p75avgOrangesScored;
    public Float p75avgLemonsScored;
    public Float p75avgOrangeFouls;
    public Float p75oranceCycleAll;
    public Float p75orangeCycleL1;
    public Float p75orangeCycleL2;
    public Float p75orangeCycleL3;
    public Float p75lemonCycleAll;
    public Float p75lemonCycleL1;
    public Float p75lemonCycleL2;
    public Float p75lemonCycleL3;
    public Float p75avgGoodDecisions;
    public Float p75avgBadDecisions;
    public Float p75avgTimeIncap;
    public Float p75avgTimeImpaired;
    public Float p75avgTimeClimbing;
    public Float p75avgLemonsSpilled;

    public Boolean hasOrangeGroundIntake;
    public Boolean hasLemonGroundIntake;
    public Boolean didPreloadOrange;
    public Boolean didPreloadLemon;
}
