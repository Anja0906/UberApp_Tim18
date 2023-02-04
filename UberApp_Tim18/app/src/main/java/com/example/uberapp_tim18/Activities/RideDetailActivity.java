package com.example.uberapp_tim18.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.L;
import com.example.uberapp_tim18.Adapters.PassengerAdapter;
import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.fragments.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Set;

import DTO.LocationSetDTO;
import DTO.PassengerIdEmailDTO;
import DTO.RideResponseDTO;
import model.Location;
import model.Passenger;
import model.Role;
import model.User;
import tools.HelperClasses;

public class RideDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        Intent intent = getIntent();
        byte[] rideBytes = intent.getByteArrayExtra("ride");
        ByteArrayInputStream bis = new ByteArrayInputStream(rideBytes);
        ObjectInput in = null;
        RideResponseDTO ride = null;
        try {
            in = new ObjectInputStream(bis);
            ride = (RideResponseDTO) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Set<LocationSetDTO> location = ride.getLocations();
        LocationSetDTO l = location.iterator().next();
        LatLng departure = new LatLng(l.getDeparture().getLatitude(),l.getDeparture().getLongitude());
        LatLng destination = new LatLng(l.getDestination().getLatitude(),l.getDestination().getLongitude());
        MapFragment fragment = new MapFragment(departure,destination);
        transaction.replace(R.id.fragment_ride_detail, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        System.out.println(ride);
        EditText start = findViewById(R.id.start_txt_view);
        start.setText(ride.getStartTime());

        EditText end = findViewById(R.id.end_txt_view);
        end.setText(ride.getEndTime());

        EditText price = findViewById(R.id.price_txt_view);
        price.setText(Double.toString(ride.getTotalCost()));
        EditText duration = findViewById(R.id.duration_txt_view);
        duration.setText(Integer.toString(ride.getEstimatedTimeInMinutes()));

        EditText passenger = findViewById(R.id.pass_txt_view);

        System.out.println(ride.getPassengers().size());
        String r = String.valueOf(ride.getPassengers().size());
        passenger.setText(r);
        ArrayList<PassengerIdEmailDTO> list = new ArrayList<>(ride.getPassengers());

        ListView listView = findViewById(R.id.list_view1);
        PassengerAdapter passengerAdapter = new PassengerAdapter(this,list);
        listView.setAdapter(passengerAdapter);




        start.setEnabled(false);
        end.setEnabled(false);
        price.setEnabled(false);
        duration.setEnabled(false);


        DrawerLayout drawerLayout = findViewById(R.id.ride_detail);

        findViewById(R.id.menu_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = findViewById(R.id.ride_detail_navigation);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inbox:
                        Intent inbox = new Intent(RideDetailActivity.this, PassengerInboxActivity.class);
                        inbox.putExtra("user", getIntent().getByteArrayExtra("user"));;
                        startActivity(inbox);
                        break;
                    case R.id.account:
                        Intent profile = new Intent(RideDetailActivity.this, PassengerAccountActivity.class);
                        profile.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(profile);
                        break;
                    case R.id.ride_history:
                        Intent history = new Intent(RideDetailActivity.this, DriverRideHistoryActivity.class);
                        history.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(history);
                        break;
                    case R.id.home:
                        User user = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("user"));
                        Intent home = null;
                        if (user.getRoles().get(1) == "ROLE_PASSANGER") {
                            home = new Intent(RideDetailActivity.this, PassengerMainActivity.class);
                        }else{
                            home = new Intent(RideDetailActivity.this, DriverMainActivity.class);
                        }

                        home.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(home);
                        break;
                    case R.id.settings:
                        Intent settings = new Intent(RideDetailActivity.this, ReviewerPreferenceActivity.class);
                        settings.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(settings);
                        break;
                }
                return false;
            }
        });

    }
}