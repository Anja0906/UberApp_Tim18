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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.uberapp_tim18.Activities.PassengerMainActivity;
import com.example.uberapp_tim18.Activities.PassengerRegisterActivity;
import com.example.uberapp_tim18.R;
import com.google.gson.Gson;
import com.shuhart.stepview.StepView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRide extends Fragment {
    //delovi GUIja
    View rootView;
    StepView stepView;
    RelativeLayout layout1;
    RelativeLayout layout2;
    RelativeLayout layout3;
    TimePicker timePicker;
    RadioGroup radioGroup;
    ToggleButton babyTransportToggle;
    ToggleButton petTransportToggle;

    //parametri neophodni za voznju
    Date dateTimeOfRide;
    Set<PassengerIdEmailDTO> passengers = new HashSet<>();
    LocationSetDTO locationSetDTO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.initGUI(inflater, container);
        this.setButtons();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String json = sharedPref.getString("object", "");
        Gson gson = new Gson();
        locationSetDTO = gson.fromJson(json, LocationSetDTO.class);
        return rootView;
    }

    //inicijalizacija GUIja
    private void initGUI(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_create_ride, container, false);
        layout1 = rootView.findViewById(R.id.stepper1);
        layout2 = rootView.findViewById(R.id.stepper2);
        layout3 = rootView.findViewById(R.id.stepper3);
        timePicker = rootView.findViewById(R.id.timePicker);
        radioGroup = rootView.findViewById(R.id.radio_group);
        babyTransportToggle = (ToggleButton) rootView.findViewById(R.id.baby_transport_button);
        petTransportToggle = (ToggleButton) rootView.findViewById(R.id.pet_transport_button);
        initStepper();
    }

    //inicijalizacija step view-a
    private void initStepper(){
        stepView = rootView.findViewById(R.id.step_view);
        setStep1();
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Time");
        steps.add("Vehicle type");
        steps.add("Other");
        stepView.setSteps(steps);
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                System.out.println(step);
                switch (step){
                    case 0: setStep1();
                    case 1: setStep2();
                    case 2: setStep3();
                }
            }
        });
    }

    //inicijalizacija listenera za dugmad
    private void setButtons(){
        Button button1 = rootView.findViewById(R.id.next1);
        Button button2 = rootView.findViewById(R.id.next2);
        Button button3 = rootView.findViewById(R.id.next3);
        Button buttonCancel = rootView.findViewById(R.id.cancel);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setStep2();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStep3();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               orderRide();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStep1();
            }
        });
    }

    //prelazak na korak broj 1
    private void setStep1() {
        stepView.go(0, false);
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
    }

    //prelazak na korak broj 2
    private void setStep2() {
        if (this.checkSelectedTime()){
            dateTimeOfRide = this.getSelectedTime().getTime();
            stepView.go(1, false);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.GONE);
        }
        else{
            Toast.makeText(getActivity(), "You cannot order a ride for more than 5 hours from now!", Toast.LENGTH_LONG).show();
        }
    }

    //prelazak na korak broj 3
    private void setStep3() {
        if (this.checkSelectedVehicleType()){
            stepView.go(2, false);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(getActivity(), "Please select the vehicle type!", Toast.LENGTH_SHORT).show();
        }

    }

    //dobavljanje selektovanog vremena
    private Calendar getSelectedTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    //provera da li je odabrano vreme u narednih 5 sati
    private boolean checkSelectedTime(){
        Calendar now = Calendar.getInstance();
        Calendar selected = getSelectedTime();
        long difference = selected.getTimeInMillis() - now.getTimeInMillis();
        long fiveHoursInMilliseconds = 5 * 60 * 60 * 1000;

        return difference <= fiveHoursInMilliseconds;
    }

    //provera selektovanog tipa vozila
    private boolean checkSelectedVehicleType(){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        return selectedId != -1;
    }

    //porucivanje voznje
    private void orderRide(){
        //izdebagovati sa backendom
        RetrofitService retrofitService = new RetrofitService();
        RideApi rideApi = retrofitService.getRetrofit().create(RideApi.class);
        Set<LocationSetDTO> locations = new HashSet<LocationSetDTO>();
        locations.add(locationSetDTO);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        VehicleName vehicleName;
        switch (selectedId){
            case 0: vehicleName = VehicleName.KOMBI;
            case 1: vehicleName = VehicleName.STANDARDNO;
            default: vehicleName = VehicleName.LUKSUZNO;
        }
        boolean babyTransport = babyTransportToggle.isChecked();
        boolean petTransport = petTransportToggle.isChecked();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(dateTimeOfRide);
        RidePostDTO ridePostDTO = new RidePostDTO(0, locations, passengers, vehicleName, babyTransport, petTransport, date);
        rideApi.save(ridePostDTO)
                .enqueue(new Callback<RideResponseDTO>() {
                    @Override
                    public void onResponse(Call<RideResponseDTO> call, Response<RideResponseDTO> response) {
                        System.out.println(ridePostDTO);
                        Toast.makeText(getActivity(), "Voznja je porucena", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), PassengerMainActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<RideResponseDTO> call, Throwable t) {
                        Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }
}