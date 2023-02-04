package com.example.uberapp_tim18.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uberapp_tim18.R;

import DTO.DriverDTO;
import DTO.UserDTO;
import retrofit.DriverApi;
import retrofit.PassengerApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PassengerAccount extends Fragment {
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
    UserDTO user;

    RetrofitService retrofitService;
    SharedPreferences sharedPreferences;
    String userId;
    String token;
    PassengerApi passengerApi;

    public PassengerAccount(UserDTO userDTO) {
        user = userDTO;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        rootView = inflater.inflate(R.layout.fragment_passenger_account, container, false);
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
        userId = sharedPreferences.getString("id", "");
        token = sharedPreferences.getString("jwt", "");
        retrofitService.onSavedUser(token);
        passengerApi = retrofitService.getRetrofit().create(PassengerApi.class);
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
        editName.setText(user.getName());
        editSurname.setText(user.getSurname());
        editEmail.setText(user.getEmail());
        editAddress.setText(user.getAddress());
        editTelephone.setText(user.getTelephoneNumber());
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

    private UserDTO collectInputs(){
        String nameTxt = editName.getText().toString();
        String surnameTxt = editSurname.getText().toString();
        String addressTxt = editAddress.getText().toString();
        String emailTxt = editEmail.getText().toString();
        String telephoneNumberTxt = editTelephone.getText().toString();
        return new UserDTO(user.getId(), nameTxt, surnameTxt, user.getProfilePicture(), telephoneNumberTxt, emailTxt, addressTxt, false);
    }

    private void sendRequest(){
        passengerApi.sendChange(user.getId(), collectInputs())
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