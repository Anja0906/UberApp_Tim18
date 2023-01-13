package model;

import java.io.Serializable;

public class Route implements Serializable {
    private Integer id;
    private Integer kilometers;
    private double price;
    private Integer serialNumber;
    private Location departure;
    private Location destination;

    public Route() {
    }

    public Route(Integer id, Integer kilometers, double price, Integer serialNumber, Location departure, Location destination) {
        this.id = id;
        this.kilometers = kilometers;
        this.price = price;
        this.serialNumber = serialNumber;
        this.departure = departure;
        this.destination = destination;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Location getDeparture() {
        return departure;
    }

    public void setDeparture(Location departure) {
        this.departure = departure;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }
}
