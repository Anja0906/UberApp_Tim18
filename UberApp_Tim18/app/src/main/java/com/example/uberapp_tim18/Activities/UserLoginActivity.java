package com.example.uberapp_tim18.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp_tim18.R;

import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.JWTResponse;
import DTO.LoginDTO;
import retrofit.LoginApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login2);

        RetrofitService retrofitService = new RetrofitService();
        this.setUserInterface(retrofitService);
    }

    private void saveLoggedUser(JWTResponse response){
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id", String.valueOf(response.getId()));
        editor.putString("email", response.getEmail());
        editor.putString("role", String.valueOf(response.getRoles().get(0)));
        editor.putString("jwt", response.getAccessToken());
        editor.apply();
    }

    private void setUserInterface(RetrofitService retrofitService){
        Button button = findViewById(R.id.login);
        LoginApi loginApi = retrofitService.getRetrofit().create(LoginApi.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.Email);
                EditText password = findViewById(R.id.Password);

                String emailText = String.valueOf(username.getText());
                String passwordText = String.valueOf(password.getText());

                LoginDTO loginDTO = new LoginDTO(emailText, passwordText);

                loginApi.save(loginDTO)
                        .enqueue(new Callback<JWTResponse>() {
                            @Override
                            public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
                                Toast.makeText(UserLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                saveLoggedUser(response.body());
                                if (response.body().getRoles().get(1).equals("ROLE_PASSENGER")) {
                                    Intent intent = new Intent(UserLoginActivity.this, PassengerInboxActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent2 = new Intent(UserLoginActivity.this, DriverMainActivity.class);
                                    startActivity(intent2);
                                }
                            }

                            @Override
                            public void onFailure(Call<JWTResponse> call, Throwable t) {
                                Toast.makeText(UserLoginActivity.this, "Login failed!!!", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(UserLoginActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                            }
                        });
            }
        });


        TextView text = findViewById(R.id.signUp);
        text.setOnClickListener(v -> {
            Intent intent = new Intent(UserLoginActivity.this, PassengerRegisterActivity.class);
            startActivity(intent);
        });

        TextView forgotPassword = findViewById(R.id.forgotPass);
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(UserLoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
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