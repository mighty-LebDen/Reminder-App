package ru.lebedev.reminder.http.controllers;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lebedev.reminder.dto.UserCreateEditDto;
import ru.lebedev.reminder.dto.UserReadDto;
import ru.lebedev.reminder.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("${url.user}")
public class UserController {
    private final UserService userServiceImpl;

    @PutMapping("/{id}")
    public UserReadDto update(
            @PathVariable @Positive Long id,
            @Validated @RequestBody UserCreateEditDto dto) {
        return userServiceImpl.update(id, dto);
    }
}
