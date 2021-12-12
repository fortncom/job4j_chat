package ru.job4j.chat.model.dto;

public class MessageDTO {

    private int id;
    private String message;
    private int personId;
    private int roomId;

    public static MessageDTO of(int id, String message, int personId, int roomId) {
        MessageDTO m = new MessageDTO();
        m.setId(id);
        m.setMessage(message);
        m.setPersonId(personId);
        m.setRoomId(roomId);
        return m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Message{"
                + "id=" + id
                + ", message='" + message + '\''
                + ", personId=" + personId
                + ", roomId=" + roomId
                + '}';
    }
}
