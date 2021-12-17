package ru.job4j.chat.model.dto;

import ru.job4j.chat.model.validator.Operation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoomDTO {

    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private int id;
    @Size(min = 2, max = 120, message = "Name must be between 2 and 120 characters")
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "ownerId must be filled", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class
    })
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
