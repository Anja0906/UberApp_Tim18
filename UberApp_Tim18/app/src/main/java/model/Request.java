package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Request extends User implements Serializable {
    private boolean accepted;

    public Request(Integer id, String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String password, boolean blocked, boolean active, String verificationCode, String resetPasswordToken, String timeOfResetPasswordToken, List<String> roles, boolean accepted) {
        super(id, name, surname, profilePicture, telephoneNumber, email, address, password, blocked, active, verificationCode, resetPasswordToken, timeOfResetPasswordToken, roles);
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
