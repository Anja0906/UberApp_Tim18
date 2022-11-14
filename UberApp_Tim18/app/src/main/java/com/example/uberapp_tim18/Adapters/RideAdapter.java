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

import model.Ride;
import tools.Mockup;

public class RideAdapter extends BaseAdapter {

    private Activity activity;

    public RideAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Mockup.getRides().size();
    }

    @Override
    public Ride getItem(int i) {
        return Mockup.getRides().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        Ride ride = Mockup.getRides().get(i);

        if(view==null)
            vi = activity.getLayoutInflater().inflate(R.layout.activity_driver_ride_history, null);

        TextView ride_main = (TextView)vi.findViewById(R.id.ride_main);
        TextView ride_description = (TextView)vi.findViewById(R.id.ride_description);
        ImageView image = (ImageView)vi.findViewById(R.id.item_icon);

        LocalDateTime beginning = ride.getBeginning();
        LocalDateTime end = ride.getEnd();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String ret = beginning.format(formatter) + " - "  + end.format(formatter);
        ride_main.setText(ret);
        ride_description.setText(Double.toString(ride.getPrice()));

        return  vi;
    }
}
