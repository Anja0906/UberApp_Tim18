package com.example.uberapp_tim18;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DriverMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        DrawerLayout drawerLayout = findViewById(R.id.driver_main);

        findViewById(R.id.menu_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inbox:
                        Intent inbox = new Intent(DriverMainActivity.this, PassengerInboxActivity.class);
                        inbox.putExtra("user", getIntent().getByteArrayExtra("user"));;
                        startActivity(inbox);
                        break;
                    case R.id.account:
                        Intent profile = new Intent(DriverMainActivity.this, PassengerAccountActivity.class);
                        profile.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(profile);
                        break;

                    case R.id.ride_history:
                        Intent history = new Intent(DriverMainActivity.this, DriverRideHistoryActivity.class);
                        history.putExtra("user", getIntent().getByteArrayExtra("user"));
//                        System.out.println(getIntent().getByteArrayExtra("user").toString());
                        startActivity(history);
                        break;

                    case R.id.review_preferences:
                        Intent settings = new Intent(DriverMainActivity.this, ReviewerPreferenceActivity.class);
                        startActivity(settings);
                        break;

                    case R.id.home:
                        Toast toast= Toast.makeText(getApplicationContext(),"Already on page",Toast. LENGTH_LONG);
                        toast.setMargin(50,50);
                        toast.show();
                        break;

                    case R.id.settings:
                        Intent settings = new Intent(DriverMainActivity.this, ReviewerPreferenceActivity.class);
                        settings.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(settings);
                        break;


                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}