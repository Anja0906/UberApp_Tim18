package DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Set;

import model.Status;
import model.VehicleName;

public class RideResponseDTO implements Serializable{

    private int id;
    private String startTime;
    private String endTime;
    private long totalCost;
    private PassengerIdEmailDTO driver;
    private Set<PassengerIdEmailDTO> passengers;
    private int estimatedTimeInMinutes;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private RejectionDTO rejection;
    private Set<LocationSetDTO> locations;
    private Status status;
    private String scheduledTime;


    public RideResponseDTO(int id, String startTime, String endTime, long totalCost, PassengerIdEmailDTO driver, Set<PassengerIdEmailDTO> passengers, int estimatedTimeInMinutes, VehicleName vehicleType, boolean babyTransport, boolean petTransport, RejectionDTO rejection, Set<LocationSetDTO> locations, Status status, String scheduledTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.rejection = rejection;
        this.locations = locations;
        this.status = status;
        this.scheduledTime = scheduledTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public PassengerIdEmailDTO getDriver() {
        return driver;
    }

    public void setDriver(PassengerIdEmailDTO driver) {
        this.driver = driver;
    }

    public Set<PassengerIdEmailDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<PassengerIdEmailDTO> passengers) {
        this.passengers = passengers;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public VehicleName getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleName vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    public RejectionDTO getRejection() {
        return rejection;
    }

    public void setRejection(RejectionDTO rejection) {
        this.rejection = rejection;
    }

    public Set<LocationSetDTO> getLocations() {
        return locations;
    }

    public void setLocations(Set<LocationSetDTO> locations) {
        this.locations = locations;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "RideResponseDTO{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", totalCost=" + totalCost +
                ", driver=" + driver +
                ", passengers=" + passengers +
                ", estimatedTimeInMinutes=" + estimatedTimeInMinutes +
                ", vehicleType=" + vehicleType +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                ", rejection=" + rejection +
                ", locations=" + locations +
                ", status=" + status +
                ", scheduledTime='" + scheduledTime + '\'' +
                '}';
    }
}
