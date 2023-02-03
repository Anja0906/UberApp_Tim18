package model;

import java.util.Date;
import java.util.List;

public class Admin extends User{

    public Admin(Integer id, String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String password, boolean blocked, boolean active, String verificationCode, String resetPasswordToken, String timeOfResetPasswordToken, List<String> roles) {
        super(id, name, surname, profilePicture, telephoneNumber, email, address, password, blocked, active, verificationCode, resetPasswordToken, timeOfResetPasswordToken, roles);
    }
}
