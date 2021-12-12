package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.domain.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoomService implements CommonService<Room> {

    private final RoomRepository roomRepository;

    public RoomService(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> findAll() {
        return StreamSupport.stream(roomRepository.findAll()
                .spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Room> findById(int id) {
        validateId(id);
        return this.roomRepository.findById(id);
    }

    @Override
    public Room create(Room room) {
        validateRoom(room);
        return this.roomRepository.save(room);
    }

    @Override
    public void update(Room room) {
        validateRoom(room);
        this.roomRepository.save(room);
    }

    @Override
    public void delete(int id) {
        validateId(id);
        Room room = new Room();
        room.setId(id);
        this.roomRepository.delete(room);
    }

    private void validateRoom(Room room) {
        if (room.getName() == null || room.getOwner() == null) {
            throw new NullPointerException("Room: name and owner mustn't be empty");
        }
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Room id must not be less than 1");
        }
    }
}
