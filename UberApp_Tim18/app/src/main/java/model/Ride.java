package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Ride implements Serializable {
    private Integer id;
    private LocalDateTime beginning;
    private LocalDateTime end;
    private double price;
    private boolean panic;
    private boolean babyInCar;
    private boolean petInCar;
    private boolean splitFair;
    private int duration;
    private Rejection rejection;
    private Driver driver;
    private Status status;
    private List<Message> messages;
    private List<Review> reviews;
    private List<Passenger> passengers;
    private Route route;

    public Ride() {}

    public Ride(LocalDateTime beginning, LocalDateTime end, double price, boolean panic, boolean babyInCar, boolean petInCar, boolean splitFair, int duration) {
        this.beginning  = beginning;
        this.end        = end;
        this.price      = price;
        this.panic      = panic;
        this.babyInCar  = babyInCar;
        this.petInCar   = petInCar;
        this.splitFair  = splitFair;
        this.duration   = duration;
    }


    public LocalDateTime getBeginning() {
        return beginning;
    }
    public void setBeginning(LocalDateTime beginning) {
        this.beginning = beginning;
    }

    public LocalDateTime getEnd() {
        return end;
    }
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPanic() {
        return panic;
    }
    public void setPanic(boolean panic) {
        this.panic = panic;
    }

    public boolean isBabyInCar() {
        return babyInCar;
    }
    public void setBabyInCar(boolean babyInCar) {
        this.babyInCar = babyInCar;
    }

    public boolean isPetInCar() {
        return petInCar;
    }
    public void setPetInCar(boolean petInCar) {
        this.petInCar = petInCar;
    }

    public boolean isSplitFair() {
        return splitFair;
    }
    public void setSplitFair(boolean splitFair) {
        this.splitFair = splitFair;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
