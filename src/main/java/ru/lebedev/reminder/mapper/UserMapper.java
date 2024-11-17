package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.dto.UserCreateEditDto;
import ru.lebedev.reminder.dto.UserReadDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reminders", ignore = true)
    User toEntity(UserCreateEditDto dto);

    UserReadDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reminders", ignore = true)
    void updateEntity(UserCreateEditDto dto, @MappingTarget User user);

}
