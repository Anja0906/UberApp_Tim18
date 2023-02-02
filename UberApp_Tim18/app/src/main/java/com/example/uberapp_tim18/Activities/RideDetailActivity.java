package com.example.uberapp_tim18.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.uberapp_tim18.R;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import DTO.RideResponseDTO;
import model.Role;
import model.User;
import tools.HelperClasses;

public class RideDetailActivity extends Activity {

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

        System.out.println(ride);
        TextView start = findViewById(R.id.beginning_txt_view);
        start.setText(ride.getStartTime());

        TextView end = findViewById(R.id.end_txt_view);
        end.setText(ride.getEndTime());

        TextView price = findViewById(R.id.price_txt_view);
        price.setText(Double.toString(ride.getTotalCost()));
        TextView duration = findViewById(R.id.duration_txt_view);
        duration.setText(Integer.toString(ride.getEstimatedTimeInMinutes()));
        TextView panic = findViewById(R.id.panic_ride_txt_view);
        TextView baby = findViewById(R.id.baby_ride_txt_view);
        if (ride.isBabyTransport()) {
            baby.setText("+");
        } else {
            baby.setText("-");
        }
        TextView pet = findViewById(R.id.pet_ride_txt_view);
        if (ride.isPetTransport()) {
            pet.setText("+");
        } else {
            pet.setText("-");
        }


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
                        if (user.getRoles().get(1) == Role.PASSENGER) {
                            home = new Intent(RideDetailActivity.this, PassengerMainActivity.class);
                        }
                        if (user.getRoles().get(1) == Role.DRIVER) {
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