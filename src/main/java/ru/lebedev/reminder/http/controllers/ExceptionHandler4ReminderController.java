package ru.lebedev.reminder.http.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Slf4j
@RestControllerAdvice(assignableTypes = {ReminderController.class})
public class ExceptionHandler4ReminderController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleGlobal(Exception ex) {
        return new ResponseEntity<>("bad request : " + ex.getLocalizedMessage(),
                                    HttpStatus.BAD_REQUEST);

    }

}
