package com.example.uberapp_tim18.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uberapp_tim18.R;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.JWTResponse;
import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import retrofit.PassengerApi;
import retrofit.UserRetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerRegisterActivity extends AppCompatActivity {
    EditText name;
    EditText surname;
    EditText address;
    EditText email;
    EditText password;
    EditText confirmPassword;
    EditText telephoneNumber;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_register);
        initializeComponents();

    }

    private void initializeComponents() {
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        address = findViewById(R.id.address);
        email = findViewById(R.id.passenger_email);
        password = findViewById(R.id.passenger_password);
        confirmPassword = findViewById(R.id.passenger_password_confirm);
        telephoneNumber = findViewById(R.id.phone_num);

        initButton();
    }

    private void initButton(){
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });
    }

    private void sendRequest(){
        UserRetrofitService userRetrofitService = new UserRetrofitService();
        PassengerApi passengerApi = userRetrofitService.getRetrofit().create(PassengerApi.class);
        PassengerPostDTO passengerPostDTO = collectInputs();
        if(passengerPostDTO!=null){
            passengerApi.save(passengerPostDTO)
                    .enqueue(new Callback<PassengerResponseDTO>() {
                        @Override
                        public void onResponse(Call<PassengerResponseDTO> call, Response<PassengerResponseDTO> response) {
                            saveLoggedUser(passengerPostDTO.getEmail());
                            Toast.makeText(PassengerRegisterActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PassengerRegisterActivity.this, ConfirmRegistration.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<PassengerResponseDTO> call, Throwable t) {
                            System.out.println("pada");
                            Log.i("Pada zbog", t.toString());
                            Toast.makeText(PassengerRegisterActivity.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }


                    });
        }

    }

    private PassengerPostDTO collectInputs(){
        String nameTxt = name.getText().toString();
        String surnameTxt = surname.getText().toString();
        String addressTxt = address.getText().toString();
        String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();
        String confirmPasswordTxt = confirmPassword.getText().toString();
        String telephoneNumberTxt = telephoneNumber.getText().toString();
        if (confirmPasswordTxt.equals(passwordTxt)){
            return new PassengerPostDTO(nameTxt, surnameTxt, "1", telephoneNumberTxt, emailTxt, addressTxt, passwordTxt);
        }
        else{
            return null;
        }
    }

    private void saveLoggedUser(String email){
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.apply();
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