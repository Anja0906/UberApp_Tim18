package com.example.uberapp_tim18.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.example.uberapp_tim18.R;

import java.util.ArrayList;
import java.util.List;

public class DriverStatistics extends Fragment {
    View rootView;

    public DriverStatistics() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_driver_statistics, container, false);
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("John", 10000));
        data.add(new ValueDataEntry("Jake", 12000));
        data.add(new ValueDataEntry("Peter", 18000));

        pie.setTitle("Voznje");
        pie.setData(data);

        Drawable d = getResources().getDrawable(R.drawable.edit_text_background);
        AnyChartView anyChartView = (AnyChartView) rootView.findViewById(R.id.chart);
        anyChartView.setBackground(d);
        anyChartView.setChart(pie);
        return rootView;
    }
}