package com.example.uberapp_tim18.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uberapp_tim18.R;

import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.EmailDTO;
import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import model.User;
import retrofit.PassengerApi;
import retrofit.UserRetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmRegistration extends AppCompatActivity {

    Button confirmed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_registration);

        initComponents();
    }

    private void initComponents() {
        confirmed = findViewById(R.id.verified_button_view);
        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkActive();
            }
        });
    }

    private void checkActive(){
        UserRetrofitService userRetrofitService = new UserRetrofitService();
        PassengerApi passengerApi = userRetrofitService.getRetrofit().create(PassengerApi.class);
        SharedPreferences preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "");
        passengerApi.findByEmail(email)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body().isActive()){
                            Toast.makeText(ConfirmRegistration.this, "Your account is active!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ConfirmRegistration.this, UserLoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(ConfirmRegistration.this, "Your account is not active!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(ConfirmRegistration.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(ConfirmRegistration.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }


                });
    }
}