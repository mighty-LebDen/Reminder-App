package remainder.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import ru.lebedev.remainder.database.repository.ReminderRepository;
import ru.lebedev.remainder.dto.ReminderCreateDto;
import ru.lebedev.remainder.dto.ReminderReadDto;
import ru.lebedev.remainder.mapper.impl.ReminderCreateMapper;
import ru.lebedev.remainder.mapper.impl.ReminderReadMapper;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReminderService {
	private final ReminderRepository reminderRepository;
	private final ReminderCreateMapper createMapper;
	private final ReminderReadMapper readMapper;
	
	@Transactional
	public ReminderReadDto create(ReminderCreateDto reminderCreateDto){
		return Optional.ofNullable(reminderCreateDto)
			.map(createMapper::map)
			.map(reminderRepository::saveAndFlush)
			.map(readMapper::map)
			.orElse(null);
	}
	@Transactional
	public boolean delete(Long id){
		return reminderRepository.findById(id)
			.map(reminder -> {
				reminderRepository.delete(reminder);
				reminderRepository.flush();
				return true;
			}).orElse(false);
	}
	@Transactional
	public Optional<ReminderReadDto> update(Long id, ReminderCreateDto dto){
		 return reminderRepository.findById(id)
			 .map(reminder -> createMapper.map(reminder, dto))
			 .map(reminderRepository::saveAndFlush)
			 .map(readMapper::map);
	}
	

	
}
