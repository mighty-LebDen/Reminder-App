package ru.lebedev.reminder.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.database.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
	}
}
