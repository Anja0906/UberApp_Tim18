package com.example.uberapp_tim18.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.uberapp_tim18.R;

import retrofit.RetrofitService;

public class RideReview extends AppCompatActivity {

    RatingBar ratingBar ;
    RatingBar ratingBar1 ;
    EditText editText;
    Button buttonCancel ;
    Button buttonConfirm ;
    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_review);
        initGui();
        cancelEvent();
        confirmEvent();
    }

    private void initGui(){
         ratingBar = findViewById(R.id.simpleRatingBar);
         ratingBar1 = findViewById(R.id.simpleRatingBar1);
         editText = findViewById(R.id.text_area);
         buttonCancel = findViewById(R.id.cancel_button);
         buttonConfirm = findViewById(R.id.confirm_button);
         retrofitService = new RetrofitService();
    }
    private void cancelEvent(){
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RideReview.this, PassengerMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void confirmEvent(){
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ratingVehicle = (int) ratingBar.getRating();
                int ratingDriver = (int) ratingBar1.getRating();
                String comment = String.valueOf(editText.getText());
                System.out.println(ratingVehicle);
                System.out.println(ratingDriver);
                System.out.println(comment);

            }
        });
    }
}