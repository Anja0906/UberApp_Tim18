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

import DTO.ReportItem;

public class DriverReports extends Fragment {

    private ListView listView;
    private View rootView;

    public DriverReports() {
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
        items.add(new ReportItem("Item 1", "shgdjfhskdhfkshadjfhdsjf"));
        items.add(new ReportItem("Item 2", "adhahdiuaiduha"));
        items.add(new ReportItem("Item 3", "asjdbascshauda"));
        ReportsAdapter adapter = new ReportsAdapter(this.getContext(), items);
        listView.setAdapter(adapter);
        return rootView;
    }
}