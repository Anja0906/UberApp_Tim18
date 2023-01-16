package DTO;

import java.util.Set;

import model.VehicleName;

public class RidePostDTO {
    private Integer id;
    private Set<LocationSetDTO> locations;
    private Set<PassengerIdEmailDTO> passengers;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private String scheduledTime;

    public RidePostDTO(Integer id, Set<LocationSetDTO> locations, Set<PassengerIdEmailDTO> passengers, VehicleName vehicleType, boolean babyTransport, boolean petTransport, String scheduledTime) {
        this.id = id;
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.scheduledTime = scheduledTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<LocationSetDTO> getLocations() {
        return locations;
    }

    public void setLocations(Set<LocationSetDTO> locations) {
        this.locations = locations;
    }

    public Set<PassengerIdEmailDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<PassengerIdEmailDTO> passengers) {
        this.passengers = passengers;
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

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
