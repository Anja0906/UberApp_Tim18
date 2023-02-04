package DTO;

import java.util.Set;

import model.VehicleName;

public class FavoriteRideDTO {
    private int id;
    private String favoriteName;
    private Set<LocationSetDTO> locations;
    private Set<PassengerIdEmailDTO> passengers;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public FavoriteRideDTO() {
    }

    public FavoriteRideDTO(int id, String favoriteName, Set<LocationSetDTO> locations, Set<PassengerIdEmailDTO> passengers, VehicleName vehicleType, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.favoriteName = favoriteName;
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
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
}
