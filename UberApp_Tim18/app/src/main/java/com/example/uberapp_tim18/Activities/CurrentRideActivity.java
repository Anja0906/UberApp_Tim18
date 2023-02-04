package com.example.uberapp_tim18.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.dialog.PanicDialog;
import com.example.uberapp_tim18.fragments.MapFragment;
import com.google.gson.Gson;
import com.google.android.gms.maps.model.LatLng;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.DriverDTO;
import DTO.LocationSetDTO;
import DTO.RideResponseDTO;
import retrofit.DriverApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentRideActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    EditText driverId;
    EditText driverName;
    EditText driverSurname;
    EditText phone;
    EditText email;
    EditText departure;
    EditText destination;

    RideResponseDTO ride;
    DriverDTO driver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ride);
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String ride1 = sharedPreferences.getString("ride", null);
        Gson gson = new Gson();
        this.ride = gson.fromJson(ride1, RideResponseDTO.class);

        System.out.println(this.ride.getDriver().getId());
        getDriver(this.ride.getDriver().getId());



        Button inbox = findViewById(R.id.inbox_button);
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OVDE SE DOPISUJU DRIVER I PASSANGER
            }
        });


        Button button = findViewById(R.id.panic_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PanicDialog panicDialog = new PanicDialog(ride);
                panicDialog.show(getSupportFragmentManager(), "custom_dialog");
            }
        });

    }

    private void initRide(){
        LocationSetDTO locationSetDTO = ride.getLocations().iterator().next();
        driverId.setText(String.valueOf(ride.getDriver().getId()));
        driverName.setText(String.valueOf(driver.getName()));
        driverSurname.setText(String.valueOf(driver.getSurname()));
        phone.setText(String.valueOf(driver.getTelephoneNumber()));
        email.setText(String.valueOf(ride.getDriver().getEmail()));
        departure.setText(String.valueOf(locationSetDTO.getDeparture().getAddress()));
        destination.setText(String.valueOf(locationSetDTO.getDestination().getAddress()));
    }

    private void initGUI(){
        editor = getSharedPreferences("user_prefs", MODE_PRIVATE).edit();
        driverId = findViewById(R.id.driver_id);
        driverName = findViewById(R.id.driver_name_txt);
        driverSurname = findViewById(R.id.driver_surname_txt);
        phone = findViewById(R.id.phone_txt_view);
        email = findViewById(R.id.email_txt_view);
        departure = findViewById(R.id.departure_txt_view);
        destination = findViewById(R.id.destination_txt_view);
        initRide();
    }

    private void getDriver(int id){
        RetrofitService retrofitService = new RetrofitService();
        String token = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("jwt", "");
        retrofitService.onSavedUser(token);
        DriverApi driverApi = retrofitService.getRetrofit().create(DriverApi.class);
        driverApi.getDriver(id)
                .enqueue(new Callback<DriverDTO>() {
                    @Override
                    public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
                        System.out.println(response.body());
                        driver = response.body();
                        initGUI();
                        LocationSetDTO locationSetDTO = ride.getLocations().iterator().next();
                        LatLng dest = new LatLng(locationSetDTO.getDestination().getLatitude(),locationSetDTO.getDestination().getLongitude());
                        LatLng departure = new LatLng(locationSetDTO.getDeparture().getLatitude(),locationSetDTO.getDeparture().getLongitude());
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        MapFragment fragment = new MapFragment(departure,dest);
                        transaction.replace(R.id.fragment_current_ride, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    @Override
                    public void onFailure(Call<DriverDTO> call, Throwable t) {
                        Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }
}