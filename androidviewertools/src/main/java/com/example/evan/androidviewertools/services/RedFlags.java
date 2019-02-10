package com.example.evan.androidviewertools.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RedFlags {

    public static Map<String, String> RED_FLAG_DATAPOINTS;

    public static String[] RED_FLAG_DATAPOINT_NAMES = {
            "calculatedData.avgLemonsSpilled",
            "calculatedData.avgOrangeFouls",
            "calculatedData.avgBadDecisions",
            "calculatedData.percentNoShow",
            "calculatedData.percentImpaired",
            "pitProgrammingLanguage",

    };
    public static String[] RED_FLAG_DATAPOINTS_RED_VALUE = {

            //SUBSTRINGS:
            //"moreThanZero", "moreThanOne", "moreThanTwo", "moreThanThree", "moreThanFour", "LabVIEW", "

            "moreThanTwo",
            "moreThanZero",
            "moreThanFour",
            "moreThanZero",
            "moreThanZero",
            "LabVIEW"
    };
    static {
        Map<String, String> redFlagDatapoints = new HashMap<String, String>() {
            {
                put("calculatedData.avgLemonsSpilled", "avg panels spilled");
                put("calculatedData.avgOrangeFouls", "avg cargo fouls out of field");
                put("calculatedData.avgBadDecisions", "avg bad decisions");
                put("calculatedData.percentNoShow", "% no show");
                put("calculatedData.percentImpaired", "% impaired");
                put("pitProgrammingLanguage", "programming language");

            }
        };

        RED_FLAG_DATAPOINTS = redFlagDatapoints;
    }

}
