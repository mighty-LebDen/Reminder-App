package ru.lebedev.reminder.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.lebedev.reminder.http.controllers.ReminderController;

@Slf4j
@RestControllerAdvice(assignableTypes = {ReminderController.class})
public class ExceptionHandler4ReminderController {

    @ExceptionHandler(ReminderServiceException.class)
    public ResponseEntity<String> handleReminderNotFoundException(ReminderServiceException e) {
        log.error("Error occurred: {}, status: {}", e.getMessage(), e.getExceptionStatus());
        return ResponseEntity.status(BAD_REQUEST).body(e.getExceptionStatus().name());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Unexpected error occurred", e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Invalid argument", e);
        return ResponseEntity.status(BAD_REQUEST).body("Invalid argument");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body("Validation error: " + ex.getMessage());
    }

}
