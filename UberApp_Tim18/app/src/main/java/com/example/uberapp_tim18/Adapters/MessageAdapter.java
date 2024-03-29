package com.example.uberapp_tim18.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp_tim18.R;

import DTO.UserDTO;
import model.Message;
import model.User;
import tools.Mockup;

//public class MessageAdapter extends BaseAdapter {
//    private Activity activity;
//    private User userTo;
//
//    public MessageAdapter(Activity activity, User userTo, int dummy) {
//        this.activity = activity;
//        this.userTo = userTo;
//    }
//
//    @Override
//    public int getCount() {
//        return Mockup.getMessages().size();
//    }
//
//    @Override
//    public Message getItem(int i) {
//        return Mockup.getMessages().get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        View vi = view;
//        Message message = Mockup.getMessages().get(i);
//
//        if(view==null)
//            vi = activity.getLayoutInflater().inflate(R.layout.activity_passenger_inbox, null);
//
//        TextView from_name = (TextView)vi.findViewById(R.id.from_name_txt_view);
//        /*
//        TextView from_content = (TextView)vi.findViewById(R.id.from_content_txt_view);
//        TextView mess_date = (TextView)vi.findViewById(R.id.from_date_txt_view);
//        TextView mess_time = (TextView)vi.findViewById(R.id.from_time_txt_view);
//         */
//        ImageView image = (ImageView)vi.findViewById(R.id.from_pfp_view);
//
//        UserDTO to = message.getTo();
//        UserDTO from = message.getFrom();
//        int pfp = Integer.parseInt(from.getProfilePicture());
//        String fromName = from.getName() + " " + from.getSurname();
////        LocalDateTime date = message.getDate();
//        String content = message.getContent();
//        content = content.substring(0, 10) + "...";
//
//        /*
//        LocalDateTime date = message.getDate();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
//        mess_date.setText(date.format(formatter));
//
//        formatter = DateTimeFormatter.ofPattern("HH:mm");
//        mess_time.setText(date.format(formatter));
//
//        from_name.setText(fromName);
//        from_content.setText(content);
//
//         */
//
//        if (pfp != -1){
//            image.setImageResource(pfp);
//        }
//
//        return  vi;
//    }
//}
