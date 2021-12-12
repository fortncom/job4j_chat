package ru.job4j.chat.model.dto;

public class RoomDTO {

    private int id;
    private String name;
    private int ownerId;

    public static RoomDTO of(int id, String name, int ownerId) {
        RoomDTO room = new RoomDTO();
        room.setId(id);
        room.setName(name);
        room.setOwnerId(ownerId);
        return room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Room{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", owner=" + ownerId
                + '}';
    }
}
