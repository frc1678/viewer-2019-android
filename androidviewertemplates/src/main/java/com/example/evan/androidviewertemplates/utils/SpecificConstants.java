package com.example.evan.androidviewertemplates.utils;

import android.util.Log;

import com.example.evan.androidviewertools.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificConstants extends Constants {
    //todo
    public static Map<String, String> KEYS_TO_TITLES;
    public static Map<String, String> DATA_TO_GRAPH;
    public static Map<String, String> DATA_COMPARISON_TEAMS_NORMAL;
    public static Map<String, String> DATA_COMPARISON_TEAMS_SD;
    public static Map<String, String> DATA_COMPARISON_TEAMS_LFM;
    public static Map<String, String> DATA_COMPARISON_TEAMS_P75;
    public static Map<String, String> DATA_COMPARISON_TIMD;
    public static List<String> CATEGORY_LIST;

    //todo
    public static final String[] DRAWER_TITLES = {"Schedule", "Upcoming Matches", "Recent Matches", "Starred Matches", "Our Schedule", "Seeding", "First Pick", "Second Pick", "Super Data", "Live Picklist", "Settings", "Data Comparison"};
    public static final String ORIGINAL_ROOT_FIREBASE_PATH = "https://dev3-2018.firebaseio.com/";
    public static String ROOT_FIREBASE_PATH = ORIGINAL_ROOT_FIREBASE_PATH;
    public static String MATCHES_PATH = ORIGINAL_ROOT_FIREBASE_PATH + "Matches";
    public static String TEAMS_PATH = ORIGINAL_ROOT_FIREBASE_PATH + "Teams";
    public static String TEAM_IN_MATCH_DATAS_PATH = ORIGINAL_ROOT_FIREBASE_PATH + "TIMDs";

        static {
        Map<String, String> initialKeysToTitlesMap = new HashMap<String, String>() {
            {
                    put("matches", "Matches");
                    put("VIEWER.matchesUntilNextMatchForTeam", "Matches Until Next Match");
//                    put("calculatedData.numMatchesPlayed", "Num. Matches Played");
                    put("lfm", "Last Four Matches");
//              TIMDs
                    put("startingLevel","Starting level");
                    put("crossedHabLine"," Crossed HAB line");
                    put("startingLocation","Starting location");
                    put("preload","Preloaded game piece");
                    put("driverStation","Driver station ");
                    put("isNoShow","Did not show up to match");
                    put("orangesScored","# cargo scored");
                    put("lemonsScored","# panel scored");
                    put("orangeFouls","Cargo fouls out of field");
                    put("calculatedData.lemonLoadSuccess","LS intake success");
                    put("calculatedData.orangeCycleAll","Avg cargo in RS/CS");
                    put("orangeCycleL1","Avg cargo L1 RS/CS");
                    put("orangeCycleL2","Avg cargo L2 RS");
                    put("orangeCycleL3","Avg cargo L3 RS");
                    put("calculatedData.lemonCycleAll","Avg hatch in RS/CS");
                    put("lemonCycleL1","Avg panel L1 RS/CS");
                    put("lemonCycleL2","Avg panel L2 RS");
                    put("lemonCycleL3","Avg panel L3 RS");
                    put("orangeSuccessAll","% cargo total success");
                    put("calculatedData.orangeSuccessDefended","% cargo defend success");
                    put("orangeSuccessUndefended","% cargo undefended success");
                    put("orangeSuccessL1","% cargo success L1 RS/CS");
                    put("calculatedData.orangeSuccessL2","% cargo success L2 RS");
                    put("calculatedData.orangeSuccessL3","% cargo success L3 RS");
                    put("lemonSuccessAll","% panel total success");
                    put("calculatedData.lemonSuccessDefended","% panel defend success");
                    put("lemonSuccessUndefended","% panel undefended success");
                    put("calculatedData.lemonSuccessL1","% panel success L1");
                    put("calculatedData.lemonSuccessL2","% panel success L2");
                    put("calculatedData.lemonSuccessL3","% panel success L3");
                    put("timeIncap","Time incap (s)");
                    put("timeImpaired","Time impaired (s)");
                    put("timeClimbing","Time climbing (s)");
                    put("lemonsSpilled","Panels spilled");
//              Teams
                    put("pitWidth","Width");
                    put("pitLength","Length");
                    put("pitMinHeight","Min. height");
                    put("pitMaxHeight","Max height");
                    put("pitWeight","Weight");
                    put("pitWheelDiameter","Wheel diameter");
                    put("pitDrivetrain","Drive train");
                    put("pitProgrammingLanguage","Programming language");
                    put("pitClimbType","Climb type");
                    put("pitHasCamera","Has camera");
                    put("pitHasVision","Has vision");
                    put("pitHasPid","Has pid");
                    put("pitHasGyro","Has gyro");
                    put("pitHasEncoders","Has encoders");
                    put("pitCanBuddyStartLevel2","Can start with another robot L2");
                    put("pitSandstormNavigationType","Sandstorm navigation");
                    put("pitHasOrangeShooter","Has cargo shooter");
                    put("pitSEALsNotes","SEALsNotes");
                    put("teamNumber","Team #");
                    put("name","Team name");
                    put("actualRPs","Total # RP");
                    put("matchesPlayed","Matches played");
                    put("actualSeed","Seed");
                    put("predictedRPs","Predicted PRs");
                    put("predictedSeed","Predicted seed");
                    put("calculatedData.hasOrangeGroundIntake","Has cargo ground intake");
                    put("calculatedData.hasLemonGroundIntake","Has panel ground intake");
                    put("didPreloadLemon","Preloaded lemon at least once");
                    put("calculatedData.avgOrangesScored","Avg cargo scored");
                    put("calculatedData.avgLemonsScored","Avg hatch scored");
                    put("calculatedData.avgOrangeFouls","Avg cargo fouls out of field");
                    put("lemonSuccessFromSide","Far side RS or side CS placements");
                    put("calculatedData.habLineSuccessL1","% HAB line from L1 success");
                    put("calculatedData.habLineSuccessL2","% HAB line from L2 success");
                    put("calculatedData.avgGoodDecisions","Avg good Decisions");
                    put("calculatedData.avgBadDecisions","Avg bad decisions");
                    put("calculatedData.avgTimeIncap","Avg incap time");
                    put("calculatedData.avgTimeImpaired","Avg impaired time");
                    put("calculatedData.avgTimeClimbing","Avg climbing time");
                    put("calculatedData.percentIncap","% incap");
                    put("calculatedData.percentImpaired","% impaired");
                    put("calculatedData.percentNoShow","% no show");
                    put("predictedDedicatedLemonCycles","Predicted only panel cycles");
                    put("predictedDedicatedOrangeCycles","Predicted only cargo");
                    put("calculatedData.predictedSoloPoints","Predicted points by team (alone)");
                    put("orangeAbility","Cargo ability");
                    put("lemonAbility","Panel ability");
                    put("firstPickAbility","First pick ability");
                    put("secondPickAbility","Second pick ability");
                    put("calculatedData.avgLemonsSpilled","Avg panels spilled");
        //lfm
                    put("calculatedData.lfmAvgOrangesScored","Avg cargo scored");
                    put("calculatedData.lfmAvgLemonsScored","Avg panel scored");
                    put("calculatedData.lfmAvgOrangeFouls","Avg cargo fouls");
                    put("calculatedData.lfmLemonLoadSuccess","Panel LS success");
                    put("calculatedData.lfmOrangeCycleAll","Avg cargo RS");
                    put("lfmOrangeCycleL1","Avg cargo L1 RS/CS");
                    put("lfmOrangeCycleL2","Avg cargo L2 RS");
                    put("lfmOrangeCycleL3","Avg cargo L3 RS");
                    put("calculatedData.lfmLemonCycleAll","Avg panel RS");
                    put("lfmLemonCycleL1","Avg panel L1 RS");
                    put("lfmLemonCycleL2","Avg panel L2 RS");
                    put("lfmLemonCycleL3","Avg panel L3 RS");
                    put("lfmOrangeSuccessAll","Cargo all success");
                    put("calculatedData.lfmOrangeSuccessDefended","Cargo defended success");
                    put("lfmOrangeSuccessUndefended","Cargo undefended success");
                    put("lfmOrangeSuccessL1","Avg panel L1 success");
                    put("calculatedData.lfmOrangeSuccessL2","Avg panel L2 success");
                    put("calculatedData.lfmOrangeSuccessL3","Avg panel L3 success");
                    put("lfmLemonSuccessAll","Avg panel all success");
                    put("calculatedData.lfmLemonSuccessDefended","Avg panel defended success");
                    put("lfmLemonSuccessUndefended","Avg panel undefended success");
                    put("lfmLemonSuccessL1","Avg panel L1 success");
                    put("calculatedData.lfmLemonSuccessL2","Avg panel L2 success");
                    put("calculatedData.lfmLemonSuccessL3","Avg panel L3 success");
                    put("lfmLemonSuccessFromSide","Avg far side RS or side CS placement");
                    put("calculatedData.lfmHabLineSuccessL1","HAB line success start from L1");
                    put("calculatedData.lfmHabLineSuccessL2","HAB line success start from L2");
                    put("calculatedData.lfmAvgGoodDecisions","Avg good decisions");
                    put("calculatedData.lfmAvgBadDecisions","Avg bad decisions");
                    put("calculatedData.lfmAvgTimeIncap","Avg time incap");
                    put("calculatedData.lfmAvgTimeImpaired","Avg time impaired");
                    put("calculatedData.lfmAvgTimeClimbing","Avg time climbing");
                    put("calculatedData.lfmPercentIncap","% incap");
                    put("calculatedData.lfmPercentImpaired","% impaired");
                    put("calculatedData.lfmPercentNoShow","% no show");
                    put("lfmAvgLemonsSpilled","Avg panels spilled");
            //sd
                    put("calculatedData.sdAvgOrangesScored","SD avg cargo scored");
                    put("calculatedData.sdAvgLemonsScored","SD avg hatch scored");
                    put("sdAvgOrangeFouls","Avg cargo fouls");
                    put("sdLemonLoadSuccess","Avg panel LS success");
                    put("sdOrangeCycleAll","Avg cargo in RS/CS");
                    put("sdOrangeCycleL1","Avg cargo in L1 RS/CS");
                    put("sdOrangeCycleL2","Avg cargo in L2 RS");
                    put("sdOrangeCycleL3","Avg cargo in L3 RS");
                    put("sdLemonCycleAll","Avg panel in RC/CS");
                    put("sdLemonCycleL1","Avg panel in L1 RC/CS");
                    put("sdLemonCycleL2","Avg panel in L2 RC/CS");
                    put("sdLemonCycleL3","Avg panel in L3 RC/CS");
                    put("sdOrangeSuccessAll","Avg cargo all success");
                    put("sdOrangeSuccessDefended","Avg cargo defended success");
                    put("sdOrangeSuccessUndefended","Avg cargo undefended success");
                    put("sdOrangeSuccessL1","Avg cargo success L1");
                    put("sdOrangeSuccessL2","Avg cargo success L2");
                    put("sdOrangeSuccessL3","Avg cargo success L3");
                    put("sdLemonSuccessAll","Avg panel all success");
                    put("sdLemonSuccessDefended","Avg panel defended success");
                    put("sdLemonSuccessUndefended","Avg panel undefended success");
                    put("sdLemonSuccessL1","Avg cargo success L1");
                    put("sdLemonSuccessL2","Avg cargo success L2");
                    put("sdLemonSuccessL3","Avg cargo success L3");
                    put("sdLemonSuccessFromSide","Avg far side RS or side CS placement");
                    put("sdHabLineSuccessL1","HAB line success start from L1");
                    put("sdHabLineSuccessL2","HAB line success start from L2");
                    put("sdAvgGoodDecisions","Avg good decisions");
                    put("sdAvgBadDecisions","Avg bad decisions");
                    put("sdAvgTimeIncap","Avg time incap");
                    put("sdAvgTimeImpaired","Avg time impaired");
                    put("sdAvgTimeClimbing","Avg time climbing");
                    put("sdPercentIncap","% incap");
                    put("sdPercentImpaired","% impaired");
                    put("sdPercentNoShow","% no show");
                    put("sdAvgLemonsSpilled","Avg panels spilled");
            //p75
                    put("p75avgOrangesScored","Avg cargo scored");
                    put("calculatedData.p75avgLemonsScored","Avg panels scored");
                    put("p75avgOrangeFouls","Avg cargo foul out of field");
                    put("p75lemonLoadSuccess","Panel LS success");
                    put("p75orangeCycleAll","Avg cargo in RS/CS");
                    put("p75orangeCycleL1","Avg cargo in L1 RS");
                    put("p75orangeCycleL2","Avg cargo in L2 RS");
                    put("p75orangeCycleL3","Avg cargo in L3 RS");
                    put("p75lemonCycleAll","Avg panel in RS/CS");
                    put("p75lemonCycleL1","Avg panel in L1 RS");
                    put("p75lemonCycleL2","Avg panel in L2 RS");
                    put("p75lemonCycleL3","Avg panel in L3 RS");
                    put("p75orangeSuccessAll","Avg cargo all success");
                    put("p75orangeSuccessDefended","Avg cargo defended success");
                    put("p75orangeSuccessUndefended","Avg cargo undefended success");
                    put("p75orangeSuccessL1","Avg cargo success in L1 RS");
                    put("p75orangeSuccessL2","Avg cargo success in L2 RS");
                    put("p75orangeSuccessL3","Avg cargo success in L3 RS");
                    put("p75lemonSuccessAll","Avg panel all success");
                    put("p75lemonSuccessL1","Avg panel success in L1 RS");
                    put("p75lemonSuccessL2","Avg panel success in L2 RS");
                    put("p75lemonSuccessL3","Avg panel success in L3 RS");
                    put("p75lemonSuccessFromSide","Avg panel success from side");
                    put("p75habLineSuccessL1","HAB line success start from L1");
                    put("p75habLineSuccessL2","HAB line success start from L2");
                    put("p75avgGoodDecisions","Avg good decisions");
                    put("p75avgBadDecisions","Avg bad decisions");
                    put("p75avgTimeIncap","Avg time incap");
                    put("p75avgTimeImpaired","Avg time impaired");
                    put("p75avgTimeClimbing","Avg time climbing");
                    put("p75percentIncap","% incap");
                    put("p75percentImpaired","% impaired");
                    put("p75percentNoShow","% no show");
                    put("p75AvgLemonsSpilled","Avg panels spilled");
//              Matches
                    put("cargoShipPreload","Cargo ship preload");
                    put("blueTeams","Blue alliance teams");
                    put("redTeams","Red alliance teams");
                    put("noShowTeams","No show teams");
                    put("blueActualScore","Blue score");
                    put("redActualScore","Red score");
                    put("blueFoulPoints","Blue fouls points");
                    put("redFoulPoints","Red fouls points");
                    put("blueActualRPs","Blue total RP");
                    put("redActualRPs","Red total RP");
                    put("blueDidRocketRP","Blue rocket RP");
                    put("redDidRocketRP","Red rocket RP");
                    put("blueDidClimbRP","Blue climb RP");
                    put("redDidClimbRP","Red climb RP");
                    put("blueChanceRocketRP","Blue chance rocket RP");
                    put("redChanceRocketRP","Red chance rocket RP");
                    put("blueChanceClimbRP","Blue chance climb RP");
                    put("redChanceClimbRP","Red chance climb RP");
                    put("bluePredictedScore","Blue predicted score");
                    put("redPredictedScore","Red predicted score");
                    put("blueChanceWin","Blue chance win");
                    put("redChanceWin","Red chance win");
                    put("bluePredictedClimbPoints","Blue predicted climb points");
                    put("redPredictedClimbPoints","Red predicted climb points");
//            SuperData
                    put("calculatedData.speedZScore","Speed Z Score");
                    put("calculatedData.agilityZScore","Agility Z Score");
//                put("EXAMPLE_DATA_POINT", "EXAMPLE_DATA_TITLE");
//                Look at past years' SpecificConstants for further formatting
            }
        };

        Map<String, String> initialDatasToGraphMap = new HashMap<String, String>() {
            {
//              put("DATA_POINT_TO_BE_GRAPHED","DATA_POINT_NEEDED_TO_GRAPH_PRIOR_DATA_POINT");


            }
        };

        Map<String, String> initialDataComparisonDatapointsTEAMSp75 = new HashMap<String, String>() {
            {
                put("p75avgOrangesScored","avg cargo scored");
                put("p75avgLemonsScored","avg panels scored");
                put("p75avgOrangeFouls","avg cargo foul out of field");
                put("p75lemonLoadSuccess","panel LS success");
                put("p75orangeCycleAll","avg cargo in RS/CS");
                put("p75orangeCycleL1","avg cargo in L1 RS");
                put("p75orangeCycleL2","avg cargo in L2 RS");
                put("p75orangeCycleL3","avg cargo in L3 RS");
                put("p75lemonCycleAll","avg panel in RS/CS");
                put("p75lemonCycleL1","avg panel in L1 RS");
                put("p75lemonCycleL2","avg panel in L2 RS");
                put("p75lemonCycleL3","avg panel in L3 RS");
                put("p75orangeSuccessAll","avg cargo all success");
                put("p75orangeSuccessDefended","avg cargo defended success");
                put("p75orangeSuccessUndefended","avg cargo undefended success");
                put("p75orangeSuccessL1","avg cargo success in L1 RS");
                put("p75orangeSuccessL2","avg cargo success in L2 RS");
                put("p75orangeSuccessL3","avg cargo success in L3 RS");
                put("p75lemonSuccessAll","avg panel all success");
                put("p75lemonSuccessL1","avg panel success in L1 RS");
                put("p75lemonSuccessL2","avg panel success in L2 RS");
                put("p75lemonSuccessL3","avg panel success in L3 RS");
                put("p75lemonSuccessFromSide","avg panel success from side");
                put("p75habLineSuccessL1","HAB line success start from L1");
                put("p75habLineSuccessL2","HAB line success start from L2");
                put("p75avgGoodDecisions","avg good decisions");
                put("p75avgBadDecisions","avg bad decisions");
                put("p75avgTimeIncap","avg time incap");
                put("p75avgTimeImpaired","avg time impaired");
                put("p75avgTimeClimbing","avg time climbing");
                put("p75percentIncap","% incap");
                put("p75percentImpaired","% impaired");
                put("p75percentNoShow","% no show");
                put("p75AvgLemonsSpilled","avg panels spilled");
            }
        };
        Map<String, String> initialDataComparisonDatapointsTEAMSlfm = new HashMap<String, String>() {
            {
                put("lfmAvgOrangesScored","avg cargo scored");
                put("lfmAvgLemonsScored","avg panel scored");
                put("lfmAvgOrangeFouls","avg cargo fouls");
                put("lfmLemonLoadSuccess","panel LS success");
                put("lfmOrangeCycleAll","avg cargo RS");
                put("lfmOrangeCycleL1","avg cargo L1 RS/CS");
                put("lfmOrangeCycleL2","avg cargo L2 RS");
                put("lfmOrangeCycleL3","avg cargo L3 RS");
                put("lfmLemonCycleAll","avg panel RS");
                put("lfmLemonCycleL1","avg panel L1 RS");
                put("lfmLemonCycleL2","avg panel L2 RS");
                put("lfmLemonCycleL3","avg panel L3 RS");
                put("lfmOrangeSuccessAll","cargo all success");
                put("lfmOrangeSuccessDefended","cargo defended success");
                put("lfmOrangeSuccessUndefended","cargo undefended success");
                put("lfmOrangeSuccessL1","avg panel L1 success");
                put("lfmOrangeSuccessL2","avg panel L2 success");
                put("lfmOrangeSuccessL3","avg panel L3 success");
                put("lfmLemonSuccessAll","avg panel all success");
                put("lfmLemonSuccessDefended","avg panel defended success");
                put("lfmLemonSuccessUndefended","avg panel undefended success");
                put("lfmLemonSuccessL1","avg panel L1 success");
                put("lfmLemonSuccessL2","avg panel L2 success");
                put("lfmLemonSuccessL3","avg panel L3 success");
                put("lfmLemonSuccessFromSide","avg far side RS or side CS placement");
                put("lfmHabLineSuccessL1","HAB line success start from L1");
                put("lfmHabLineSuccessL2","HAB line success start from L2");
                put("lfmAvgGoodDecisions","avg good decisions");
                put("lfmAvgBadDecisions","avg bad decisions");
                put("lfmAvgTimeIncap","avg time incap");
                put("lfmAvgTimeImpaired","avg time impaired");
                put("lfmAvgTimeClimbing","avg time climbing");
                put("lfmPercentIncap","% incap");
                put("lfmPercentImpaired","% impaired");
                put("lfmPercentNoShow","% no show");
                put("lfmAvgLemonsSpilled","avg panels spilled");
            }
        };
        Map<String, String> initialDataComparisonDatapointsTEAMSnormal = new HashMap<String, String>() {
            {
                put("actualRPs","total # RP");
                put("matchesPlayed","matches played");
                put("actualSeed","seed");
                put("predictedRPs","predicted PRs");
                put("predictedSeed","predicted seed");
                put("hasOrangeGroundIntake","has cargo ground intake");
                put("hasLemonGroundIntake","has panel ground intake");
                put("didPreloadLemon","preloaded lemon at least once");
                put("avgOrangesScored","avg cargo scored");
                put("avgLemonsScored","avg hatch scored");
                put("avgOrangeFouls","avg cargo fouls out of field");
                put("lemonSuccessFromSide","Far side RS or side CS placements");
                put("habLineSuccessL1","% HAB line from L1 success");
                put("habLineSuccessL2","% HAB line from L2 success");
                put("avgGoodDecisions","avg Bad Decisions");
                put("avgTimeIncap","avg incap time");
                put("avgTimeImpaired","avg impaired time");
                put("avgTimeClimbing","avg climbing time");
                put("percentIncap","% incap");
                put("percentImpaired","% impaired");
                put("percentNoShow","% no show");
                put("predictedDedicatedLemonCycles","predicted only panel cycles");
                put("predictedDedicatedOrangeCycles","predicted only cargo");
                put("predictedSoloPoints","predicted points by team (alone)");
                put("orangeAbility","cargo ability");
                put("lemonAbility","panel ability");
                put("firstPickAbility","first pick ability");
                put("secondPickAbility","second pick ability");
                put("calculatedData.avgLemonsSpilled","avg panels spilled");
            }
        };
        Map<String, String> initialDataComparisonDatapointsTEAMSsd = new HashMap<String, String>() {
            {
                put("sdAvgOrangesScored","avg cargo scored");
                put("sdAvgLemonsScored","avg hatch scored");
                put("sdAvgOrangeFouls","avg cargo fouls");
                put("sdLemonLoadSuccess","avg panel LS success");
                put("sdOrangeCycleAll","avg cargo in RS/CS");
                put("sdOrangeCycleL1","avg cargo in L1 RS/CS");
                put("sdOrangeCycleL2","avg cargo in L2 RS");
                put("sdOrangeCycleL3","avg cargo in L3 RS");
                put("sdLemonCycleAll","avg panel in RC/CS");
                put("sdLemonCycleL1","avg panel in L1 RC/CS");
                put("sdLemonCycleL2","avg panel in L2 RC/CS");
                put("sdLemonCycleL3","avg panel in L3 RC/CS");
                put("sdOrangeSuccessAll","avg cargo all success");
                put("sdOrangeSuccessDefended","avg cargo defended success");
                put("sdOrangeSuccessUndefended","avg cargo undefended success");
                put("sdOrangeSuccessL1","avg cargo success L1");
                put("sdOrangeSuccessL2","avg cargo success L2");
                put("sdOrangeSuccessL3","avg cargo success L3");
                put("sdLemonSuccessAll","avg panel all success");
                put("sdLemonSuccessDefended","avg panel defended success");
                put("sdLemonSuccessUndefended","avg panel undefended success");
                put("sdLemonSuccessL1","avg cargo success L1");
                put("sdLemonSuccessL2","avg cargo success L2");
                put("sdLemonSuccessL3","avg cargo success L3");
                put("sdLemonSuccessFromSide","avg far side RS or side CS placement");
                put("sdHabLineSuccessL1","HAB line success start from L1");
                put("sdHabLineSuccessL2","HAB line success start from L2");
                put("sdAvgGoodDecisions","avg good decisions");
                put("sdAvgBadDecisions","avg bad decisions");
                put("sdAvgTimeIncap","avg time incap");
                put("sdAvgTimeImpaired","avg time impaired");
                put("sdAvgTimeClimbing","avg time climbing");
                put("sdPercentIncap","% incap");
                put("sdPercentImpaired","% impaired");
                put("sdPercentNoShow","% no show");
                put("sdAvgLemonsSpilled","avg panels spilled");
            }
        };
        Map<String, String> initialDataComparisonDatapointsTIMD = new HashMap<String, String>() {
            {
                      put("orangesScored","# cargo scored");
                      put("lemonsScored","# panel scored");
                      put("orangeFouls","cargo fouls out of field");
                      put("lemonLoadSuccess","LS intake success");
                      put("orangeCycleAll","avg cargo in RS/CS");
                      put("orangeCycleL1","avg cargo L1 RS/CS");
                      put("orangeCycleL2","avg cargo L2 RS");
                      put("orangeCycleL3","avg cargo L3 RS");
                      put("lemonCycleAll","avg hatch in RS/CS");
                      put("lemonCycleL1","avg panel L1 RS/CS");
                      put("lemonCycleL2","avg panel L2 RS");
                      put("lemonCycleL3","avg panel L3 RS");
                      put("orangeSuccessAll","% cargo total success");
                      put("orangeSuccessDefended","% cargo defend success");
                      put("orangeSuccessUndefended","% cargo undefended success");
                      put("orangeSuccessL1","% cargo success L1 RS/CS");
                      put("orangeSuccessL2","% cargo success L2 RS");
                      put("orangeSuccessL3","% cargo success L3 RS");
                      put("lemonSuccessAll","% panel total success");
                      put("lemonSuccessDefended","% panel defend success");
                      put("lemonSuccessUndefended","% panel undefended success");
                      put("lemonSuccessL1","% panel success L1");
                      put("lemonSuccessL2","% panel success L2");
                      put("lemonSuccessL3","% panel success L3");
                      put("timeIncap","time incap (s)");
                      put("timeImpaired","time impaired (s)");
                      put("timeClimbing","time climbing (s)");
                      put("lemonsSpilled","panels spilled");
            }
        };
        List<String> categoryList = Arrays.asList(
                "Normal",
                "Last Four Matches",
                "Standard Deviation",
                "75th Percentile");

        KEYS_TO_TITLES = initialKeysToTitlesMap;
        DATA_TO_GRAPH = initialDatasToGraphMap;
        DATA_COMPARISON_TEAMS_SD = initialDataComparisonDatapointsTEAMSsd;
        DATA_COMPARISON_TEAMS_LFM = initialDataComparisonDatapointsTEAMSlfm;
        DATA_COMPARISON_TEAMS_NORMAL = initialDataComparisonDatapointsTEAMSnormal;
        DATA_COMPARISON_TEAMS_P75 = initialDataComparisonDatapointsTEAMSp75;
        DATA_COMPARISON_TIMD = initialDataComparisonDatapointsTIMD;
        CATEGORY_LIST = categoryList;
    }
}
