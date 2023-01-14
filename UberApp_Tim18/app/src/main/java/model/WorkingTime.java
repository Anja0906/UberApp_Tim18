package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class WorkingTime  implements Serializable {
    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Driver driver;

    public WorkingTime() {}

    public WorkingTime(Integer id, LocalDateTime startTime, LocalDateTime endTime, Driver driver) {
        this.id         = id;
        this.startTime  = startTime;
        this.endTime    = endTime;
        this.driver     = driver;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
