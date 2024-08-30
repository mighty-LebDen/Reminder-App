package remainder.mapper.impl;

//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.lebedev.remainder.database.entity.Reminder;
import ru.lebedev.remainder.database.entity.User;
import ru.lebedev.remainder.database.repository.UserRepository;
import ru.lebedev.remainder.dto.ReminderCreateDto;
import ru.lebedev.remainder.mapper.Mapper;
@Component
@RequiredArgsConstructor
public class ReminderCreateMapper implements Mapper<ReminderCreateDto, Reminder> {
	private final UserRepository userRepository;
	@Override
	public Reminder map(ReminderCreateDto from) {
		return Reminder.builder()
			.title(from.title())
			.description(from.description())
			.remind(from.remind())
			.user(getUser(from.userId()))
			.build();
	}
	private User getUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("User with %d, not found".formatted(id)));
	}
	public Reminder map(Reminder reminder, ReminderCreateDto dto){
		reminder.setTitle(dto.title());
		reminder.setDescription(dto.description());
		reminder.setRemind(dto.remind());
		return reminder;
	}
}
