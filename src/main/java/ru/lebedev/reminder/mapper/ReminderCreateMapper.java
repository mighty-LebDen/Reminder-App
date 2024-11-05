package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.dto.ReminderCreateEditDto;

@Mapper
public interface ReminderCreateMapper {

    @Mapping(target = "id", ignore = true)
    Reminder reminderCreateDtoToReminder(ReminderCreateEditDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ReminderCreateEditDto dto, @MappingTarget Reminder reminder);



}


