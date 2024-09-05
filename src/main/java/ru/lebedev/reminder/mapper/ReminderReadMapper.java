package ru.lebedev.remainder.mapper;

import org.mapstruct.Mapper;

import ru.lebedev.remainder.database.entity.Reminder;
import ru.lebedev.remainder.dto.ReminderReadDto;
@Mapper
public interface ReminderReadMapper {
	ReminderReadDto reminderToReminderReadDto(Reminder reminder);
}
