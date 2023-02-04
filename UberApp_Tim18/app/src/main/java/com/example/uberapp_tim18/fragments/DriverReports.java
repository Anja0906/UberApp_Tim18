package com.example.uberapp_tim18.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uberapp_tim18.Adapters.ReportsAdapter;
import com.example.uberapp_tim18.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DTO.ReportDTO;
import DTO.ReportItem;

public class DriverReports extends Fragment {

    ListView listView;
    View rootView;
    ReportDTO report;

    public DriverReports(ReportDTO reportDTO) {
        this.report = reportDTO;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_driver_reports, container, false);
        listView = rootView.findViewById(R.id.list_view);
        List<ReportItem> items = new ArrayList<>();
        int sum=0;
        for (Map.Entry<String, Integer> entry : report.getRidesPerDay().entrySet()) {
            sum += entry.getValue();
        }
        String formattedValue = String.format("%.2f", report.getTotalKilometers());
        items.add(new ReportItem("Average money income", String.valueOf(report.getAverageMoney()), R.drawable.money_dolar));
        items.add(new ReportItem("Total kilometers", formattedValue, R.drawable.highway));
        items.add(new ReportItem("Total income", String.valueOf(report.getMoneySum()), R.drawable.money_bag));
        items.add(new ReportItem("Number of rides", String.valueOf(sum), R.drawable.car_new));
        ReportsAdapter adapter = new ReportsAdapter(this.getContext(), items);
        listView.setAdapter(adapter);
        return rootView;
    }
}