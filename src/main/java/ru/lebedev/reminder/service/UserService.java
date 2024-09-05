package ru.lebedev.reminder.service;

import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.dto.UserReadDto;

public interface UserService {
	User findById(Long id);
	UserReadDto create(UserCreateEditDto dto);
}
