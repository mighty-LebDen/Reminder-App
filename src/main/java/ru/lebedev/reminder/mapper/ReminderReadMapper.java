package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.dto.ReminderReadDto;

@Mapper
public interface ReminderReadMapper {

    @Mapping(target = "userReadDto", source = "user")
    ReminderReadDto reminderToReminderReadDto(Reminder reminder);

}
