package com.example.uberapp_tim18.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.uberapp_tim18.R;
import com.example.uberapp_tim18.fragments.AboutVehicle;
import com.example.uberapp_tim18.fragments.DriverAccount;
import com.example.uberapp_tim18.fragments.DriverReports;
import com.example.uberapp_tim18.fragments.DriverStatistics;
import com.example.uberapp_tim18.fragments.PassengerAccount;

public class DriverAccountActivity extends AppCompatActivity {
    ImageView account;
    ImageView reports;
    ImageView statistics;
    ImageView vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DriverAccount driverAccount = new DriverAccount();
        fragmentTransaction.add(R.id.fragment_container, driverAccount, "my_fragment_tag").commit();
        initMenu();
    }

    private void initMenu(){
        account = findViewById(R.id.account_account_menu);
        reports = findViewById(R.id.reports_account_menu);
        statistics = findViewById(R.id.stats_account_menu);
        vehicle = findViewById(R.id.about_vehicle);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverAccount driverAccount = new DriverAccount();
                openFragment(driverAccount);
            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverReports driverReports = new DriverReports();
                openFragment(driverReports);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverStatistics driverStatistics = new DriverStatistics();
                openFragment(driverStatistics);
            }
        });
        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutVehicle aboutVehicle = new AboutVehicle();
                openFragment(aboutVehicle);
            }
        });
    }

    private void openFragment(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    
}