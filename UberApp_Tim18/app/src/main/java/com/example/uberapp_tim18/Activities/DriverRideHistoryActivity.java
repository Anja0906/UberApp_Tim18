package com.example.uberapp_tim18.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.uberapp_tim18.Adapters.RideAdapter;
import com.example.uberapp_tim18.R;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.Ride;
import model.Role;
import model.User;
import tools.HelperClasses;


public class DriverRideHistoryActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);

        Intent mainIntent = getIntent();

        lv = (ListView) findViewById(R.id.list_view);
        RideAdapter adapter = new RideAdapter(this);
        lv.setAdapter(adapter);
        //FragmentTransition.to(DriverRideHistoryFragment.newInstance(), this, false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(DriverRideHistoryActivity.this, RideDetailActivity.class);
            Ride ride = (Ride) parent.getItemAtPosition(position);
            byte[] rideBytes = new byte[0];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(ride);
                out.flush();
                rideBytes = bos.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException ex) {
                    // ignore close exception
                }
            }
            intent.putExtra("ride", rideBytes);

            intent.putExtra("user", mainIntent.getByteArrayExtra("user"));
//            System.out.println(getIntent().getByteArrayExtra("user").toString());
            startActivity(intent);
        }


        });
        DrawerLayout drawerLayout = findViewById(R.id.simple_list_view);

        findViewById(R.id.menu_toolbar_icon_simple_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {drawerLayout.openDrawer(GravityCompat.START);}
        });


        NavigationView navigationView = findViewById(R.id.navigation_view_simple_list);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.inbox:
                    Intent inbox = new Intent(DriverRideHistoryActivity.this, PassengerInboxActivity.class);
                    inbox.putExtra("user", getIntent().getByteArrayExtra("user"));
                    startActivity(inbox);
                    break;
                case R.id.account:
                    Intent profile = new Intent(DriverRideHistoryActivity.this, PassengerAccountActivity.class);
                    profile.putExtra("user", getIntent().getByteArrayExtra("user"));
                    startActivity(profile);
                    break;
                case R.id.ride_history:
                    Toast toast= Toast. makeText(getApplicationContext(),"Already on page",Toast. LENGTH_SHORT);
                    toast.show();
                    break;
                case R.id.home:
                    User user = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("user"));
                    Intent home = null;
                    if (user.getRole() == Role.PASSENGER) {
                        home = new Intent(DriverRideHistoryActivity.this, PassengerMainActivity.class);
                    }
                    if (user.getRole() == Role.DRIVER) {
                        home = new Intent(DriverRideHistoryActivity.this, DriverMainActivity.class);
                    }
                    home.putExtra("user", getIntent().getByteArrayExtra("user"));
                    startActivity(home);
                    break;
                case R.id.settings:
                    Intent settings = new Intent(DriverRideHistoryActivity.this, ReviewerPreferenceActivity.class);
                    settings.putExtra("user", getIntent().getByteArrayExtra("user"));
                    startActivity(settings);
                    break;
            }
            return false;
        }
        });
    }

}