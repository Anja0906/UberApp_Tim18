package com.example.uberapp_tim18;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.JWTResponse;
import DTO.LoginDTO;
import model.User;
import retrofit.LoginApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.HelperClasses;
import tools.Mockup;

public class UserLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login2);

        ArrayList<User> users = Mockup.getUsers();

        Button button = findViewById(R.id.login);
        RetrofitService retrofitService = new RetrofitService();
        LoginApi loginApi = retrofitService.getRetrofit().create(LoginApi.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.Email);
                EditText password = (EditText) findViewById(R.id.Password);
//                boolean found = false;
//                User foundUser = null;
//                for (User user : users) {
//                    if (username.getText().toString().equals(user.getEmail()) && password.getText().toString().equals(user.getPassword())) {
//                        found = true;
//                        foundUser = user;
//                        break;
//                    }
//                }
//                if (!found) {
//                    Toast.makeText(UserLoginActivity.this, "Incorrect data!", Toast.LENGTH_SHORT).show();
//                } else {
//                    byte[] userBytes = HelperClasses.Serialize(foundUser);
//                    if (foundUser.getRole() == Role.PASSENGER) {
//                        Intent intent = new Intent(UserLoginActivity.this, PassengerMainActivity.class);
//                        intent.putExtra("user", userBytes);
//                        startActivity(intent);
//                    } else {
//                        Intent intent2 = new Intent(UserLoginActivity.this, DriverMainActivity.class);
//                        intent2.putExtra("user", userBytes);
//                        startActivity(intent2);
//                    }
//                }

                String emailText = String.valueOf(username.getText());
                String passwordText = String.valueOf(password.getText());

                LoginDTO loginDTO = new LoginDTO(emailText, passwordText);

                loginApi.save(loginDTO)
                        .enqueue(new Callback<JWTResponse>() {
                            @Override
                            public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
                                Toast.makeText(UserLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                byte[] userBytes = HelperClasses.Serialize(response.body());
                                if (response.body().getRoles().get(0).equals("ROLE_USER")) {
                                    Intent intent = new Intent(UserLoginActivity.this, PassengerMainActivity.class);
                                    intent.putExtra("user", userBytes);
                                    startActivity(intent);
                                } else {
                                    Intent intent2 = new Intent(UserLoginActivity.this, DriverMainActivity.class);
                                    intent2.putExtra("user", userBytes);
                                    startActivity(intent2);
                                }
                            }

                            @Override
                            public void onFailure(Call<JWTResponse> call, Throwable t) {
                                Toast.makeText(UserLoginActivity.this, "Login failed!!!", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(UserLoginActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                            }

                        });
//
            }

        });


        TextView text = findViewById(R.id.signUp);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, PassengerRegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
    protected void onDestroy() {
        super.onDestroy();
    }


}