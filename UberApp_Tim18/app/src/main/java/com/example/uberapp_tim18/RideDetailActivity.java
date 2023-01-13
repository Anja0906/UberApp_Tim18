package com.example.uberapp_tim18;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Ride;
import model.Role;
import model.User;
import tools.HelperClasses;

public class RideDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);
        Intent intent = getIntent();
//        System.out.println(getIntent().getByteArrayExtra("user").toString());
        byte[] rideBytes = intent.getByteArrayExtra("ride");
        ByteArrayInputStream bis = new ByteArrayInputStream(rideBytes);
        ObjectInput in = null;
        Ride ride = null;
        try {
            in = new ObjectInputStream(bis);
            ride = (Ride) in.readObject();
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

        //System.out.println(ride);
        TextView beginning = (TextView)findViewById(R.id.beginning_txt_view);
        LocalDateTime beg = ride.getBeginning();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        beginning.setText(beg.format(formatter));

        TextView end = (TextView)findViewById(R.id.end_txt_view);
        LocalDateTime endf = ride.getBeginning();
        end.setText(endf.format(formatter));

        TextView price = (TextView)findViewById(R.id.price_txt_view);
        price.setText(Double.toString(ride.getPrice()));
        TextView duration = (TextView)findViewById(R.id.duration_txt_view);
        duration.setText(Integer.toString(ride.getDuration()));
        TextView panic = (TextView)findViewById(R.id.panic_ride_txt_view);
        if (ride.isPanic()) {
            panic.setText("+");
        } else {
            panic.setText("-");
        }
        TextView baby = (TextView)findViewById(R.id.baby_ride_txt_view);
        if (ride.isBabyInCar()) {
            baby.setText("+");
        } else {
            baby.setText("-");
        }
        TextView pet = (TextView)findViewById(R.id.pet_ride_txt_view);
        if (ride.isPetInCar()) {
            pet.setText("+");
        } else {
            pet.setText("-");
        }
        TextView split = (TextView)findViewById(R.id.split_fair_ride_txt_view);
        if (ride.isSplitFair()) {
            split.setText("+");
        } else {
            split.setText("-");
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
                        if (user.getRole() == Role.PASSENGER) {
                            home = new Intent(RideDetailActivity.this, PassengerMainActivity.class);
                        }
                        if (user.getRole() == Role.DRIVER) {
                            home = new Intent(RideDetailActivity.this, DriverMainActivity.class);
                        }
                        home.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(home);
                        break;
                }
                return false;
            }
        });

    }
}