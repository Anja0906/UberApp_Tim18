package model;

import java.io.Serializable;

public class Vehicle  implements Serializable {
    private Integer id;

    private VehicleType vehicleType;
    private String model;
    private String licenseNumber;

    private Location currentLocation;
    private Integer passengerSeats;
    private Boolean babyTransport;
    private Boolean petTransport;

    public Vehicle() {}

    public Vehicle(Integer id, VehicleType vehicleType, String model, String licenseNumber, Location currentLocation, Integer passengerSeats, Boolean babyTransport, Boolean petTransport) {
        this.id              = id;
        this.vehicleType     = vehicleType;
        this.model           = model;
        this.licenseNumber   = licenseNumber;
        this.currentLocation = currentLocation;
        this.passengerSeats  = passengerSeats;
        this.babyTransport   = babyTransport;
        this.petTransport    = petTransport;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(Location currentLocation) {this.currentLocation = currentLocation;}

    public Integer getPassengerSeats() {
        return passengerSeats;
    }
    public void setPassengerSeats(Integer passengerSeats) {
        this.passengerSeats = passengerSeats;
    }

    public Boolean getBabyTransport() {
        return babyTransport;
    }
    public void setBabyTransport(Boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public Boolean getPetTransport() {
        return petTransport;
    }
    public void setPetTransport(Boolean petTransport) {
        this.petTransport = petTransport;
    }
}
