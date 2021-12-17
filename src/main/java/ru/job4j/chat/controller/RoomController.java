package ru.job4j.chat.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.domain.Message;
import ru.job4j.chat.model.domain.Room;
import ru.job4j.chat.model.dto.RoomDTO;
import ru.job4j.chat.model.validator.Operation;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/room")
@Validated
public class RoomController {

    private static final String API = "http://localhost:8080/message";

    private final RestTemplate restTemplate;

    private final RoomService roomService;
    private final PersonService personService;

    public RoomController(final RoomService roomService,
                          RestTemplate restTemplate, PersonService personService) {
        this.roomService = roomService;
        this.restTemplate = restTemplate;
        this.personService = personService;
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
                .body(StreamSupport.stream(roomService.findAll().spliterator(),
                        false).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                this.roomService.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Room with id %s not found.", id))),
                HttpStatus.OK);
    }

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Room> create(@Valid @RequestBody Room room) {
        return new ResponseEntity<>(
                this.roomService.create(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Room room) {
        roomService.update(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roomService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch")
    @Validated(Operation.OnPatch.class)
    public ResponseEntity<RoomDTO> patch(@Valid @RequestBody RoomDTO dto)
            throws InvocationTargetException, IllegalAccessException {
        Optional<Room> target = roomService.findById(dto.getId());
        if (target.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
        Room current = target.get();
        Optional.of(dto.getName()).ifPresent(current::setName);
        Optional.of(dto.getOwnerId()).ifPresent(
               id -> personService.findById(id).ifPresentOrElse(
                       current::setOwner, () -> dto.setOwnerId(current.getOwner().getId())));
        roomService.update(current);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
