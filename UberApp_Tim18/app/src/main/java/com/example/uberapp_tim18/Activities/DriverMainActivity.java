package com.example.uberapp_tim18.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.uberapp_tim18.R;
import com.google.android.material.navigation.NavigationView;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class DriverMainActivity extends AppCompatActivity {
    private StompClient stompClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        initializeWebSocketConnection();

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
                        Intent profile = new Intent(DriverMainActivity.this, DriverAccountActivity.class);
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
                    case R.id.logout:
                        Intent logout = new Intent(DriverMainActivity.this, UserLoginActivity.class);
                        startActivity(logout);
                        break;

                    case R.id.settings:
                        Intent setting = new Intent(DriverMainActivity.this, ReviewerPreferenceActivity.class);
                        setting.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(setting);
                        break;


                }
                return false;
            }
        });



    }
    @SuppressLint("CheckResult")
    void openGlobalSocket(){
        String id = getSharedPreferences("user_prefs",MODE_PRIVATE).getString("id","");
        stompClient.topic("/socket-topic/newRide/"+id).subscribe(
                topicMessage ->{
                    Log.i("SOCKET", topicMessage.getPayload());
                },
                throwable -> {
                    Log.e("SOCKET", "Error: " + throwable.getMessage());
                }
        );
    }

    void initializeWebSocketConnection(){
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP,"ws://192.168.0.26:8080/socket/websocket");
        stompClient.connect();
        openGlobalSocket();

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