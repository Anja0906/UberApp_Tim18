package model;

public class Admin extends User{

    public Admin(Integer id, String name, String surname, int profilePicture, String telephoneNumber, String email, String address, boolean blocked, String password, Role role) {
        super(id, name, surname, profilePicture, telephoneNumber, email, address, blocked, password, Role.ADMIN);
    }

}
