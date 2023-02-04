package DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Role;
import model.User;

public class UserDTO implements Serializable {

    private Integer id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private boolean blocked;
    private List<String> roles;

    public UserDTO() {
    }

    public UserDTO(Integer id, String name, String surname, String profilePicture, String telephoneNumber, String email, String address, List<String> roles) {
        this.id                 = id;
        this.name               = name;
        this.surname            = surname;
        this.profilePicture     = profilePicture;
        this.telephoneNumber    = telephoneNumber;
        this.email              = email;
        this.address            = address;
        this.roles              = roles;
    }

//    public UserDTO(User user) {
//        this.id              = user.getId();
//        this.name            = user.getName();
//        this.surname         = user.getSurname();
//        this.profilePicture  = user.getProfilePicture();
//        this.telephoneNumber = user.getTelephoneNumber();
//        this.email           = user.getEmail();
//        this.address         = user.getAddress();
//        this.blocked         = user.isBlocked();
//        List<String> rolesStr = new ArrayList<>();
//        for (Role role:user.getRoles()) {
//            rolesStr.add(role.getName());
//        }
//    }

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
