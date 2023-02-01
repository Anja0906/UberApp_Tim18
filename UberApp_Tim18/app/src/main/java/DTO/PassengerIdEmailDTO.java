package DTO;

import java.io.Serializable;

public class PassengerIdEmailDTO  implements Serializable {
    private Integer id;
    private String email;

    public PassengerIdEmailDTO(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
