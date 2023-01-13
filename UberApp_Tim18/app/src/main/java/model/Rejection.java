package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Rejection implements Serializable {
    private Integer id;
    private String reason;
    private LocalDateTime timeOfRejection;
    private User user;

    public Rejection() {
    }

    public Rejection(Integer id, String reason, LocalDateTime timeOfRejection, User user) {
        this.id = id;
        this.reason = reason;
        this.timeOfRejection = timeOfRejection;
        this.user = user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getTimeOfRejection() {
        return timeOfRejection;
    }

    public void setTimeOfRejection(LocalDateTime timeOfRejection) {
        this.timeOfRejection = timeOfRejection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
