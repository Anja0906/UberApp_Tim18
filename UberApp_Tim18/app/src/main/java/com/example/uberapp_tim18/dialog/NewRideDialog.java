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

import DTO.LocationSetDTO;
import DTO.ReasonDTO;
import DTO.RideResponseDTO;
import model.Ride;
import retrofit.RetrofitService;
import retrofit.RideApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRideDialog extends DialogFragment {

    private EditText editText;
    private Button confirmButton;
    private Button cancelButton;
    private RideResponseDTO rideResponseDTO;

    public NewRideDialog(RideResponseDTO rideResponseDTO) {
        this.rideResponseDTO = rideResponseDTO;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_ride_dialog, null);
        System.out.println(rideResponseDTO.toString());
        Button confirmButton = view.findViewById(R.id.confirm_button);


        EditText passangers = view.findViewById(R.id.name_txt_view);
        EditText estimatedTime = view.findViewById(R.id.phone_txt_view);
        EditText departure = view.findViewById(R.id.email_txt_view);
        EditText destination = view.findViewById(R.id.address_txt_view);
        EditText price = view.findViewById(R.id.pass_txt_view);


        LocationSetDTO locationSetDTO = rideResponseDTO.getLocations().iterator().next();

        passangers.setText(String.valueOf(rideResponseDTO.getPassengers().size()));
        estimatedTime.setText(String.valueOf(rideResponseDTO.getEstimatedTimeInMinutes()));
        departure.setText(locationSetDTO.getDeparture().getAddress());
        destination.setText(locationSetDTO.getDestination().getAddress());
        price.setText(String.valueOf(rideResponseDTO.getTotalCost()));
        Button reject = view.findViewById(R.id.reject_button);

        RetrofitService retrofitService = new RetrofitService();
        RideApi rideApi = retrofitService.getRetrofit().create(RideApi.class);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rideApi.acceptRide(rideResponseDTO.getId()).enqueue(new Callback<Void>() {
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

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reason = String.valueOf(editText.getText());
                rideApi.cancelRide(rideResponseDTO.getId(),new ReasonDTO(reason)).enqueue(new Callback<Void>() {
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

        builder.setView(view);
        return builder.create();
    }
}