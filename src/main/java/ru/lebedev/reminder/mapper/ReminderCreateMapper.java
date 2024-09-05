package ru.lebedev.remainder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.remainder.database.entity.Reminder;
import ru.lebedev.remainder.dto.ReminderCreateDto;



@Mapper(componentModel = "spring", uses = {UserMapperHelper.class})
public interface ReminderCreateMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user", qualifiedByName = {"mapUserIdToUser"})
	Reminder reminderCreateDtoToReminder(ReminderCreateDto dto);
}


