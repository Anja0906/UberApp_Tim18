package com.example.uberapp_tim18.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uberapp_tim18.Adapters.RideAdapter;
import com.example.uberapp_tim18.R;
import com.google.android.material.navigation.NavigationView;

import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.RideResponseDTO;
import DTO.RideRetDTOMap;
import retrofit.DriverApi;
import retrofit.PassengerApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.HelperClasses;

public class PassengerRideHistoryActivity extends AppCompatActivity {
    private ListView lv;
    private NavigationView navigationView;
    private RideAdapter adapter;
    private DrawerLayout drawerLayout;
    private RetrofitService retrofitService;
    private SharedPreferences preferences;

    private String token;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);
        this.initGui();
        PassengerApi passengerApi = retrofitService.getRetrofit().create(PassengerApi.class);
        passengerApi.getRides(this.id).enqueue(new Callback<RideRetDTOMap>() {
            @Override
            public void onResponse(Call<RideRetDTOMap> call, Response<RideRetDTOMap> response) {
                if(response.body()==null){
                    Toast toast= Toast.makeText(getApplicationContext(),"Ride history is empty",Toast. LENGTH_SHORT);
                    toast.show();
                }else{
                    adapter.setRides(response.body().getResults());
                    lv.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<RideRetDTOMap> call, Throwable t) {
                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PassengerRideHistoryActivity.this, RideDetailActivity.class);
                RideResponseDTO rideResponseDTO = (RideResponseDTO) parent.getItemAtPosition(position);
                byte[] receiverBytes = HelperClasses.Serialize(rideResponseDTO);
                intent.putExtra("ride", receiverBytes);
                startActivity(intent);
            }
        });

        findViewById(R.id.menu_toolbar_icon_simple_list).setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        String role =preferences.getString("role","");
        this.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.inbox:
                    Intent inbox = new Intent(PassengerRideHistoryActivity.this, PassengerInboxActivity.class);
                    inbox.putExtra("user", getIntent().getByteArrayExtra("user"));
                    startActivity(inbox);
                    break;
                case R.id.account:
                    Intent profile = new Intent(PassengerRideHistoryActivity.this, PassengerAccountActivity.class);
                    profile.putExtra("user", getIntent().getByteArrayExtra("user"));
                    startActivity(profile);
                    break;
                case R.id.logout:
                    Intent logout = new Intent(PassengerRideHistoryActivity.this, UserLoginActivity.class);
                    startActivity(logout);
                    break;
                case R.id.ride_history:
                    Toast toast= Toast. makeText(getApplicationContext(),"Already on page",Toast. LENGTH_SHORT);
                    toast.show();
                    break;
                case R.id.home:
                    Intent home;
                    if (role.equals("ROLE_PASSENGER") ) {
                        home = new Intent(PassengerRideHistoryActivity.this, PassengerMainActivity.class);
                    }
                    else {
                        home = new Intent(PassengerRideHistoryActivity.this, DriverMainActivity.class);
                    }
                    startActivity(home);
                    break;
                case R.id.settings:
                    Intent settings = new Intent(PassengerRideHistoryActivity.this, ReviewerPreferenceActivity.class);
                    settings.putExtra("user", getIntent().getByteArrayExtra("user"));
                    startActivity(settings);
                    break;
            }
            return false;
        });
    }
    public void initGui(){
        this.lv = findViewById(R.id.list_view);
        this.adapter = new RideAdapter(this);
        lv.setAdapter(adapter);
        this.navigationView = findViewById(R.id.navigation_view_simple_list);
        this.navigationView.setItemIconTintList(null);
        this.drawerLayout = findViewById(R.id.simple_list_view);
        this.retrofitService = new RetrofitService();
        this.preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        this.token = preferences.getString("jwt", "");
        this.id = Integer.parseInt(preferences.getString("id", ""));
        retrofitService.onSavedUser(token);
    }
}