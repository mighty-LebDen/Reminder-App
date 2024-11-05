package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.MappingConstants;
import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.dto.UserReadDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserReadMapper {
	UserReadDto toDto(User user);
}
