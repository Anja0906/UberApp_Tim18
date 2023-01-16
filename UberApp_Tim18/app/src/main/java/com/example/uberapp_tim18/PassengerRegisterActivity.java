package com.example.uberapp_tim18;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import retrofit.PassengerApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_register);

        PassengerApi passengerApi;
        RetrofitService retrofitService;

        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeComponents();

            }
        });
        //                String emailText = String.valueOf(username.getText());
//                String passwordText = String.valueOf(password.getText());
//
//                LoginDTO loginDTO = new LoginDTO(emailText, passwordText);
//
//                loginApi.save(loginDTO)
//                        .enqueue(new Callback<JWTResponse>() {
//                            @Override
//                            public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
//                                Toast.makeText(UserLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
//                                byte[] userBytes = HelperClasses.Serialize(response.body());
//                                if (response.body().getRoles().get(0).equals("ROLE_USER")) {
//                                    Intent intent = new Intent(UserLoginActivity.this, PassengerMainActivity.class);
//                                    intent.putExtra("user", userBytes);
//                                    startActivity(intent);
//                                } else {
//                                    Intent intent2 = new Intent(UserLoginActivity.this, DriverMainActivity.class);
//                                    intent2.putExtra("user", userBytes);
//                                    startActivity(intent2);
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<JWTResponse> call, Throwable t) {
//                                Toast.makeText(UserLoginActivity.this, "Login failed!!!", Toast.LENGTH_SHORT).show();
//                                Logger.getLogger(UserLoginActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
//                            }
//
//                        });
    }

    private void initializeComponents() {
        EditText name = (EditText) findViewById(R.id.name);
        EditText surname = (EditText) findViewById(R.id.surname);
        EditText address = (EditText) findViewById(R.id.address);
        EditText email = (EditText) findViewById(R.id.passenger_email);
        EditText password = (EditText) findViewById(R.id.passenger_password);
        EditText confirmPassword = (EditText) findViewById(R.id.passenger_password_confirm);
        EditText telephoneNumber = (EditText) findViewById(R.id.phone_num);
        Button register = findViewById(R.id.register);

        RetrofitService retrofitService = new RetrofitService();
        PassengerApi passengerApi = retrofitService.getRetrofit().create(PassengerApi.class);

        register.setOnClickListener(view -> {
            String nameTxt = String.valueOf(name.getText());
            String surnameTxt = String.valueOf(surname.getText());
            String addressTxt = String.valueOf(address.getText());
            String emailTxt = String.valueOf(email.getText());
            String passwordTxt = String.valueOf(password.getText());
            String confirmPasswordTxt = String.valueOf(confirmPassword.getText());
            String telephoneNumberTxt = String.valueOf(telephoneNumber.getText());

            if(passwordTxt.equals(confirmPasswordTxt)){
                PassengerPostDTO passengerPostDTO = new PassengerPostDTO(nameTxt, surnameTxt, 1, telephoneNumberTxt, emailTxt, addressTxt, passwordTxt);

                passengerApi.save(passengerPostDTO)
                        .enqueue(new Callback<PassengerResponseDTO>() {
                            @Override
                            public void onResponse(Call<PassengerResponseDTO> call, Response<PassengerResponseDTO> response) {
                                Toast.makeText(PassengerRegisterActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, response.body().getEmail());
//                                Intent intent = new Intent(PassengerRegisterActivity.this, UserLoginActivity.class);
//                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<PassengerResponseDTO> call, Throwable t) {
                                Toast.makeText(PassengerRegisterActivity.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                            }


                        });
            }


        });
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