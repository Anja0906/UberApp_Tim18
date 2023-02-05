package com.example.uberapp_tim18.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.fragments.AboutVehicle;
import com.example.uberapp_tim18.fragments.DriverAccount;
import com.example.uberapp_tim18.fragments.DriverReports;
import com.example.uberapp_tim18.fragments.DriverStatistics;
import com.example.uberapp_tim18.fragments.MapFragment;
import com.example.uberapp_tim18.fragments.PassengerAccount;
import com.google.android.material.navigation.NavigationView;
import com.example.uberapp_tim18.fragments.ProfileStart;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.DriverDTO;
import DTO.ReportDTO;
import DTO.RideResponseDTO;
import DTO.VehicleDTO;
import retrofit.DriverApi;
import retrofit.RetrofitService;
import retrofit.RideApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAccountActivity extends AppCompatActivity {
    ImageView account;
    ImageView reports;
    ImageView statistics;
    ImageView vehicle;

    RetrofitService retrofitService;
    SharedPreferences sharedPreferences;
    String driverId;
    String token;
    DriverApi driverApi;

    DriverDTO driver = new DriverDTO();
    VehicleDTO vehicleDTO = new VehicleDTO();
    ReportDTO reportDTO = new ReportDTO();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account);
        initServices();
        initObjects();
        initMenu();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ProfileStart profileStart = new ProfileStart();
        transaction.add(R.id.fragment_container, profileStart);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initServices(){
        retrofitService = new RetrofitService();
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        driverId = sharedPreferences.getString("id", "");
        token = sharedPreferences.getString("jwt", "");
        retrofitService.onSavedUser(token);
        driverApi = retrofitService.getRetrofit().create(DriverApi.class);
    }

    private void navBarClick(){
        DrawerLayout drawerLayout = findViewById(R.id.driver_account_activity);
        findViewById(R.id.menu_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    
    private void navBar(){
        NavigationView navigationView = findViewById(R.id.navigation_view_passenger_account);
        navigationView.setItemIconTintList(null);
        SharedPreferences preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String role =preferences.getString("role","");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inbox:
                        Intent inbox = new Intent(DriverAccountActivity.this, PassengerInboxActivity.class);
                        inbox.putExtra("user", getIntent().getByteArrayExtra("user"));;
                        startActivity(inbox);
                        break;
                    case R.id.account:
                        Intent profile = new Intent(DriverAccountActivity.this, DriverAccountActivity.class);
                        profile.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(profile);
                        break;
                    case R.id.logout:
                        Intent logout = new Intent(DriverAccountActivity.this, UserLoginActivity.class);
                        startActivity(logout);
                        break;

                    case R.id.ride_history:
                        Intent history = new Intent(DriverAccountActivity.this, DriverRideHistoryActivity.class);
                        history.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(history);
                        break;
                    case R.id.home:
                        Intent home;
                        if (role.equals("ROLE_PASSENGER") ) {
                            home = new Intent(DriverAccountActivity.this, PassengerMainActivity.class);
                        }
                        else {
                            home = new Intent(DriverAccountActivity.this, DriverMainActivity.class);
                        }
                        startActivity(home);
                        break;
                    case R.id.settings:
                        Intent settings = new Intent(DriverAccountActivity.this, ReviewerPreferenceActivity.class);
                        settings.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(settings);
                        break;
                }
                return false;
            }
        });

    }


    private void initMenu(){
        account = findViewById(R.id.account_account_menu);
        reports = findViewById(R.id.reports_account_menu);
        statistics = findViewById(R.id.stats_account_menu);
        vehicle = findViewById(R.id.about_vehicle);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverAccount driverAccount = new DriverAccount(driver);
                openFragment(driverAccount);
            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverReports driverReports = new DriverReports(reportDTO);
                openFragment(driverReports);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverStatistics driverStatistics = new DriverStatistics(reportDTO);
                openFragment(driverStatistics);
            }
        });
        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutVehicle aboutVehicle = new AboutVehicle(vehicleDTO);
                openFragment(aboutVehicle);
            }
        });
    }

    private void openFragment(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void initObjects(){
        this.getDriver();
        this.getVehicle();
        this.getReports();
    }

    private void getDriver(){
        System.out.println(driverId);
        System.out.println(Integer.parseInt(driverId));
        driverApi.getDriver(Integer.parseInt(driverId))
                .enqueue(new Callback<DriverDTO>() {
            @Override
            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
                driver = response.body();
            }
            @Override
            public void onFailure(Call<DriverDTO> call, Throwable t) {
                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }

    private void getVehicle(){
        driverApi.getVehicle(Integer.parseInt(driverId))
                .enqueue(new Callback<VehicleDTO>() {
                    @Override
                    public void onResponse(Call<VehicleDTO> call, Response<VehicleDTO> response) {
                        vehicleDTO = response.body();
                    }
                    @Override
                    public void onFailure(Call<VehicleDTO> call, Throwable t) {
                        Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }

    private void getReports(){
        driverApi.getReports(Integer.parseInt(driverId))
                .enqueue(new Callback<ReportDTO>() {
                    @Override
                    public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                        reportDTO = response.body();
                    }
                    @Override
                    public void onFailure(Call<ReportDTO> call, Throwable t) {
                        Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }


    
}