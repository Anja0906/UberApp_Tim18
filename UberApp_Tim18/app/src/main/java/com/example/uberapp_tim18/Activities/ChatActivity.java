package com.example.uberapp_tim18.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uberapp_tim18.Adapters.ChatAdapter;
import com.example.uberapp_tim18.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import model.Message;
import model.User;
import tools.HelperClasses;
import tools.Mockup;


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
    private ArrayList<Message> allMessages, messages;
    private ChatAdapter chatAdapter;
    private Context context;
    private User sender, receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rv = findViewById(R.id.recyclerMessages);
        inputMessage = findViewById(R.id.input_message);
        txtSender = findViewById(R.id.toolbar_sender_name);
        imgSender = findViewById(R.id.toolbar_sender_img);
        sendBtn = findViewById(R.id.sendBtn);
        allMessages = Mockup.getMessages();
        messages = new ArrayList<>();

        this.sender = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("sender"));
        this.receiver = (User) HelperClasses.Deserialize(getIntent().getByteArrayExtra("receiver"));

        txtSender.setText(receiver.getName() + " " + receiver.getSurname());
        int pfp = Integer.parseInt(receiver.getProfilePicture());
        if (pfp != -1){
            imgSender.setImageResource(pfp);
        }


        for (Message message : allMessages) {
            if ((Objects.equals(message.getTo(), this.receiver) && Objects.equals(message.getFrom(), this.sender)) ||
            (Objects.equals(message.getFrom(), this.receiver) && Objects.equals(message.getTo(), this.sender))) {
                messages.add(message);
                //System.out.println(message);
            }
        }

        chatAdapter = new ChatAdapter(messages, ChatActivity.this, this.sender);
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
        // on below line we are checking
        // if item is empty or not.
        if (!item.isEmpty()) {
            // on below line we are adding
            // item to our list
            Message message = new Message(this.sender, this.receiver, item, LocalDateTime.now(), Message.MessageType.SUPPORT);
            messages.add(message);
            // on below line we are notifying
            // adapter that data has updated.
            chatAdapter.notifyDataSetChanged();
        }
    }
}