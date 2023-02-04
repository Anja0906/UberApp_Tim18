package DTO;

import model.Review;

public class DriverReviewDTO {
    private Integer id;
    private Integer rating;
    private String comment;
    private PassengerIdEmailDTO passenger;
    private String type;


    public DriverReviewDTO(){}
    public DriverReviewDTO(Integer id, Integer rating, String comment, PassengerIdEmailDTO passenger) {
        this.id         = id;
        this.rating     = rating;
        this.comment    = comment;
        this.passenger  = passenger;
        this.type       = "driver";
    }

    public DriverReviewDTO(Review review, PassengerIdEmailDTO passenger){
        this(review.getId(),review.getRating(),review.getComment(), passenger);
    }
}
