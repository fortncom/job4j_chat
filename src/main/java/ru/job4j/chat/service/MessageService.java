package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.domain.Message;
import ru.job4j.chat.repository.MessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessageService implements CommonService<Message> {

    private final MessageRepository messageRepository;

    public MessageService(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findByRoomName(String name) {
        return StreamSupport.stream(
                this.messageRepository.findByRoom(name).spliterator(), false
        ).collect(Collectors.toList());
    }

    @Override
    public List<Message> findAll() {
        return StreamSupport.stream(
                this.messageRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Message> findById(int id) {
        validateId(id);
        return this.messageRepository.findById(id);
    }

    @Override
    public Message create(Message message) {
        validateMessage(message);
        return this.messageRepository.save(message);
    }

    @Override
    public void update(Message message) {
        validateMessage(message);
        this.messageRepository.save(message);
    }

    @Override
    public void delete(int id) {
        validateId(id);
        Message message = new Message();
        message.setId(id);
        this.messageRepository.delete(message);
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
