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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.time.format.DateTimeFormatter;

import model.Message;
import model.Ride;
import model.User;
import tools.HelperClasses;

public class MessageDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        Intent intent = getIntent();
        byte[] messageBytes = intent.getByteArrayExtra("message");
        ByteArrayInputStream bis = new ByteArrayInputStream(messageBytes);
        ObjectInput in = null;
        Message message = null;
        try {
            in = new ObjectInputStream(bis);
            message = (Message) in.readObject();
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

        TextView from = (TextView)findViewById(R.id.from_txt_view);
        from.setText(message.getFrom().getEmailAddress());
        TextView type = (TextView)findViewById(R.id.type_txt_view);
        type.setText(message.getType().toString());
        TextView date = (TextView)findViewById(R.id.date_txt_view);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        date.setText(message.getDate().format(formatter));
        TextView content = (TextView)findViewById(R.id.content_txt_view);
        content.setText(message.getContent());


        DrawerLayout drawerLayout = findViewById(R.id.message_detail);

        findViewById(R.id.menu_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = findViewById(R.id.message_detail_navigation);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.inbox:
                        Intent inbox = new Intent(MessageDetailActivity.this, PassengerInboxActivity.class);
                        inbox.putExtra("user", intent.getByteArrayExtra("user"));;
                        startActivity(inbox);
                        break;
                    case R.id.account:
                        Intent profile = new Intent(MessageDetailActivity.this, PassengerAccountActivity.class);
                        profile.putExtra("user", intent.getByteArrayExtra("user"));
                        startActivity(profile);
                        break;

                    case R.id.ride_history:
                        Intent history = new Intent(MessageDetailActivity.this, DriverRideHistoryActivity.class);
                        history.putExtra("user", intent.getByteArrayExtra("user"));
                        startActivity(history);
                        break;
                    case R.id.home:
                        User user = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("user"));
                        Intent home = null;
                        if (user.getRole() == 1) {
                            home = new Intent(MessageDetailActivity.this, PassengerMainActivity.class);
                        }
                        if (user.getRole() == 2) {
                            home = new Intent(MessageDetailActivity.this, DriverMainActivity.class);
                        }
                        home.putExtra("user", intent.getByteArrayExtra("user"));
                        startActivity(home);
                        break;
                }
                return false;
            }
        });
    }
}