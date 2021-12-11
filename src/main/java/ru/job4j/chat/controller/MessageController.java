package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.repository.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("")
    public List<Message> findByRoomName(@RequestParam String name) {
        return StreamSupport.stream(
                this.messageRepository.findByRoom(name).spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return StreamSupport.stream(
                this.messageRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        validateId(id);
        return new ResponseEntity<>(
                this.messageRepository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Message with id %s not found.", id))),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        validateMessage(message);
        return new ResponseEntity<>(
                this.messageRepository.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        validateMessage(message);
        this.messageRepository.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        validateId(id);
        Message message = new Message();
        message.setId(id);
        this.messageRepository.delete(message);
        return ResponseEntity.ok().build();
    }

    private void validateMessage(Message message) {
        if (message.getMessage() == null || message.getPerson() == null
                || message.getRoom() == null) {
            throw new NullPointerException("Message: message and person and room mustn't be empty");
        }
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Message id must not be less than 1");
        }
    }
}
