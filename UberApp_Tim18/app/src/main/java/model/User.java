package model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private Integer id;
    private String name;
    private String surname;
    private int profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private boolean blocked;
    private String password;
    private Role role;

    public User(){}

    public User(Integer id, String name, String surname, int profilePicture, String telephoneNumber, String email, String address, boolean blocked, String password, Role role) {
        this.id              = id;
        this.name            = name;
        this.surname         = surname;
        this.profilePicture  = profilePicture;
        this.telephoneNumber = telephoneNumber;
        this.email           = email;
        this.address         = address;
        this.blocked         = blocked;
        this.password        = password;
        this.role            = role;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(int profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }
    public void setTelephoneNumber(String telephoneNumber) {this.telephoneNumber = telephoneNumber;}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isBlocked() {
        return blocked;
    }
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(@Nullable Object _obj) {
        User obj = (User) _obj;
        return Objects.equals(this.name, obj.getName()) &&
                Objects.equals(this.surname, obj.getSurname()) &&
                Objects.equals(this.profilePicture, obj.getProfilePicture()) &&
                Objects.equals(this.telephoneNumber, obj.getTelephoneNumber()) &&
                Objects.equals(this.email, obj.getEmail()) &&
                Objects.equals(this.address, obj.getAddress()) &&
                Objects.equals(this.password, obj.getPassword()) &&
                Objects.equals(this.role, obj.getRole());
    }
}