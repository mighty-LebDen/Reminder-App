package ru.lebedev.reminder.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id %d not found", id));
    }

    public UserNotFoundException(String email) {
        super(String.format("User with email %s not found", email));
    }
}
