package com.example.uberapp_tim18.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;



import com.example.uberapp_tim18.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    ImageView imageView;
    Animation splash_animation;
    int SPLASH_TIME_OUT = 5000;

    private void makeToast(String string,int flag){
        Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG);
        toast.setMargin(50,50);
        toast.show();
        if(flag == 1) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        System.out.println("DASDASDAS");
        imageView = findViewById(R.id.logo_splash);
        splash_animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        imageView.setAnimation(splash_animation);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnectedMobileData = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null
                && cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();

        boolean isWifiConnected = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null
                && cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isConnectedMobileData || isWifiConnected) {
            if (!isLocationEnabled) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Location is required for this app. Please turn on location.")
                        .setCancelable(false).setPositiveButton("Turn On", (dialog, id) -> {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        });
                AlertDialog alert = builder.create();
                alert.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                        if (isLocationEnabled) {
                            startActivity(new Intent(SplashActivity.this, UserLoginActivity.class));
                            finish();
                        } else {
                            makeToast("Location must be enabled", Toast.LENGTH_SHORT);
                        }
                    }
                }, SPLASH_TIME_OUT);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, UserLoginActivity.class));
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }
        } else {
            makeToast("You are not connected on internet", 1);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isLocationEnabled) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, UserLoginActivity.class));
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}