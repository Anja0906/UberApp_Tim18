package com.example.uberapp_tim18.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.fragments.ForgotPasswordCode;

import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.EmailDTO;
import DTO.RideRetDTOMap;
import model.User;
import retrofit.DriverApi;
import retrofit.PassengerApi;
import retrofit.RetrofitService;
import retrofit.UserRetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        UserRetrofitService retrofitService = new UserRetrofitService();
        setUserInterface(retrofitService);
    }

    private void setUserInterface(UserRetrofitService retrofitService){
        EditText email = findViewById(R.id.email);
        Button button = findViewById(R.id.verify);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailDTO = email.getText().toString();
                PassengerApi passengerApi = retrofitService.getRetrofit().create(PassengerApi.class);
                passengerApi.findByEmail(emailDTO).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.body() == null){
                            Toast toast= Toast.makeText(getApplicationContext(),"User with this email doesnt exist",Toast. LENGTH_SHORT);
                            toast.show();
                        }else{
                            user = response.body();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            ForgotPasswordCode fragment = new ForgotPasswordCode(user);
                            transaction.replace(R.id.fragment_container_forgot_pass, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Logger.getLogger(ForgotPasswordActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });

            }
        });
    }
}