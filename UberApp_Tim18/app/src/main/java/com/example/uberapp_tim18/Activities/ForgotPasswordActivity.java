package com.example.uberapp_tim18.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.uberapp_tim18.R;

import retrofit.RetrofitService;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        RetrofitService retrofitService = new RetrofitService();
        setUserInterface(retrofitService);
    }

    private void setUserInterface(RetrofitService retrofitService){
        EditText username = (EditText) findViewById(R.id.email);
        EditText newPassword = (EditText) findViewById(R.id.passenger_password);
        EditText confirmPassword = (EditText) findViewById(R.id.passenger_password_confirm);
        String emailText = String.valueOf(username.getText());
        String newPasswordText = String.valueOf(newPassword.getText());
        String confirmPasswordText = String.valueOf(confirmPassword.getText());
        Button button = findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newPasswordText.equals(confirmPasswordText)){
                    //odraditi promenu lozinke na beku preko retrofit servisa
                }
            }
        });
    }
}