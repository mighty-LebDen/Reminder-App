package ru.lebedev.reminder.exception;

import lombok.Getter;

@Getter
public class ReminderServiceException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public ReminderServiceException(String message, ExceptionStatus exceptionStatus) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

}
