package com.example.uberapp_tim18.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.fragments.EditProfile;
import com.example.uberapp_tim18.fragments.PassengerAccount;
import com.google.android.material.navigation.NavigationView;



import model.Role;
import model.User;
import tools.HelperClasses;

public class PassengerAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_account);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PassengerAccount passengerAccount = new PassengerAccount();
        fragmentTransaction.add(R.id.fragment_container, passengerAccount, "my_fragment_tag").commit();

        SharedPreferences preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        DrawerLayout drawerLayout = findViewById(R.id.passenger_account_activity);

        findViewById(R.id.menu_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = findViewById(R.id.navigation_view_passenger_account);
        navigationView.setItemIconTintList(null);
        String role =preferences.getString("role","");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inbox:
                        Intent inbox = new Intent(PassengerAccountActivity.this, PassengerInboxActivity.class);
                        inbox.putExtra("user", getIntent().getByteArrayExtra("user"));;
                        startActivity(inbox);
                        break;
                    case R.id.account:
                        Intent profile = new Intent(PassengerAccountActivity.this, PassengerAccountActivity.class);
                        profile.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(profile);
                        break;

                    case R.id.ride_history:
                        Intent history = new Intent(PassengerAccountActivity.this, DriverRideHistoryActivity.class);
                        history.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(history);
                        break;
                    case R.id.home:
                        Intent home;
                        if (role.equals("ROLE_PASSENGER") ) {
                            home = new Intent(PassengerAccountActivity.this, PassengerMainActivity.class);
                        }
                        else {
                            home = new Intent(PassengerAccountActivity.this, DriverMainActivity.class);
                        }
                        startActivity(home);
                        break;
                    case R.id.settings:
                        Intent settings = new Intent(PassengerAccountActivity.this, ReviewerPreferenceActivity.class);
                        settings.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(settings);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}