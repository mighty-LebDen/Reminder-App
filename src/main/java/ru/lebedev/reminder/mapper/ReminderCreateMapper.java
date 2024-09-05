package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.dto.ReminderCreateDto;
import ru.lebedev.reminder.service.UserService;
import ru.lebedev.reminder.service.impl.UserServiceImpl;


@Mapper
public interface ReminderCreateMapper {
	@Mapping(target = "id", ignore = true)
	Reminder reminderCreateDtoToReminder(ReminderCreateDto dto);
}


