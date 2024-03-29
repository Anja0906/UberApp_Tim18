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
import java.util.Set;
import DTO.DriverDTO;
import DTO.LocationSetDTO;
import DTO.PassengerIdEmailDTO;
import DTO.ResetPasswordDTO;
import DTO.RideResponseDTO;
import DTO.UserDTO;
import retrofit.RetrofitService;
import retrofit.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.HelperClasses;
import retrofit.DriverApi;

public class CurrentRideActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    EditText driverId;
    EditText driverName;
    EditText driverSurname;
    EditText phone;
    EditText email;
    EditText departure;
    EditText destination;

    RideResponseDTO ride, rideResponseDTO;
    byte[] rideBytes, receiverBytes, senderBytes;
    private RetrofitService retrofitService;
    private Set<PassengerIdEmailDTO> passengers;
    private Integer passengerId;
    DriverDTO driver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ride);
        this.retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        String role = sharedPreferences.getString("role", "ROLE_PASSENGER");

        String ride1 = sharedPreferences.getString("ride", null);

        Gson gson = new Gson();
        this.ride = gson.fromJson(ride1, RideResponseDTO.class);


        byte[] rideBytes = getIntent().getByteArrayExtra("ride");
        this.rideResponseDTO = (RideResponseDTO) HelperClasses.Deserialize(rideBytes);
        passengers = rideResponseDTO.getPassengers();
        for (PassengerIdEmailDTO passengerIdEmailDTO : passengers) {
            // ima samo jedan svakako...
            passengerId = passengerIdEmailDTO.getId();
        }

        System.out.println(this.rideResponseDTO.getDriver().getId());
        getDriver(this.rideResponseDTO.getDriver().getId());


        userApi.findById(rideResponseDTO.getDriver().getId()).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.body()!=null) {
                    UserDTO dto = response.body();
                    if (dto.getRoles().contains("ROLE_DRIVER") && role.equals("ROLE_DRIVER")) {
                        senderBytes = HelperClasses.Serialize(dto);
                    } else {
                        receiverBytes = HelperClasses.Serialize(dto);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred get user", t);
            }
        });

        userApi.findById(passengerId).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.body()!=null) {
                    UserDTO dto = response.body();
                    if (dto.getRoles().contains("ROLE_PASSENGER") && role.equals("ROLE_PASSENGER")) {
                        senderBytes = HelperClasses.Serialize(dto);
                    } else {
                        receiverBytes = HelperClasses.Serialize(dto);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred get user", t);
            }
        });

        Button inbox = findViewById(R.id.inbox_button);
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentRideActivity.this, ChatActivity.class);
                intent.putExtra("receiver", receiverBytes);
                intent.putExtra("sender", senderBytes);
                intent.putExtra("rideId", rideResponseDTO.getId());
                startActivity(intent);
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