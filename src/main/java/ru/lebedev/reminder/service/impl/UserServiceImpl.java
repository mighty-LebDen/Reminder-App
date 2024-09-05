package ru.lebedev.reminder.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.database.repository.UserRepository;
import ru.lebedev.reminder.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
	}
}
