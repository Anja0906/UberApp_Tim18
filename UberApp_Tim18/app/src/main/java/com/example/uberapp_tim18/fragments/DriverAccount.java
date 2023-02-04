package com.example.uberapp_tim18.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uberapp_tim18.Activities.ConfirmRegistration;
import com.example.uberapp_tim18.Activities.PassengerRegisterActivity;
import com.example.uberapp_tim18.R;

import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.DriverDTO;
import DTO.PassengerPostDTO;
import DTO.PassengerResponseDTO;
import retrofit.DriverApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAccount extends Fragment {
    EditText editName;
    EditText editSurname;
    EditText editEmail;
    EditText editAddress;
    EditText editTelephone;
    ImageView edit;
    ImageView confirmEditButton;
    ImageView cancelEditButton;
    View rootView;
    LinearLayout editLayout;
    LinearLayout confirmEdit;
    DriverDTO driver;

    RetrofitService retrofitService;
    SharedPreferences sharedPreferences;
    String driverId;
    String token;
    DriverApi driverApi;

    public DriverAccount(DriverDTO driver) {
        this.driver = driver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(inflater, container);
        initServices();
        initButtons();
        return rootView;
    }

    private void initView(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_driver_account, container, false);
        editName = rootView.findViewById(R.id.name_edit_text);
        editSurname = rootView.findViewById(R.id.surname_edit_text);
        editEmail = rootView.findViewById(R.id.email_edit_text);
        editAddress = rootView.findViewById(R.id.address_edit_text);
        editTelephone = rootView.findViewById(R.id.phone_edit_text);
        edit = rootView.findViewById(R.id.edit_data);
        confirmEditButton = rootView.findViewById(R.id.confirm_edit_data);
        cancelEditButton = rootView.findViewById(R.id.cancel_edit_data);
        editLayout = rootView.findViewById(R.id.edit);
        confirmEdit = rootView.findViewById(R.id.confirm_edit);
        initValues();
        setDisabled();
    }

    private void initServices(){
        retrofitService = new RetrofitService();
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        driverId = sharedPreferences.getString("id", "");
        token = sharedPreferences.getString("jwt", "");
        retrofitService.onSavedUser(token);
        driverApi = retrofitService.getRetrofit().create(DriverApi.class);
    }


    private void initButtons(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editLayout.setVisibility(View.GONE);
                confirmEdit.setVisibility(View.VISIBLE);
                setEnabled();
            }
        });
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
                editLayout.setVisibility(View.VISIBLE);
                confirmEdit.setVisibility(View.GONE);
                initValues();
                setDisabled();
            }
        });
        cancelEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initValues();
                editLayout.setVisibility(View.VISIBLE);
                confirmEdit.setVisibility(View.GONE);
                setDisabled();
            }
        });
    }

    private void initValues(){
        editName.setText(driver.getName());
        editSurname.setText(driver.getSurname());
        editEmail.setText(driver.getEmail());
        editAddress.setText(driver.getAddress());
        editTelephone.setText(driver.getTelephoneNumber());
    }

    private void setDisabled(){
        editName.setEnabled(false);
        editSurname.setEnabled(false);
        editEmail.setEnabled(false);
        editAddress.setEnabled(false);
        editTelephone.setEnabled(false);
    }

    private void setEnabled(){
        editName.setEnabled(true);
        editSurname.setEnabled(true);
        editEmail.setEnabled(true);
        editAddress.setEnabled(true);
        editTelephone.setEnabled(true);
    }

    private DriverDTO collectInputs(){
        String nameTxt = editName.getText().toString();
        String surnameTxt = editSurname.getText().toString();
        String addressTxt = editAddress.getText().toString();
        String emailTxt = editEmail.getText().toString();
        String telephoneNumberTxt = editTelephone.getText().toString();
        return new DriverDTO(driver.getId(), nameTxt, surnameTxt, driver.getProfilePicture(), telephoneNumberTxt, emailTxt, addressTxt);
    }

    private void sendRequest(){
        driverApi.sendChange(driver.getId(), collectInputs())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(rootView.getContext(), "You have sent the request to admin!", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(rootView.getContext(), "Save failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}