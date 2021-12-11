package ru.job4j.chat.exception;

public class UserValidateException extends RuntimeException {

    public UserValidateException(String message) {
        super(message);
    }
}