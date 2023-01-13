package com.example.uberapp_tim18;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//dasdasdsadasdadasdasda
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this,SplashActivity.class);
        //Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        startActivity(intent);

    }
}