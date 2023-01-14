package model;

import java.io.Serializable;

public class Review implements Serializable {
    private Integer id;
    private Integer rating;
    private String comment;
    private Passenger passenger;

    public Review() {
    }

    public Review(Integer id, Integer rating, String comment, Passenger passenger) {
        this.id         = id;
        this.rating     = rating;
        this.comment    = comment;
        this.passenger  = passenger;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Passenger getPassenger() {
        return passenger;
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
