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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uberapp_tim18.Adapters.MessageAdapter;
import com.example.uberapp_tim18.Adapters.RideAdapter;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Message;
import model.Ride;
import model.User;
import tools.HelperClasses;

public class PassengerInboxActivity extends Activity {
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);

        Intent mainIntent = getIntent();

        byte[] userBytes = getIntent().getByteArrayExtra("user");
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

        lv  = (ListView) findViewById(R.id.list_view);
        MessageAdapter adapter = new MessageAdapter(this, user);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PassengerInboxActivity.this, MessageDetailActivity.class);
                Message message = (Message) parent.getItemAtPosition(position);
                byte[] messageBytes = new byte[0];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = null;
                try {
                    out = new ObjectOutputStream(bos);
                    out.writeObject(message);
                    out.flush();
                    messageBytes = bos.toByteArray();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bos.close();
                    } catch (IOException ex) {
                        // ignore close exception
                    }
                }
                intent.putExtra("message", messageBytes);
                intent.putExtra("user", mainIntent.getByteArrayExtra("user"));
                startActivity(intent);
            }
        });

        DrawerLayout drawerLayout = findViewById(R.id.simple_list_view);

        findViewById(R.id.menu_toolbar_icon_simple_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {drawerLayout.openDrawer(GravityCompat.START);}
        });


        NavigationView navigationView = findViewById(R.id.navigation_view_simple_list);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.inbox:
                        Toast toast= Toast.makeText(getApplicationContext(),"Already on page",Toast. LENGTH_LONG);
                        toast.setMargin(50,50);
                        toast.show();
                        break;
                    case R.id.account:
                        Intent profile = new Intent(PassengerInboxActivity.this, PassengerAccountActivity.class);
                        profile.putExtra("user", mainIntent.getByteArrayExtra("user"));
                        startActivity(profile);
                        break;
                    case R.id.ride_history:
                        Intent ride = new Intent(PassengerInboxActivity.this, PassengerAccountActivity.class);
                        ride.putExtra("user", mainIntent.getByteArrayExtra("user"));
                        startActivity(ride);
                        break;
                    case R.id.home:
                        User user = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("user"));
                        Intent home = null;
                        if (user.getRole() == 1) {
                            home = new Intent(PassengerInboxActivity.this, PassengerMainActivity.class);
                        }
                        if (user.getRole() == 2) {
                            home = new Intent(PassengerInboxActivity.this, DriverMainActivity.class);
                        }
                        home.putExtra("user", mainIntent.getByteArrayExtra("user"));
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