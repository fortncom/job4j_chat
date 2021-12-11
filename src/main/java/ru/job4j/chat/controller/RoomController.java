package ru.job4j.chat.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/room")
public class RoomController {

    private static final String API = "http://localhost:8080/message";

    private final RoomRepository roomRepository;

    private final RestTemplate restTemplate;

    public RoomController(final RoomRepository roomRepository, RestTemplate restTemplate) {
        this.roomRepository = roomRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<Message> findMessageByRoom(@RequestParam String name) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        return restTemplate.exchange(RequestEntity
                .method(HttpMethod.GET, API + "?name=" + name)
                .header("Authorization", token)
                        .build(),
                new ParameterizedTypeReference<List<Message>>() { }
        ).getBody();
    }

    @GetMapping("/")
    public ResponseEntity<List<Room>> findAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(StreamSupport.stream(roomRepository.findAll().spliterator(),
                        false).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        validateId(id);
        return new ResponseEntity<>(
                this.roomRepository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Room with id %s not found.", id))),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        validateRoom(room);
        return new ResponseEntity<>(
                this.roomRepository.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        validateRoom(room);
        this.roomRepository.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        validateId(id);
        Room room = new Room();
        room.setId(id);
        this.roomRepository.delete(room);
        return ResponseEntity.ok().build();
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
