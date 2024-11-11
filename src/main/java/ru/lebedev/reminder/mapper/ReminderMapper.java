package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.dto.ReminderCreateEditDto;
import ru.lebedev.reminder.dto.ReminderReadDto;

@Mapper
public interface ReminderMapper {

    @Mapping(target = "id", ignore = true)
    Reminder toEntity(ReminderCreateEditDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ReminderCreateEditDto dto, @MappingTarget Reminder reminder);

    @Mapping(target = "userReadDto", source = "user")
    ReminderReadDto toDto(Reminder reminder);



}


