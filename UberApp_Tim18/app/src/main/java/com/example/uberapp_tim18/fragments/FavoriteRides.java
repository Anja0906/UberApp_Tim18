package com.example.uberapp_tim18.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uberapp_tim18.Adapters.FavoriteRoutesAdapter;
import com.example.uberapp_tim18.Adapters.ReportsAdapter;
import com.example.uberapp_tim18.R;

import java.util.ArrayList;
import java.util.List;

import DTO.FavoriteRoute;
import DTO.ReportItem;

public class FavoriteRides extends Fragment {

    public FavoriteRides() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite_rides, container, false);
        List<FavoriteRoute> items = new ArrayList<>();
        items.add(new FavoriteRoute("huwdakusfhuahfu", "ajfjkhdaskjhfdahjasfj"));
        items.add(new FavoriteRoute("ksdhfahfa", "akshcduiascn"));
        items.add(new FavoriteRoute("", "ajfjkhdaskjhfd,zsnckjshjksaahjasfj"));
        items.add(new FavoriteRoute("jzcbjbsjakcsaljcscks", ""));
        items.add(new FavoriteRoute("huwdakusfhuscjjkshacahfu", "ajkshcxkhaskjchjsakcklsajdksa"));
        FavoriteRoutesAdapter adapter = new FavoriteRoutesAdapter(this.getContext(), items);
        ListView listView = rootView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        return rootView;
    }
}