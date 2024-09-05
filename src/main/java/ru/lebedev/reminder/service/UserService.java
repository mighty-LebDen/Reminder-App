package ru.lebedev.reminder.service;

import ru.lebedev.reminder.database.entity.User;

public interface UserService {
	User findById(Long id);
}
