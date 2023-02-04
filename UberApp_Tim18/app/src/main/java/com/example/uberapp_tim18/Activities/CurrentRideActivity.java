package com.example.uberapp_tim18.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.airbnb.lottie.L;
import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.fragments.ForgotPasswordCode;
import com.example.uberapp_tim18.fragments.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class CurrentRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ride);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MapFragment fragment = new MapFragment(0);
        transaction.replace(R.id.fragment_current_ride, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}