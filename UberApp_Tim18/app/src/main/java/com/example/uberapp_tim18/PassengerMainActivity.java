package com.example.uberapp_tim18;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.uberapp_tim18.Adapters.SimpleFragmentPagerAdapter;
import com.google.android.material.navigation.NavigationView;

public class PassengerMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapFragment fragment = new MapFragment();
//        CreateRide fragment = new CreateRide();
        fragmentTransaction.add(R.id.fragment_container, fragment, "my_fragment_tag");
        fragmentTransaction.commit();

        DrawerLayout drawerLayout = findViewById(R.id.passenger_main);

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
                        Intent inbox = new Intent(PassengerMainActivity.this, PassengerInboxActivity.class);
                        inbox.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(inbox);
                        break;
                    case R.id.account:
                        Intent profile = new Intent(PassengerMainActivity.this, PassengerAccountActivity.class);
                        profile.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(profile);
                        break;
                    case R.id.ride_history:
                        Intent rideHistory = new Intent(PassengerMainActivity.this, DriverRideHistoryActivity.class);
                        rideHistory.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(rideHistory);
                        break;
                    case R.id.review_preferences:
                        Intent settings = new Intent(PassengerMainActivity.this, ReviewerPreferenceActivity.class);
                        startActivity(settings);
                        break;
                    case R.id.home:
                        Toast toast= Toast.makeText(getApplicationContext(),"Already on page",Toast. LENGTH_LONG);
                        toast.setMargin(50,50);
                        toast.show();
                        break;
                    case R.id.settings:
                        Intent setting = new Intent(PassengerMainActivity.this, ReviewerPreferenceActivity.class);
                        setting.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(setting);
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