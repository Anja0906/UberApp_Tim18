package DTO;

import java.io.Serializable;

public class PassengerResponseDTO implements Serializable {
    private String message;

    public PassengerResponseDTO() {
    }

    public PassengerResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
