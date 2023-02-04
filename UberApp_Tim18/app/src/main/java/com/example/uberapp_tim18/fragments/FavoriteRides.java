package com.example.uberapp_tim18.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.uberapp_tim18.Adapters.FavoriteRoutesAdapter;
import com.example.uberapp_tim18.Adapters.ReportsAdapter;
import com.example.uberapp_tim18.R;

import java.util.ArrayList;
import java.util.List;

import DTO.FavoriteRoute;
import DTO.ReportItem;

public class FavoriteRides extends Fragment {
    View rootView;
    ListView listView;
    ImageView add;
    ImageView confirm;
    ImageView cancel;
    RelativeLayout list;
    RelativeLayout addFav;

    public FavoriteRides() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_favorite_rides, container, false);
        initList();
        initGUI();
        return rootView;
    }

    private void initGUI() {
        list = rootView.findViewById(R.id.list_layout);
        addFav = rootView.findViewById(R.id.form);
        add = rootView.findViewById(R.id.add_favorite_ride);
        confirm = rootView.findViewById(R.id.confirm_button);
        cancel = rootView.findViewById(R.id.cancel_edit_data);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setVisibility(View.GONE);
                addFav.setVisibility(View.VISIBLE);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setVisibility(View.VISIBLE);
                addFav.setVisibility(View.GONE);
                //dodati u listu svih
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setVisibility(View.VISIBLE);
                addFav.setVisibility(View.GONE);
                //samo odbaciti promene
            }
        });

    }

    private void initList(){
        List<FavoriteRoute> items = new ArrayList<>();
        items.add(new FavoriteRoute("huwdakusfhuahfu", "ajfjkhdaskjhfdahjasfj"));
        items.add(new FavoriteRoute("ksdhfahfa", "akshcduiascn"));
        items.add(new FavoriteRoute("", "ajfjkhdaskjhfd,zsnckjshjksaahjasfj"));
        items.add(new FavoriteRoute("jzcbjbsjakcsaljcscks", ""));
        items.add(new FavoriteRoute("huwdakusfhuscjjkshacahfu", "ajkshcxkhaskjchjsakcklsajdksa"));
        FavoriteRoutesAdapter adapter = new FavoriteRoutesAdapter(this.getContext(), items);
        listView = rootView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}