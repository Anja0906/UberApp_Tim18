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

import model.Role;
import model.User;
import tools.HelperClasses;
import tools.Mockup;

public class UserLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login2);

        ArrayList<User> users = Mockup.getUsers();

        Button button = findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.Email);
                EditText password = (EditText)findViewById(R.id.Password);
                boolean found = false;
                User foundUser = null;
                for (User user : users) {
                    if (username.getText().toString().equals(user.getEmail()) && password.getText().toString().equals(user.getPassword())) {
                        found = true;
                        foundUser = user;
                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(UserLoginActivity.this, "Incorrect data!", Toast.LENGTH_SHORT).show();
                } else {
                    byte[] userBytes = HelperClasses.Serialize(foundUser);
                    if (foundUser.getRole() == Role.PASSENGER) {
                        Intent intent = new Intent(UserLoginActivity.this, PassengerMainActivity.class);
                        intent.putExtra("user", userBytes);
                        startActivity(intent);
                    } else {
                        Intent intent2 = new Intent(UserLoginActivity.this, DriverMainActivity.class);
                        intent2.putExtra("user", userBytes);
                        startActivity(intent2);
                    }
                }
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