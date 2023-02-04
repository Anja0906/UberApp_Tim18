package com.example.uberapp_tim18.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.uberapp_tim18.R;

import DTO.VehicleDTO;
import retrofit.DriverApi;
import retrofit.RetrofitService;

public class AboutVehicle extends Fragment {
    VehicleDTO vehicle;
    View rootView;
    EditText vehicleType;
    EditText model;
    EditText licenseNumber;
    EditText passengerSeats;
    ToggleButton babyTransport;
    ToggleButton petTransport;

    public AboutVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about_vehicle, container, false);
        initView();
        return rootView;
    }

    private void initView(){
        vehicleType = rootView.findViewById(R.id.vehicle_type_edit_text);
        vehicleType.setText(vehicle.getVehicleType());
        model = rootView.findViewById(R.id.model_edit_text);
        model.setText(vehicle.getModel());
        licenseNumber = rootView.findViewById(R.id.license_number_edit_text);
        licenseNumber.setText(vehicle.getLicenseNumber());
        passengerSeats = rootView.findViewById(R.id.passenger_seats_edit_text);
        passengerSeats.setText(String.valueOf(vehicle.getPassengerSeats()));
        babyTransport = rootView.findViewById(R.id.baby_transport_button);
        babyTransport.setChecked(vehicle.getBabyTransport());
        petTransport = rootView.findViewById(R.id.pet_transport_button);
        babyTransport.setChecked(vehicle.getPetTransport());
    }



}