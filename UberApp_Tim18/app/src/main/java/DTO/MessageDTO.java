package DTO;

import java.io.Serializable;

import model.Message;


public class MessageDTO implements Serializable {

    private Integer receiverId;
    private String message;
    private String type;
    private Integer rideId;

    public MessageDTO() {}

    public MessageDTO(Integer receiverId, String message, String type, Integer rideId) {
        this.receiverId = receiverId;
        this.message    = message;
        this.type       = type;
        this.rideId     = rideId;
    }

    public MessageDTO(Message message) {
        this.receiverId = message.getReceiver().getId();
        this.message    = message.getMessage();
        this.type       = message.getMessageType();
        this.rideId     = message.getRide().getId();
    }
}
