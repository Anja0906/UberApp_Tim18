package com.example.uberapp_tim18.fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.uberapp_tim18.Activities.PassengerMainActivity;
import com.example.uberapp_tim18.Activities.PassengerRegisterActivity;
import com.example.uberapp_tim18.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.LocationSetDTO;
import DTO.PassengerIdEmailDTO;
import DTO.RidePostDTO;
import DTO.RideResponseDTO;
import model.VehicleName;
import retrofit.RetrofitService;
import retrofit.RideApi;
import retrofit.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRide extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_ride, container, false);
        EditText inputText = rootView.findViewById(R.id.passengers);
        Button add = rootView.findViewById(R.id.add);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String json = sharedPref.getString("object", "");
        Gson gson = new Gson();
        LocationSetDTO locationSetDTO = gson.fromJson(json, LocationSetDTO.class);

        RetrofitService retrofitService = new RetrofitService();
        RideApi rideApi = retrofitService.getRetrofit().create(RideApi.class);

        Button finish = rootView.findViewById(R.id.next);
        finish.setOnClickListener(view -> {
            UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
            //promeniti kada dodju lokacije sa fragmenta
            Set<LocationSetDTO> locations = new HashSet<LocationSetDTO>();

            locations.add(locationSetDTO);
            //putnici sa beka
            Set<PassengerIdEmailDTO> passengers = new HashSet<PassengerIdEmailDTO>();
            RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            VehicleName vehicleName;
            switch (selectedId){
                case 0: vehicleName = VehicleName.KOMBI;
                case 1: vehicleName = VehicleName.STANDARDNO;
                default: vehicleName = VehicleName.LUKSUZNO;
            }
            passengers.add(new PassengerIdEmailDTO(4, "pera@gmail.com"));
            ToggleButton babyTransportToggle = (ToggleButton) rootView.findViewById(R.id.baby_transport_button);
            boolean babyTransport = babyTransportToggle.isChecked();
            ToggleButton petTransportToggle = (ToggleButton) rootView.findViewById(R.id.pet_transport_button);
            boolean petTransport = petTransportToggle.isChecked();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(currentDate);
            RidePostDTO ridePostDTO = new RidePostDTO(0, locations, passengers, vehicleName, babyTransport, petTransport, date);
            rideApi.save(ridePostDTO)
                    .enqueue(new Callback<RideResponseDTO>() {
                        @Override
                        public void onResponse(Call<RideResponseDTO> call, Response<RideResponseDTO> response) {
                            Toast.makeText(getActivity(), "Voznja je porucena", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), PassengerMainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<RideResponseDTO> call, Throwable t) {
                            Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }


                    });
        });
        return rootView;
    }



}