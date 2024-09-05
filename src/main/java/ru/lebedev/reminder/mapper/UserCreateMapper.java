package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.dto.UserCreateEditDto;

@Mapper
public interface UserCreateMapper {
	@Mapping(target = "id", ignore = true)
	User toEntity(UserCreateEditDto dto);
	
}
