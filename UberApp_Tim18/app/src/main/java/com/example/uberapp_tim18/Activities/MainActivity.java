package com.example.uberapp_tim18.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberapp_tim18.R;

//dasdasdsadasdadasdasda
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);
//        MapFragment mapFragment = new MapFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.bane, mapFragment).commit();

    }
}