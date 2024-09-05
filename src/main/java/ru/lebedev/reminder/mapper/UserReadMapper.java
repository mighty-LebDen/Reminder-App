package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;

import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.dto.UserReadDto;

@Mapper
public interface UserReadMapper {
	UserReadDto toDto(User user);
}
