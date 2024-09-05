package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;

import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.dto.ReminderReadDto;
@Mapper
public interface ReminderReadMapper {
	ReminderReadDto reminderToReminderReadDto(Reminder reminder);
}
