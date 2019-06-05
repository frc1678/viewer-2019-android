package com.example.evan.androidviewertools.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RedFlags {

    public static Map<String, String> RED_FLAG_DATAPOINTS;

    public static String[] RED_FLAG_DATAPOINT_NAMES = {
            "calculatedData.avgPinningFouls",
            "calculatedData.avgCargoFouls",
            "calculatedData.percentNoShow",

    };
    public static String[] RED_FLAG_DATAPOINTS_RED_VALUE = {

            //SUBSTRINGS:
            //"moreThanZero", "moreThanOne", "moreThanTwo", "moreThanThree", "moreThanFour",

            "moreThanTwo",
            "moreThanZero",
            "moreThanFour",
    };

    static {
        Map<String, String> redFlagDatapoints = new HashMap<String, String>() {
            {
                put("calculatedData.avgPinningFouls", "avg panels spilled");
                put("calculatedData.percentNoShow", "% no show");

            }
        };

        RED_FLAG_DATAPOINTS = redFlagDatapoints;
    }

}
