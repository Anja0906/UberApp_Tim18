package com.example.uberapp_tim18.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.uberapp_tim18.Adapters.UserAdapter;
import com.example.uberapp_tim18.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.MessageResponseDTO;
import DTO.RideRetDTOMap;
import DTO.UserDTO;
import model.Role;
import model.User;
import retrofit.DriverApi;
import retrofit.RetrofitService;
import retrofit.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.HelperClasses;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

/*
SENDER - > Current user
RECEIVER - > Person current user is talking to
???????????????????????????????????
 */


public class PassengerInboxActivity extends Activity {
    private ListView lv;
    private RetrofitService retrofitService;
    private UserAdapter adapter;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SharedPreferences preferences;

    private String token;
    private Integer id;
    private UserDTO currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);
        Intent mainIntent = getIntent();
        this.initGUI();

        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        userApi.findAlmostAllUsers().enqueue(new Callback<List<UserDTO>>() {
            @Override
            public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                if (response.body().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(),"No users to talk to!",Toast. LENGTH_SHORT);
                    toast.show();
                } else {
                    adapter.setAllUsers(response.body());

                    lv.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });

//        byte[] userBytes = getIntent().getByteArrayExtra("user");
//        User currentUser = (User)HelperClasses.Deserialize(userBytes);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PassengerInboxActivity.this, ChatActivity.class);
                UserDTO user = (UserDTO) parent.getItemAtPosition(position);
                byte[] receiverBytes = HelperClasses.Serialize(user);
                intent.putExtra("receiver", receiverBytes);
                byte[] senderBytes = HelperClasses.Serialize(currentUser);
                intent.putExtra("sender", senderBytes);
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
                        Intent ride = new Intent(PassengerInboxActivity.this, DriverRideHistoryActivity.class);
                        ride.putExtra("user", mainIntent.getByteArrayExtra("user"));
                        startActivity(ride);
                        break;
                    case R.id.home:
                        User user = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("user"));
                        Intent home = null;
                        if (user.getRoles().get(1) == Role.PASSENGER) {
                            home = new Intent(PassengerInboxActivity.this, PassengerMainActivity.class);
                        }
                        if (user.getRoles().get(1) == Role.DRIVER) {
                            home = new Intent(PassengerInboxActivity.this, DriverMainActivity.class);
                        }
                        home.putExtra("user", mainIntent.getByteArrayExtra("user"));
                        startActivity(home);
                        break;
                    case R.id.settings:
                        Intent settings = new Intent(PassengerInboxActivity.this, ReviewerPreferenceActivity.class);
                        settings.putExtra("user", getIntent().getByteArrayExtra("user"));
                        startActivity(settings);
                        break;
                }
                return false;
            }
        });
        initializeWebSocketConnection();
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

    public void initGUI() {
        this.preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        this.token = preferences.getString("jwt", "");
        this.retrofitService = new RetrofitService();
        this.id = Integer.parseInt(preferences.getString("id", ""));
        lv  = (ListView) findViewById(R.id.list_view);
        this.adapter = new UserAdapter(this);
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        userApi.findById(this.id).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.body()==null){
                    Toast toast= Toast.makeText(getApplicationContext(),"No user",Toast. LENGTH_SHORT);
                    toast.show();
                }else{
                    currentUser = response.body();
                    adapter.setUser(response.body());
//                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });

        this.navigationView = findViewById(R.id.navigation_view_simple_list);
        this.navigationView.setItemIconTintList(null);
        this.drawerLayout = findViewById(R.id.simple_list_view);
        retrofitService.onSavedUser(token);
    }

    StompClient stompClient;
    @SuppressLint("CheckResult")
    void openGlobalSocket(){
        stompClient.topic("/socket-topic/newMessageInbox/" + this.id).subscribe(
                topicMessage ->{
                    Log.i("SOCKET", topicMessage.getPayload());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"New message!",Toast. LENGTH_SHORT).show();
                            try {
                                Thread.sleep(1000);
                                Intent intent = new Intent(PassengerInboxActivity.this, ChatActivity.class);
                                byte[] receiverBytes = HelperClasses.Serialize(currentUser);
                                intent.putExtra("sender", receiverBytes);
                                String intUser = topicMessage.getPayload().split(",")[2].split(":")[1];
                                Log.i("USER SENDER", intUser);
                                int receiverId = Integer.parseInt(intUser);
//                                byte[] senderBytes = null;
                                UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
                                userApi.findById(receiverId).enqueue(new Callback<UserDTO>() {
                                    @Override
                                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                        byte[] senderBytes = HelperClasses.Serialize(response.body());
                                        intent.putExtra("receiver", senderBytes);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<UserDTO> call, Throwable t) {

                                    }
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                      },
                throwable -> {
                    Log.e("SOCKET", "Error: " + throwable.getMessage());
                }
        );
    }

    private void initializeWebSocketConnection(){
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP,"ws://172.16.177.204:8080/socket/websocket");
        stompClient.connect();
        openGlobalSocket();

    }

}