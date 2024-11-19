package ru.lebedev.reminder.service;

import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.dto.UserCreateEditDto;
import ru.lebedev.reminder.dto.UserReadDto;

public interface UserService {

    User findById(Long id);

    UserReadDto create(UserCreateEditDto dto);

    UserReadDto findByUsername(String email);

    UserReadDto findByTelegram(String telegram);

    UserReadDto update(Long id, UserCreateEditDto dto);

    UserReadDto addChatId(Long id, Long chatId);

}
