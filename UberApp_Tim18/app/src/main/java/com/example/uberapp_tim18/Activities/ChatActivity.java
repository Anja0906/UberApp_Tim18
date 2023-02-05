package com.example.uberapp_tim18.Activities;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uberapp_tim18.Adapters.ChatAdapter;
import com.example.uberapp_tim18.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.MessageDTO;
import DTO.MessageResponseDTO;
import DTO.MessageRetDTOMap;
import DTO.UserDTO;
import retrofit.RetrofitService;
import retrofit.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.HelperClasses;
import ua.naiksoftware.stomp.StompClient;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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
    private Integer otherUserId;
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
        if (this.receiver!=null){
            this.otherUserId=this.receiver.getId();
        }
        this.rideId = (Integer) getIntent().getIntExtra("rideId", -1);

        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        if (rideId==-1) {
            userApi.findMessages(id).enqueue(new Callback<MessageRetDTOMap>() {
                @Override
                public void onResponse(Call<MessageRetDTOMap> call, Response<MessageRetDTOMap> response) {
                    ArrayList<MessageResponseDTO> messageResponseDTOS = response.body().getResults();
                    if (messageResponseDTOS.isEmpty()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "No messages to show!", Toast.LENGTH_SHORT);
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
        } else {
            userApi.findMessages(id, otherUserId, rideId).enqueue(new Callback<MessageRetDTOMap>() {
                @Override
                public void onResponse(Call<MessageRetDTOMap> call, Response<MessageRetDTOMap> response) {
                    ArrayList<MessageResponseDTO> messageResponseDTOS = response.body().getResults();
                    if (messageResponseDTOS.isEmpty()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "No messages to show!", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        sort(messageResponseDTOS);
                        for (MessageResponseDTO message : messageResponseDTOS) {
                            messages.add(message);
                            chatAdapter.notifyDataSetChanged();
                        }
                        chatAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MessageRetDTOMap> call, Throwable t) {
                    Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred get all messages", t);
                }
            });
        }
        txtSender.setText(receiver.getName() + " " + receiver.getSurname());
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

    }

    private void addItem(String item) {
        MessageDTO dto;
        if (!item.isEmpty()) {
            if (rideId==-1) {
                 dto = new MessageDTO(receiver.getId(), item, "RIDE", -1);
            } else {
                dto = new MessageDTO(receiver.getId(), item, "RIDE", this.rideId);
            }
            UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
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
        }
    }

    private void addItem2(String item) {
        MessageDTO dto;
        if (!item.isEmpty()) {
            if (rideId==-1) {
                dto = new MessageDTO(receiver.getId(), item, "RIDE", -1);
            } else {
                dto = new MessageDTO(receiver.getId(), item, "RIDE", this.rideId);
            }
            UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
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
                                Integer id = Integer.parseInt(topicMessage.getPayload().split(",")[0].split(":")[1]);
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                Integer senderId = Integer.parseInt(topicMessage.getPayload().split(",")[2].split(":")[1]);
                                Integer receiverId = Integer.parseInt(topicMessage.getPayload().split(",")[3].split(":")[1]);
                                String message = topicMessage.getPayload().split(",")[4].split(":")[1];
                                String type = topicMessage.getPayload().split(",")[5].split(":")[1];
                                Integer rideId = Integer.parseInt(topicMessage.getPayload().split(",")[6].split(":")[1]);
                                messages.add(new MessageResponseDTO(id, formatter.format(new Date()), senderId, receiverId, message, type, rideId));
                                chatAdapter.notifyDataSetChanged();

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


    public static void sort(ArrayList<MessageResponseDTO> list) {

        list.sort((o1, o2)
                -> o1.getTimeOfSending().compareTo(
                o2.getTimeOfSending()));
    }

}