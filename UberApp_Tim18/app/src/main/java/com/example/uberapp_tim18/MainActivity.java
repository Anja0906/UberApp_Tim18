package com.example.uberapp_tim18;





import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this,SplashActivity.class);
        startActivity(intent);
        //        MapFragment mapFragment = new MapFragment();
//        DrawRouteFragment drawRouteFragment = new DrawRouteFragment();
////        SearchFragment searchFragment = new SearchFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mapFragment).commit();

    }
}