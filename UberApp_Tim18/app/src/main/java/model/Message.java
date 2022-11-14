package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    public static enum MessageType {
        PANIC,
        RIDE,
        SUPPORT
    }

    private User from;
    private User to;
    private String content;
    private LocalDateTime date;
    private MessageType type;

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", type=" + type +
                '}';
    }

    public Message(User from, User to, String content, LocalDateTime date, MessageType type) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.date = date;
        this.type = type;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public MessageType getType() {
        return type;
    }
}
