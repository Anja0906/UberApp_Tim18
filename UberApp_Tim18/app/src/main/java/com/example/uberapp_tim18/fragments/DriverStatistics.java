package com.example.uberapp_tim18.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import com.example.uberapp_tim18.Activities.PassengerRegisterActivity;
import com.example.uberapp_tim18.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.ReportDTO;
import retrofit.DriverApi;
import retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverStatistics extends Fragment {
    View rootView;
    ReportDTO report;
    AppCompatButton buttonDates;

    BarChart barChart1;
    BarChart barChart2;
    BarChart barChart3;
    Date savedDate1;
    Date savedDate2;

    public DriverStatistics(ReportDTO reportDTO) {
        this.report = reportDTO;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_driver_statistics, container, false);
        initView();
        return rootView;

    }

    private void initView(){
        buttonDates = rootView.findViewById(R.id.button_dates);
        barChart1 = rootView.findViewById(R.id.chart1);
        barChart2 = rootView.findViewById(R.id.chart2);
        barChart3 = rootView.findViewById(R.id.chart3);
        setButtonListener();
        initCharts(report);
    }

    private void setButtonListener(){
        buttonDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectedValues();
            }
        });
    }

    //dobavljanje selektovanih vredonsti iz DatePicker-a
    private void getSelectedValues(){
        Calendar newCalendar = Calendar.getInstance();
        DatePicker datePicker1 = rootView.findViewById(R.id.datePicker1);
        DatePicker datePicker2 = rootView.findViewById(R.id.datePicker2);
        newCalendar.set(datePicker1.getYear(), datePicker1.getMonth(), datePicker1.getDayOfMonth());
        savedDate1 = newCalendar.getTime();
        newCalendar.set(datePicker2.getYear(), datePicker2.getMonth(), datePicker2.getDayOfMonth());
        savedDate2 = newCalendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date1 = dateFormat.format(savedDate1);
        String date2 = dateFormat.format(savedDate2);
        getReportsWithDates(date1, date2);
    }

    private void initCharts(ReportDTO reportDTO){
        initChart1(reportDTO);
        initChart2(reportDTO);
        initChart3(reportDTO);
    }

    //Rides per day
    private void initChart1(ReportDTO reportDTO){
        List<String> dates = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : reportDTO.getRidesPerDay().entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            dates.add(key);
            values.add(value);
        }

        List<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            barEntries.add(new BarEntry(i, values.get(i)));
        }

        initSingleChart(barEntries, "Rides per day", barChart1, dates);

    }

    //Money per day
    private void initChart2(ReportDTO reportDTO){
        List<String> dates = new ArrayList<>();
        List<Long> values = new ArrayList<>();
        for (Map.Entry<String, Long> entry : reportDTO.getMoneyPerDay().entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            dates.add(key);
            values.add(value);
        }

        List<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            barEntries.add(new BarEntry(i, values.get(i)));
        }

        initSingleChart(barEntries, "Income per day", barChart2, dates);
    }

    //Kilometers per day
    private void initChart3(ReportDTO reportDTO){
        List<String> dates = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for (Map.Entry<String, Double> entry : reportDTO.getKilometersPerDay().entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            dates.add(key);
            values.add(value);
        }
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            barEntries.add(new BarEntry(i, Float.parseFloat(values.get(i).toString())));
        }
        initSingleChart(barEntries, "Kilometers per day", barChart3, dates);
    }

    //Inicijalizacija svih chartova
    private void initSingleChart(List<BarEntry> barEntries, String title, BarChart barChart, List<String> dates){
        BarDataSet barDataSet = new BarDataSet(barEntries, title);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData barData = new BarData(dataSets);
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(15f);
        barDataSet.setValueTextSize(10f);
        barChart.invalidate();
        barChart.getDescription().setEnabled(false);
    }

    //Zahtev za promenu izvestaja prema selektovanim datumima
    private void getReportsWithDates(String date1, String date2){
        RetrofitService retrofitService = new RetrofitService();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String driverId = sharedPreferences.getString("id", "");
        String token = sharedPreferences.getString("jwt", "");
        retrofitService.onSavedUser(token);
        DriverApi driverApi = retrofitService.getRetrofit().create(DriverApi.class);
        driverApi.getReportsForDates(Integer.parseInt(driverId), date1, date2)
                .enqueue(new Callback<ReportDTO>() {
                    @Override
                    public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                        initCharts(response.body());

                    }
                    @Override
                    public void onFailure(Call<ReportDTO> call, Throwable t) {
                        Logger.getLogger(PassengerRegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }
}