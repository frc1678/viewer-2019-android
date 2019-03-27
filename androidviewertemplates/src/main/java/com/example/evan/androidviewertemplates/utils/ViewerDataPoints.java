package com.example.evan.androidviewertemplates.utils;

import android.content.Intent;
import android.util.Log;

import com.example.evan.androidviewertemplates.firebase_classes.TeamTemplate;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.utils.Utils;

import org.jcodec.common.DictionaryCompressor;

public class ViewerDataPoints {
    // Performs calculations that are done on the viewer device

    private static Integer getRawMatchesUntilNextMatchForTeam(TeamTemplate team, Intent args) {
        Integer currentMatch = Utils.getLastMatchPlayed();
        for (Integer matchNumber : Utils.getMatchNumbersForTeamNumber(team.teamNumber)) {
            if (matchNumber > currentMatch) {
                Integer num = matchNumber - currentMatch;
                return num;
            }
        }
        return null;
    }

    public static String getMatchesUntilNextMatchForTeam(TeamTemplate team, Intent args) {
        Integer rawValue = getRawMatchesUntilNextMatchForTeam(team, args);
        if (rawValue == null) {
            return "none";
        }
        return rawValue.toString();
    }
}
