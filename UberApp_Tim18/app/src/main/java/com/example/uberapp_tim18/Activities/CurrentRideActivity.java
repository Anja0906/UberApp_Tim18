package com.example.uberapp_tim18.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.dialog.PanicDialog;
import com.example.uberapp_tim18.fragments.MapFragment;
import com.google.gson.Gson;

import DTO.LocationSetDTO;
import DTO.RideResponseDTO;

public class CurrentRideActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    EditText driverId;
    EditText driverName;
    EditText driverSurname;
    EditText phone;
    EditText email;
    EditText departure;
    EditText destination;

    RideResponseDTO ride;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ride);
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String ride = sharedPreferences.getString("ride", null);

        Gson gson = new Gson();
        this.ride = gson.fromJson(ride, RideResponseDTO.class);

        initGUI();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MapFragment fragment = new MapFragment(0);
        transaction.replace(R.id.fragment_current_ride, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        Button inbox = findViewById(R.id.inbox_button);
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OVDE SE DOPISUJU DRIVER I PASSANGER
            }
        });


        Button button = findViewById(R.id.panic_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PanicDialog panicDialog = new PanicDialog();
                panicDialog.show(getSupportFragmentManager(), "custom_dialog");
            }
        });

    }

    private void initRide(){
        LocationSetDTO locationSetDTO = ride.getLocations().iterator().next();
        driverId.setText(String.valueOf(ride.getDriver()));
        departure.setText(String.valueOf(locationSetDTO.getDeparture().getAddress()));
        destination.setText(String.valueOf(locationSetDTO.getDestination().getAddress()));
    }

    private void initGUI(){
        editor = getSharedPreferences("user_prefs", MODE_PRIVATE).edit();
        driverId = findViewById(R.id.driver_id);
        driverName = findViewById(R.id.driver_name_txt);
        driverSurname = findViewById(R.id.driver_surname_txt);
        phone = findViewById(R.id.phone_txt_view);
        email = findViewById(R.id.email_txt_view);
        departure = findViewById(R.id.departure_txt_view);
        destination = findViewById(R.id.destination_txt_view);
        initRide();
    }
}