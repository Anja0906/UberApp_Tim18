package com.example.uberapp_tim18.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import com.example.uberapp_tim18.R;

import model.Message;
import model.User;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<Message> messagesArrayList;
    private Context context;
    private User currentUser;
    int SENT = 1;
    int RECEIVED = 2;

    public ChatAdapter(ArrayList<Message> _messagesArrayList, Context _context, User _currentUser) {
        this.messagesArrayList = _messagesArrayList;
        this.context = _context;
        this.currentUser = _currentUser;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==SENT)
        {
            view = LayoutInflater.from(context).inflate(R.layout.sender_chat_layout,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            view = LayoutInflater.from(context).inflate(R.layout.receiver_chat_layout,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messagesArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            SenderViewHolder viewHolder=(SenderViewHolder)holder;

            viewHolder.message.setText(message.getContent());

            LocalDateTime date = message.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
            viewHolder.timeOfMessage.setText(date.format(formatter));
        }
        else
        {
            ReceiverViewHolder viewHolder=(ReceiverViewHolder)holder;
            viewHolder.message.setText(message.getContent());

            LocalDateTime date = message.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
            viewHolder.timeOfMessage.setText(date.format(formatter));
        }
    }


    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int i) {
        Message message = messagesArrayList.get(i);
        if (Objects.equals(message.getTo(), currentUser)) {
            return 2;
        }
        return 1;
    }

}

class SenderViewHolder extends RecyclerView.ViewHolder
{
    TextView message;
    TextView timeOfMessage;

    public SenderViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.sender_message);
        timeOfMessage=itemView.findViewById(R.id.time_of_message_sender);
    }
}

class ReceiverViewHolder extends RecyclerView.ViewHolder
{
    TextView message;
    TextView timeOfMessage;

    public ReceiverViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.receiver_message);
        timeOfMessage=itemView.findViewById(R.id.time_of_message_receiver);
    }
}
