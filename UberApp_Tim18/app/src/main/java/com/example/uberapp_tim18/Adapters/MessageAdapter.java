package com.example.uberapp_tim18.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberapp_tim18.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Message;
import model.User;
import tools.Mockup;

public class MessageAdapter extends BaseAdapter {
    private Activity activity;
    private User userTo;

    public MessageAdapter(Activity activity, User userTo) {
        this.activity = activity;
        this.userTo = userTo;
    }

    @Override
    public int getCount() {
        return Mockup.getRides().size();
    }

    @Override
    public Message getItem(int i) {
        return Mockup.getMessages().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        Message message = Mockup.getMessages().get(i);

        if(view==null)
            vi = activity.getLayoutInflater().inflate(R.layout.activity_passenger_inbox, null);

        TextView from_name = (TextView)vi.findViewById(R.id.from_name_txt_view);
        TextView from_content = (TextView)vi.findViewById(R.id.from_content_txt_view);
        TextView mess_date = (TextView)vi.findViewById(R.id.from_date_txt_view);
        TextView mess_time = (TextView)vi.findViewById(R.id.from_time_txt_view);
        ImageView image = (ImageView)vi.findViewById(R.id.from_pfp_view);

        User to = message.getTo();
        User from = message.getFrom();
        int pfp = from.getAvatar();
        String fromName = from.getFirstName() + " " + from.getLastName();
//        LocalDateTime date = message.getDate();
        String content = message.getContent();
        content = content.substring(0, 10) + "...";

        LocalDateTime date = message.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        mess_date.setText(date.format(formatter));

        formatter = DateTimeFormatter.ofPattern("HH:mm");
        mess_time.setText(date.format(formatter));

        from_name.setText(fromName);
        from_content.setText(content);

        if (pfp != -1){
            image.setImageResource(pfp);
        }

        return  vi;
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private Bitmap decodeSampledBitmap(String pathName,
                                       int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }
}
