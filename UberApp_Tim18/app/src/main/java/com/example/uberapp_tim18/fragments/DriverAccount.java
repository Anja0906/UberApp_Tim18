package com.example.uberapp_tim18.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.uberapp_tim18.R;

public class DriverAccount extends Fragment {
    EditText editName;
    EditText editSurname;
    EditText editEmail;
    EditText editAddress;
    EditText editTelephone;
    View rootView;

    public DriverAccount() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(inflater, container);
        return rootView;
    }

    private void initView(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_driver_account, container, false);
        editName = rootView.findViewById(R.id.name_edit_text);
        editSurname = rootView.findViewById(R.id.surname_edit_text);
        editEmail = rootView.findViewById(R.id.email_edit_text);
        editAddress = rootView.findViewById(R.id.address_edit_text);
        editTelephone = rootView.findViewById(R.id.phone_edit_text);
        setDisabled();
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
}