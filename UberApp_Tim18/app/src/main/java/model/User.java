package model;

import java.io.Serializable;

public class User implements Serializable {
    //private int id;
    private String firstName;
    private String lastName;
    private int avatar;
    private double telephoneNumber;
    private String emailAddress;
    private String address;
    private String password;
    private int role;

    public User(String firstName, String lastName, int avatar, double telephoneNumber, String emailAddress, String address, String password, int role) {
        //this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    /*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
     */

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public double getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(double telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int password) {
        this.role = role;
    }

    public String getName() {
        return firstName+" "+lastName;
    }

}

