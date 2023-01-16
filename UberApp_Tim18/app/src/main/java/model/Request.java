package model;

import java.io.Serializable;

public class Request extends User implements Serializable {
    private boolean accepted;

    public Request(Integer id, String name, String surname, int profilePicture, String telephoneNumber, String email, String address, boolean blocked, String password, Role role, boolean accepted) {
        super(id, name, surname, profilePicture, telephoneNumber, email, address, blocked, password, role);
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
