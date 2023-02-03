package com.example.uberapp_tim18.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uberapp_tim18.Activities.ForgotPasswordActivity;
import com.example.uberapp_tim18.Activities.UserLoginActivity;
import com.example.uberapp_tim18.R;

import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.ResetPasswordDTO;
import model.User;
import retrofit.PassengerApi;
import retrofit.RetrofitService;
import retrofit.UserRetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordCode extends Fragment {


    private User user;

    EditText code;
    EditText newPass;
    EditText confirmNewPass;
    Button button;

    View rootView;
    PassengerApi passengerApi;
    UserRetrofitService retrofitService;

    public ForgotPasswordCode(User user) {
        this.user = user;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initView(inflater, container);
        return rootView;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_forgot_password_code, container, false);
        code = rootView.findViewById(R.id.code);
        newPass = rootView.findViewById(R.id.passenger_password);
        confirmNewPass = rootView.findViewById(R.id.passenger_password_confirm);
        button = rootView.findViewById(R.id.confirmPass);
        UserRetrofitService retrofitService = new UserRetrofitService();
        passengerApi = retrofitService.getRetrofit().create(PassengerApi.class);
        responseOnInit();
        listenerOnClick();
    }

    public void responseOnInit() {
        passengerApi.resetPassRequest(user.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Logger.getLogger(ForgotPasswordActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }

    public void listenerOnClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPasswordDTO passwordDTO = new ResetPasswordDTO(newPass.getText().toString(), code.getText().toString());
                passengerApi.resetPass(user.getId(), passwordDTO).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast toast = Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Logger.getLogger(ForgotPasswordActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
            }
        });
    }

}

