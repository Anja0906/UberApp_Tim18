package com.example.uberapp_tim18.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp_tim18.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import DTO.RideResponseDTO;
import model.Ride;
import tools.Mockup;

public class RideAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<RideResponseDTO> rides;

    public RideAdapter(Activity activity) {
        this.activity = activity;
        this.rides = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.rides.size();
    }

    @Override
    public RideResponseDTO getItem(int i) {
        return this.rides.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public ArrayList<RideResponseDTO> getRides() {
        return rides;
    }

    public void setRides(ArrayList<RideResponseDTO> rides) {
        this.rides = rides;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        RideResponseDTO ride = this.rides.get(i);

        if(view==null)
            vi = activity.getLayoutInflater().inflate(R.layout.activity_driver_ride_history, null);

        TextView ride_main = vi.findViewById(R.id.ride_main);
        TextView ride_description = vi.findViewById(R.id.ride_description);
        ImageView image = vi.findViewById(R.id.item_icon);

        String beginning = ride.getStartTime();
        String end = ride.getEndTime();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String ret = beginning + " - "  + end;
        ride_main.setText(ret);
        ride_description.setText(String.valueOf(ride.getTotalCost()));

        return  vi;
    }
}
