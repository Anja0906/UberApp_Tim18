package com.example.uberapp_tim18.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.uberapp_tim18.R;

import java.util.ArrayList;
import java.util.List;

import DTO.PassengerIdEmailDTO;
import DTO.ReportItem;
import DTO.RideResponseDTO;

public class PassengerAdapter extends BaseAdapter {
    private Activity activity;

    private ArrayList<PassengerIdEmailDTO> passengers;

    public PassengerAdapter(Activity activity,ArrayList<PassengerIdEmailDTO> passengers) {
        this.activity = activity;
        this.passengers = passengers;
    }

    public ArrayList<PassengerIdEmailDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<PassengerIdEmailDTO> passengers) {
        this.passengers = passengers;
    }

    @Override
    public int getCount() {
       return this.passengers.size();
    }

    @Override
    public PassengerIdEmailDTO getItem(int i) {
        return this.passengers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        PassengerIdEmailDTO item = this.passengers.get(i);
        if(view==null)
            vi = activity.getLayoutInflater().inflate(R.layout.passenger_list_item,viewGroup,false);

        TextView passengerEmail = vi.findViewById(R.id.passenger_email1);
        passengerEmail.setText(item.getEmail());
        return  vi;
    }
}
