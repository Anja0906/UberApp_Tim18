package com.example.uberapp_tim18.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.uberapp_tim18.R;

import DTO.ReasonDTO;
import DTO.RideResponseDTO;
import retrofit.RetrofitService;
import retrofit.RideApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PanicDialog extends DialogFragment {

    private EditText editText;
    private Button confirmButton;
    private Button cancelButton;
    private RideResponseDTO rideResponseDTO;

    public PanicDialog(RideResponseDTO rideResponseDTO) {
        this.rideResponseDTO = rideResponseDTO;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.panic_dialog, null);

        EditText editText = view.findViewById(R.id.text_area);
        Button confirmButton = view.findViewById(R.id.confirm_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        RetrofitService retrofitService = new RetrofitService();
        RideApi rideApi = retrofitService.getRetrofit().create(RideApi.class);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = String.valueOf(editText.getText());
                rideApi.activatePanic(rideResponseDTO.getId(),new ReasonDTO(reason)).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
