package model;

import java.io.Serializable;

public class Note implements Serializable {
    private Integer id;
    private String message;
    private User user;

    public Note() {}

    public Note(Integer id, String message, User user) {
        this.id         = id;
        this.message    = message;
        this.user       = user;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
