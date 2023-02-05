package com.example.uberapp_tim18.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.fragments.DriverReports;
import com.example.uberapp_tim18.fragments.DriverStatistics;
import com.example.uberapp_tim18.fragments.FavoriteRides;
import com.example.uberapp_tim18.fragments.PassengerAccount;
import com.example.uberapp_tim18.fragments.PassengerStatistics;
import com.example.uberapp_tim18.fragments.ProfileStart;
import com.google.android.material.navigation.NavigationView;

import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.ReportDTO;
import DTO.UserDTO;
import retrofit.PassengerApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerAccountActivity extends AppCompatActivity {
    ImageView account;
    ImageView reports;
    ImageView statistics;
    ImageView favoriteRoutes;

    RetrofitService retrofitService;
    SharedPreferences sharedPreferences;
    String userId;
    String token;
    PassengerApi passengerApi;

    UserDTO user = new UserDTO();
    ReportDTO reportDTO = new ReportDTO();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_account);
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
        userId = sharedPreferences.getString("id", "");
        token = sharedPreferences.getString("jwt", "");
        retrofitService.onSavedUser(token);
        passengerApi = retrofitService.getRetrofit().create(PassengerApi.class);
    }

    private void initMenu(){
        account = findViewById(R.id.account_account_menu);
        reports = findViewById(R.id.reports_account_menu);
        statistics = findViewById(R.id.stats_account_menu);
        favoriteRoutes = findViewById(R.id.favorite_routes);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassengerAccount passengerAccount = new PassengerAccount(user);
                openFragment(passengerAccount);
            }
        });
        NavigationView navigationView = findViewById(R.id.navigation_view_passenger_account);
        navigationView.setItemIconTintList(null);
        String role =sharedPreferences.getString("role","");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.inbox:
                        Intent inbox = new Intent(PassengerAccountActivity.this, PassengerInboxActivity.class);
                        inbox.putExtra("user", getIntent().getByteArrayExtra("user"));
                        ;
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
                        if (role.equals("ROLE_PASSENGER")) {
                            home = new Intent(PassengerAccountActivity.this, PassengerMainActivity.class);
                        } else {
                            home = new Intent(PassengerAccountActivity.this, DriverMainActivity.class);
                        }
                        startActivity(home);
                        break;
                    case R.id.settings:
                        Intent settings = new Intent(PassengerAccountActivity.this, ReviewerPreferenceActivity.class);
                        settings.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(settings);
                        break;
                    case R.id.logout:
                        Intent logout = new Intent(PassengerAccountActivity.this, UserLoginActivity.class);
                        startActivity(logout);
                        break;
                }
                return false;
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
                PassengerStatistics passengerStatistics = new PassengerStatistics(reportDTO);
                openFragment(passengerStatistics);
            }
        });

        favoriteRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteRides favoriteRides = new FavoriteRides();
                openFragment(favoriteRides);
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
        this.getPassenger();
        this.getReports();
    }

    private void getPassenger(){
        System.out.println(userId);
        System.out.println(Integer.parseInt(userId));
        passengerApi.getUser(Integer.parseInt(userId))
                .enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        user = response.body();
                    }
                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }

    private void getReports(){
        passengerApi.getReports(Integer.parseInt(userId), "0", "100")
                .enqueue(new Callback<ReportDTO>() {
                    @Override
                    public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                        if(response.body()!=null){
                            reportDTO = response.body();
                        }
                        else{
                            System.out.println("There are no reports!");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReportDTO> call, Throwable t) {
                        Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }



}