package com.example.uberapp_tim18;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import model.Ride;
import model.User;
import tools.HelperClasses;

public class PassengerAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_account);
        Intent intent = getIntent();
        byte[] userBytes = intent.getByteArrayExtra("user");
        ByteArrayInputStream bis = new ByteArrayInputStream(userBytes);
        ObjectInput in = null;
        User user = null;
        try {
            in = new ObjectInputStream(bis);
            user = (User)in.readObject();
//            System.out.println("uspeo sam");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        //System.out.println(ride);
        TextView name = (TextView)findViewById(R.id.name_txt_view);
        name.setText(user.getName());
        TextView phone = (TextView)findViewById(R.id.phone_txt_view);
        phone.setText(Double.toString(user.getTelephoneNumber()));
        TextView email = (TextView)findViewById(R.id.email_txt_view);
        email.setText(user.getEmailAddress());
        TextView address = (TextView)findViewById(R.id.address_txt_view);
        address.setText(user.getAddress());
        TextView password = (TextView)findViewById(R.id.pass_txt_view);
        password.setText(user.getPassword());

        DrawerLayout drawerLayout = findViewById(R.id.passenger_account_activity);

        findViewById(R.id.menu_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = findViewById(R.id.navigation_view_passenger_account);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inbox:
                        Intent inbox = new Intent(PassengerAccountActivity.this, PassengerInboxActivity.class);
                        inbox.putExtra("user", getIntent().getByteArrayExtra("user"));;
                        startActivity(inbox);
                        break;
                    case R.id.account:
                        Intent profile = new Intent(PassengerAccountActivity.this, PassengerAccountActivity.class);
                        profile.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(profile);
                        break;

                    case R.id.ride_history:
                        Intent history = new Intent(PassengerAccountActivity.this, DriverRideHistoryActivity.class);
                        history.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(history);
                        break;
                    case R.id.home:
                        User user = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("user"));
                        Intent home = null;
                        if (user.getRole() == 1) {
                            home = new Intent(PassengerAccountActivity.this, PassengerMainActivity.class);
                        }
                        if (user.getRole() == 2) {
                            home = new Intent(PassengerAccountActivity.this, DriverMainActivity.class);
                        }
                        home.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(home);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}