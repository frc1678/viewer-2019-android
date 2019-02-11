package com.example.evan.androidviewertemplates.drawer_fragments.data_comparison;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class ValueFormatter implements IValueFormatter {

    private DecimalFormat mFormat;

    public ValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0.00");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        //if value is 0.04, return "0" for the value, else, return value unless it's actually 0. if it's 0 (null), then return ""
        if (value == 0.04f) {
            return "";
        } else if (value == 0.2f) {
            return "0";
        } else if (value != 0.0f) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            return df.format(value);
        }
        return "";
    }
}

