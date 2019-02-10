package com.example.evan.androidviewertemplates.utils;

import android.util.Log;

import com.example.evan.androidviewertools.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpecificConstants extends Constants {
    //todo
    public static Map<String, String> KEYS_TO_TITLES;
    public static Map<String, String> DATA_TO_GRAPH;

        //todo
    public static final String[] DRAWER_TITLES = {"Schedule", "Upcoming Matches", "Recent Matches", "Starred Matches", "Our Schedule", "Seeding", "First Pick", "Second Pick", "Super Data", "Live Picklist", "Function"};

    public static final String ORIGINAL_ROOT_FIREBASE_PATH = "https://dev3-2018.firebaseio.com/";

    public static String ROOT_FIREBASE_PATH = ORIGINAL_ROOT_FIREBASE_PATH;
    public static String MATCHES_PATH = ORIGINAL_ROOT_FIREBASE_PATH + "Matches";
    public static String TEAMS_PATH = ORIGINAL_ROOT_FIREBASE_PATH + "Teams";
    public static String TEAM_IN_MATCH_DATAS_PATH = ORIGINAL_ROOT_FIREBASE_PATH + "TeamInMatchDatas";

        static {
        Map<String, String> initialKeysToTitlesMap = new HashMap<String, String>() {
            {
                    put("matches", "Matches");
                    put("VIEWER.matchesUntilNextMatchForTeam", "Matches Until Next Match");
//                    put("calculatedData.numMatchesPlayed", "Num. Matches Played");
                    put("lfm", "Last Four Matches");
//              TIMDs
                    put("startingLevel","starting level");
                    put("crossedHabLine"," crossed HAB line");
                    put("startingLocation","starting location");
                    put("preload","preloaded game piece");
                    put("driverStation","driver station ");
                    put("isNoShow","did not show up to match");
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
//              Teams
                    put("pitWidth","width");
                    put("pitLength","length");
                    put("pitMinHeight","min. height");
                    put("pitMaxHeight","max height");
                    put("pitWeight","weight");
                    put("pitWheelDiameter","wheel diameter");
                    put("pitDrivetrain","drive train");
                    put("pitProgrammingLanguage","programming language");
                    put("pitClimbType","climb type");
                    put("pitHasCamera","has camera");
                    put("pitHasVision","has vision");
                    put("pitHasPid","has pid");
                    put("pitHasGyro","has gyro");
                    put("pitHasEncoders","has encoders");
                    put("pitCanBuddyStartLevel2","can start with another robot L2");
                    put("pitSandstormNavigationType","sandstorm navigation");
                    put("pitHasOrangeShooter","has cargo shooter");
                    put("pitSEALsNotes","SEALsNotes");
                    put("number","team #");
                    put("name","team name");
                    put("actualRPs","total # RP");
                    put("matchesPlayed","matches played");
                    put("actualSeed","seed");
                    put("predictedRPs","predicted PRs");
                    put("predictedSeed","predicted seed");
                    put("calculatedData.hasOrangeGroundIntake","has cargo ground intake");
                    put("calculatedData.hasLemonGroundIntake","has panel ground intake");
                    put("didPreloadLemon","preloaded lemon at least once");
                    put("calculatedData.avgOrangesScored","avg cargo scored");
                    put("calculatedData.avgLemonsScored","avg hatch scored");
                    put("calculatedData.avgOrangeFouls","avg cargo fouls out of field");
                    put("lemonSuccessFromSide","Far side RS or side CS placements");
                    put("calculatedData.habLineSuccessL1","% HAB line from L1 success");
                    put("calculatedData.habLineSuccessL2","% HAB line from L2 success");
                    put("calculatedData.avgGoodDecisions","avg good Decisions");
                    put("calculatedData.avgBadDecisions","avg bad decisions");
                    put("calculatedData.avgTimeIncap","avg incap time");
                    put("avgTimeImpaired","avg impaired time");
                    put("calculatedData.avgTimeClimbing","avg climbing time");
                    put("calculatedData.percentIncap","% incap");
                    put("calculatedData.percentImpaired","% impaired");
                    put("calculatedData.percentNoShow","% no show");
                    put("predictedDedicatedLemonCycles","predicted only panel cycles");
                    put("predictedDedicatedOrangeCycles","predicted only cargo");
                    put("calculatedData.predictedSoloPoints","predicted points by team (alone)");
                    put("orangeAbility","cargo ability");
                    put("lemonAbility","panel ability");
                    put("firstPickAbility","first pick ability");
                    put("secondPickAbility","second pick ability");
                    put("calculatedData.avgLemonsSpilled","avg panels spilled");
        //lfm
                    put("calculatedData.lfmAvgOrangesScored","avg cargo scored");
                    put("calculatedData.lfmAvgLemonsScored","avg panel scored");
                    put("calculatedData.lfmAvgOrangeFouls","avg cargo fouls");
                    put("calculatedData.lfmLemonLoadSuccess","panel LS success");
                    put("calculatedData.lfmOrangeCycleAll","avg cargo RS");
                    put("lfmOrangeCycleL1","avg cargo L1 RS/CS");
                    put("lfmOrangeCycleL2","avg cargo L2 RS");
                    put("lfmOrangeCycleL3","avg cargo L3 RS");
                    put("calculatedData.lfmLemonCycleAll","avg panel RS");
                    put("lfmLemonCycleL1","avg panel L1 RS");
                    put("lfmLemonCycleL2","avg panel L2 RS");
                    put("lfmLemonCycleL3","avg panel L3 RS");
                    put("lfmOrangeSuccessAll","cargo all success");
                    put("calculatedData.lfmOrangeSuccessDefended","cargo defended success");
                    put("lfmOrangeSuccessUndefended","cargo undefended success");
                    put("lfmOrangeSuccessL1","avg panel L1 success");
                    put("calculatedData.lfmOrangeSuccessL2","avg panel L2 success");
                    put("calculatedData.lfmOrangeSuccessL3","avg panel L3 success");
                    put("lfmLemonSuccessAll","avg panel all success");
                    put("calculatedData.lfmLemonSuccessDefended","avg panel defended success");
                    put("lfmLemonSuccessUndefended","avg panel undefended success");
                    put("lfmLemonSuccessL1","avg panel L1 success");
                    put("calculatedData.lfmLemonSuccessL2","avg panel L2 success");
                    put("calculatedData.lfmLemonSuccessL3","avg panel L3 success");
                    put("lfmLemonSuccessFromSide","avg far side RS or side CS placement");
                    put("calculatedData.lfmHabLineSuccessL1","HAB line success start from L1");
                    put("calculatedData.lfmHabLineSuccessL2","HAB line success start from L2");
                    put("calculatedData.lfmAvgGoodDecisions","avg good decisions");
                    put("calculatedData.lfmAvgBadDecisions","avg bad decisions");
                    put("calculatedData.lfmAvgTimeIncap","avg time incap");
                    put("calculatedData.lfmAvgTimeImpaired","avg time impaired");
                    put("calculatedData.lfmAvgTimeClimbing","avg time climbing");
                    put("calculatedData.lfmPercentIncap","% incap");
                    put("calculatedData.lfmPercentImpaired","% impaired");
                    put("calculatedData.lfmPercentNoShow","% no show");
                    put("lfmAvgLemonsSpilled","avg panels spilled");
            //sd
                    put("calculatedData.sdAvgOrangesScored","sd avg cargo scored");
                    put("calculatedData.sdAvgLemonsScored","sd avg hatch scored");
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
            //p75
                    put("p75avgOrangesScored","avg cargo scored");
                    put("calculatedData.p75avgLemonsScored","avg panels scored");
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
//              Matches
                    put("cargoShipPreload","cargo ship preload");
                    put("blueTeams","blue alliance teams");
                    put("redTeams","red alliance teams");
                    put("noShowTeams","no show teams");
                    put("blueActualScore","blue score");
                    put("redActualScore","red score");
                    put("blueFoulPoints","blue fouls points");
                    put("redFoulPoints","red fouls points");
                    put("blueActualRPs","blue total RP");
                    put("redActualRPs","red total RP");
                    put("blueDidRocketRP","blue rocket RP");
                    put("redDidRocketRP","red rocket RP");
                    put("blueDidClimbRP","blue climb RP");
                    put("redDidClimbRP","red climb RP");
                    put("blueChanceRocketRP","blue chance rocket RP");
                    put("redChanceRocketRP","red chance rocket RP");
                    put("blueChanceClimbRP","blue chance climb RP");
                    put("redChanceClimbRP","red chance climb RP");
                    put("bluePredictedScore","blue predicted score");
                    put("redPredictedScore","red predicted score");
                    put("blueChanceWin","blue chance win");
                    put("redChanceWin","red chance win");
                    put("bluePredictedClimbPoints","blue predicted climb points");
                    put("redPredictedClimbPoints","red predicted climb points");


            }
        };

        Map<String, String> initialDatasToGraphMap = new HashMap<String, String>() {
            {
//              put("DATA_POINT_TO_BE_GRAPHED","DATA_POINT_NEEDED_TO_GRAPH_PRIOR_DATA_POINT");


            }
        };

        KEYS_TO_TITLES = initialKeysToTitlesMap;
        DATA_TO_GRAPH = initialDatasToGraphMap;
    }
}
