package remainder.mapper.impl;

import org.springframework.stereotype.Component;

import ru.lebedev.remainder.database.entity.Reminder;
import ru.lebedev.remainder.dto.ReminderReadDto;
import ru.lebedev.remainder.mapper.Mapper;
@Component
public class ReminderReadMapper implements Mapper<Reminder, ReminderReadDto> {
	@Override
	public ReminderReadDto map(Reminder from) {
		return new ReminderReadDto(
			from.getId(),
			from.getTitle(),
			from.getDescription(),
			from.getRemind()
		);
	}
}
