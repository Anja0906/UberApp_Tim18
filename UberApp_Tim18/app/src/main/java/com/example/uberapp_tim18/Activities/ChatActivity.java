package com.example.uberapp_tim18.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uberapp_tim18.Adapters.ChatAdapter;
import com.example.uberapp_tim18.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.MessageDTO;
import DTO.MessageResponseDTO;
import DTO.MessageRetDTOMap;
import DTO.RideResponseDTO;
import DTO.UserDTO;
import model.Message;
import model.User;
import retrofit.RetrofitService;
import retrofit.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.HelperClasses;
import tools.Mockup;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


/*
SENDER - > Current user
RECEIVER - > Person current user is talking to
???????????????????????????????????
 */


public class ChatActivity extends AppCompatActivity {

    private RecyclerView rv;
    private EditText inputMessage;
    private TextView txtSender;
    private ImageView imgSender;
    Button sendBtn;
    private ArrayList<MessageResponseDTO> allMessages, messages;
    private ChatAdapter chatAdapter;
    private Context context;
    private UserDTO sender, receiver;
    private RetrofitService retrofitService;
    private Integer id;
    private SharedPreferences preferences;
    private String token;
    private Integer rideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rv = findViewById(R.id.recyclerMessages);
        inputMessage = findViewById(R.id.input_message);
        txtSender = findViewById(R.id.toolbar_sender_name);
        imgSender = findViewById(R.id.toolbar_sender_img);
        sendBtn = findViewById(R.id.sendBtn);
        messages = new ArrayList<>();
        this.preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        this.token = preferences.getString("jwt", "");
        this.retrofitService = new RetrofitService();
        this.id = Integer.parseInt(preferences.getString("id", ""));
        retrofitService.onSavedUser(token);

        this.sender = (UserDTO) HelperClasses.Deserialize(getIntent().getByteArrayExtra("sender"));
        this.receiver = (UserDTO) HelperClasses.Deserialize(getIntent().getByteArrayExtra("receiver"));
        this.rideId = (Integer) getIntent().getIntExtra("rideId", -1);

        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        userApi.findMessages(this.id).enqueue(new Callback<MessageRetDTOMap>() {
            @Override
            public void onResponse(Call<MessageRetDTOMap> call, Response<MessageRetDTOMap> response) {
                ArrayList<MessageResponseDTO> messageResponseDTOS = response.body().getResults();
                if (messageResponseDTOS.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(),"No messages to show!",Toast. LENGTH_SHORT);
                    toast.show();
                } else {
                    sort(messageResponseDTOS);
                    for (MessageResponseDTO message : messageResponseDTOS) {
                        if ((message.getSenderId().equals(sender.getId()) && message.getReceiverId().equals(receiver.getId())) || (
                                message.getSenderId().equals(receiver.getId()) && message.getReceiverId().equals(sender.getId()))) {
                            messages.add(message);
                            chatAdapter.notifyDataSetChanged();
                        }
                    }
                    chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MessageRetDTOMap> call, Throwable t) {
                Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred get all messages", t);
            }
        });


        txtSender.setText(receiver.getName() + " " + receiver.getSurname());
//        int pfp = Integer.parseInt(receiver.getProfilePicture());
//        if (pfp != -1){
//            imgSender.setImageResource(pfp);
//        }}


        chatAdapter = new ChatAdapter(messages, ChatActivity.this, sender);
        rv.setAdapter(chatAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(inputMessage.getText().toString());
                inputMessage.getText().clear();
            }
        });

//        initializeWebSocketConnection();
    }

    private void addItem(String item) {
        // on below line we are checking
        // if item is empty or not.
        MessageDTO dto;
        if (!item.isEmpty()) {
            if (rideId==-1) {
                 dto = new MessageDTO(receiver.getId(), item, "RIDE", -1);
            } else {
                dto = new MessageDTO(receiver.getId(), item, "RIDE", this.rideId);
            }
            UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
            // on below line we are adding
            // item to our list
            userApi.sendMessage(receiver.getId(), dto).enqueue(new Callback<MessageResponseDTO>() {
                @Override
                public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                    if (response == null) {
                        Toast.makeText(getApplicationContext(), "Cannot send message", Toast.LENGTH_SHORT).show();
                    } else {
                        messages.add(response.body());
                        chatAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MessageResponseDTO> call, Throwable t) {
                    Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                }
            });
            // on below line we are notifying
            // adapter that data has updated.
        }
    }

    private void addItem2(String item) {
        // on below line we are checking
        // if item is empty or not.
        MessageDTO dto;
        if (!item.isEmpty()) {
            if (rideId==-1) {
                dto = new MessageDTO(receiver.getId(), item, "RIDE", -1);
            } else {
                dto = new MessageDTO(receiver.getId(), item, "RIDE", this.rideId);
            }
            UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
            // on below line we are adding
            // item to our list
            userApi.sendMessage(receiver.getId(), dto).enqueue(new Callback<MessageResponseDTO>() {
                @Override
                public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                    if (response == null) {
                        Toast.makeText(getApplicationContext(), "Cannot send message", Toast.LENGTH_SHORT).show();
                    } else {
                        messages.add(response.body());
                        chatAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MessageResponseDTO> call, Throwable t) {
                    Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                }
            });
            // on below line we are notifying
            // adapter that data has updated.
        }
    }

    StompClient stompClient;
    @SuppressLint("CheckResult")
    void openGlobalSocket(){
        stompClient.topic("/socket-topic/newMessage/" + this.id).subscribe(
                topicMessage ->{
                    Log.i("SOCKET", topicMessage.getPayload());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"New message!",Toast. LENGTH_SHORT).show();
                            try {
//                                Thread.sleep(1000);
//                                Intent intent = new Intent(ChatActivity.this, ChatActivity.class);
//                                byte[] receiverBytes = HelperClasses.Serialize(sender);
//                                intent.putExtra("sender", receiverBytes);
                                Integer id = Integer.parseInt(topicMessage.getPayload().split(",")[0].split(":")[1]);
//                                String dateStr = topicMessage.getPayload().split(",")[1].split(":")[1] + ":" + topicMessage.getPayload().split(",")[1].split(":")[2] + ":" + topicMessage.getPayload().split(",")[1].split(":")[3];
//                                Date timeOfSendingDate = null;
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                                timeOfSendingDate = formatter.parse(dateStr);
                                Integer senderId = Integer.parseInt(topicMessage.getPayload().split(",")[2].split(":")[1]);
                                Integer receiverId = Integer.parseInt(topicMessage.getPayload().split(",")[3].split(":")[1]);
                                String message = topicMessage.getPayload().split(",")[4].split(":")[1];
                                String type = topicMessage.getPayload().split(",")[5].split(":")[1];
                                Integer rideId = Integer.parseInt(topicMessage.getPayload().split(",")[6].split(":")[1]);
                                messages.add(new MessageResponseDTO(id, formatter.format(new Date()), senderId, receiverId, message, type, rideId));
                                chatAdapter.notifyDataSetChanged();
//                                Log.i("USER SENDER", intUser);
//                                int receiverId = Integer.parseInt(intUser);
//                                byte[] senderBytes = null;
//                                UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
//                                userApi.findById(receiverId).enqueue(new Callback<UserDTO>() {
//                                    @Override
//                                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
//                                        byte[] senderBytes = HelperClasses.Serialize(response.body());
//                                        intent.putExtra("receiver", senderBytes);
//                                        startActivity(intent);
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<UserDTO> call, Throwable t) {
//
//                                    }
//                                });

                            } catch (Exception e) {
                                Log.i("DATE", e.getMessage());
                            }

                        }
                    });
                },
                throwable -> {
                    Log.e("SOCKET", "Error: " + throwable.getMessage());
                }
        );
        chatAdapter.notifyDataSetChanged();

    }

    private void initializeWebSocketConnection(){
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP,"ws://192.168.0.33:8080/socket/websocket");
        stompClient.connect();
        openGlobalSocket();

    }

    public static void sort(ArrayList<MessageResponseDTO> list) {

        list.sort((o1, o2)
                -> o1.getTimeOfSending().compareTo(
                o2.getTimeOfSending()));
    }

}