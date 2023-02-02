package model;

import java.util.Date;
import java.util.List;

public class Driver extends User{
    private boolean online;
    private WorkingTime workingTime;
    private List<Document> documents;
    private Vehicle vehicle;

    public Driver() {}

    public Driver(Integer id, String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String password, boolean blocked, boolean active, String verificationCode, String resetPasswordToken, Date timeOfResetPasswordToken, List<Role> roles, boolean online, WorkingTime workingTime, List<Document> documents, Vehicle vehicle) {
        super(id, name, surname, profilePicture, telephoneNumber, email, address, password, blocked, active, verificationCode, resetPasswordToken, timeOfResetPasswordToken, roles);
        this.online = online;
        this.workingTime = workingTime;
        this.documents = documents;
        this.vehicle = vehicle;
    }

    public boolean isOnline() {
        return online;
    }
    public void setOnline(boolean online) {
        this.online = online;
    }

    public WorkingTime getWorkingTime() {
        return workingTime;
    }
    public void setWorkingTime(WorkingTime workingTime) {
        this.workingTime = workingTime;
    }

    public List<Document> getDocuments() {
        return documents;
    }
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
