package ru.lebedev.reminder.http.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.lebedev.reminder.dto.UserCreateEditDto;
import ru.lebedev.reminder.dto.UserReadDto;
import ru.lebedev.reminder.service.impl.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("${url.users}")
public class UserController {
	private final UserServiceImpl userService;
	@PostMapping("/create")
	public UserReadDto create(@RequestBody UserCreateEditDto dto) {
		return userService.create(dto);
		
	}
	
}
