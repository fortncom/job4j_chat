package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.domain.Message;
import ru.job4j.chat.model.dto.MessageDTO;
import ru.job4j.chat.model.validator.Operation;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoomService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
@Validated
public class MessageController {

    private final MessageService messageService;
    private final PersonService personService;
    private final RoomService roomService;

    public MessageController(final MessageService messageService,
            final PersonService personService, final RoomService roomService) {
        this.messageService = messageService;
        this.personService = personService;
        this.roomService = roomService;
    }

    @GetMapping("")
    public List<Message> findByRoomName(@NotBlank @RequestParam String name) {
        return messageService.findByRoomName(name);
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                this.messageService.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Message with id %s not found.", id))),
                HttpStatus.OK);
    }

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Message> create(@Valid @RequestBody Message message) {
        return new ResponseEntity<>(
                this.messageService.create(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Message message) {
        messageService.update(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        messageService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch")
    @Validated(Operation.OnPatch.class)
    public MessageDTO patch(@Valid @RequestBody MessageDTO dto)
            throws InvocationTargetException, IllegalAccessException {
        Optional<Message> target = messageService.findById(dto.getId());
        if (target.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
        Message current = target.get();
        Optional.of(dto.getMessage()).ifPresent(current::setMessage);
        Optional.of(dto.getPersonId()).ifPresent(
                id -> personService.findById(id).ifPresentOrElse(
                        current::setPerson, () -> dto.setPersonId(current.getPerson().getId())));
        Optional.of(dto.getRoomId()).ifPresent(
                id -> roomService.findById(id).ifPresentOrElse(
                        current::setRoom, () -> dto.setRoomId(current.getRoom().getId())));
        messageService.update(current);
        return dto;
    }
}
