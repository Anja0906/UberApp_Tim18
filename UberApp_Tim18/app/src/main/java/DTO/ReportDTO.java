package DTO;

import java.io.Serializable;
import java.util.HashMap;

public class ReportDTO implements Serializable {
    private HashMap<String, Integer> ridesPerDay;
    private HashMap<String, Double> kilometersPerDay;
    private HashMap<String, Long> moneyPerDay;
    private double moneySum;
    private double averageMoney;
    private double totalKilometers;

    public ReportDTO() {
    }

    public ReportDTO(HashMap<String, Integer> ridesPerDay, HashMap<String, Double> kilometersPerDay, HashMap<String, Long> moneyPerDay, double moneySum, double averageMoney, double totalKilometers) {
        this.ridesPerDay = ridesPerDay;
        this.kilometersPerDay = kilometersPerDay;
        this.moneyPerDay = moneyPerDay;
        this.moneySum = moneySum;
        this.averageMoney = averageMoney;
        this.totalKilometers = totalKilometers;
    }

    public HashMap<String, Integer> getRidesPerDay() {
        return ridesPerDay;
    }

    public void setRidesPerDay(HashMap<String, Integer> ridesPerDay) {
        this.ridesPerDay = ridesPerDay;
    }

    public HashMap<String, Double> getKilometersPerDay() {
        return kilometersPerDay;
    }

    public void setKilometersPerDay(HashMap<String, Double> kilometersPerDay) {
        this.kilometersPerDay = kilometersPerDay;
    }

    public HashMap<String, Long> getMoneyPerDay() {
        return moneyPerDay;
    }

    public void setMoneyPerDay(HashMap<String, Long> moneyPerDay) {
        this.moneyPerDay = moneyPerDay;
    }

    public double getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(double moneySum) {
        this.moneySum = moneySum;
    }

    public double getAverageMoney() {
        return averageMoney;
    }

    public void setAverageMoney(double averageMoney) {
        this.averageMoney = averageMoney;
    }

    public double getTotalKilometers() {
        return totalKilometers;
    }

    public void setTotalKilometers(double totalKilometers) {
        this.totalKilometers = totalKilometers;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "ridesPerDay=" + ridesPerDay +
                ", kilometersPerDay=" + kilometersPerDay +
                ", moneyPerDay=" + moneyPerDay +
                ", moneySum=" + moneySum +
                ", averageMoney=" + averageMoney +
                ", totalKilometers=" + totalKilometers +
                '}';
    }
}
