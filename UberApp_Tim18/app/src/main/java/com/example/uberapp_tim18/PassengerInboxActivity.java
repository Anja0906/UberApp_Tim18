package com.example.uberapp_tim18;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.uberapp_tim18.Adapters.UserAdapter;
import com.google.android.material.navigation.NavigationView;

import model.Role;
import model.User;
import tools.HelperClasses;

/*
SENDER - > Current user
RECEIVER - > Person current user is talking to
???????????????????????????????????
 */


public class PassengerInboxActivity extends Activity {
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);

        Intent mainIntent = getIntent();

        byte[] userBytes = getIntent().getByteArrayExtra("user");
        User currentUser = (User)HelperClasses.Deserialize(userBytes);

        lv  = (ListView) findViewById(R.id.list_view);
        UserAdapter adapter = new UserAdapter(this, currentUser);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PassengerInboxActivity.this, ChatActivity.class);
                User user = (User) parent.getItemAtPosition(position);
                byte[] receiverBytes = HelperClasses.Serialize(user);
                intent.putExtra("receiver", receiverBytes);
                intent.putExtra("sender", mainIntent.getByteArrayExtra("user"));
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
                        Toast toast= Toast.makeText(getApplicationContext(),"Already on page",Toast. LENGTH_LONG);
                        toast.setMargin(50,50);
                        toast.show();
                        break;
                    case R.id.account:
                        Intent profile = new Intent(PassengerInboxActivity.this, PassengerAccountActivity.class);
                        profile.putExtra("user", mainIntent.getByteArrayExtra("user"));
                        startActivity(profile);
                        break;
                    case R.id.ride_history:
                        Intent ride = new Intent(PassengerInboxActivity.this, DriverRideHistoryActivity.class);
                        ride.putExtra("user", mainIntent.getByteArrayExtra("user"));
                        startActivity(ride);
                        break;
                    case R.id.home:
                        User user = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("user"));
                        Intent home = null;
                        if (user.getRole() == Role.PASSENGER) {
                            home = new Intent(PassengerInboxActivity.this, PassengerMainActivity.class);
                        }
                        if (user.getRole() == Role.DRIVER) {
                            home = new Intent(PassengerInboxActivity.this, DriverMainActivity.class);
                        }
                        home.putExtra("user", mainIntent.getByteArrayExtra("user"));
                        startActivity(home);
                        break;
                    case R.id.settings:
                        Intent settings = new Intent(PassengerInboxActivity.this, ReviewerPreferenceActivity.class);
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