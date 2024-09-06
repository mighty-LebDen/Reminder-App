package ru.lebedev.reminder.service.impl;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import ru.lebedev.reminder.database.repository.ReminderRepository;
import ru.lebedev.reminder.dto.ReminderCreateDto;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.mapper.ReminderCreateMapper;
import ru.lebedev.reminder.mapper.ReminderReadMapper;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReminderService {

    private final UserServiceImpl userService;

    private final ReminderRepository reminderRepository;
    private final ReminderCreateMapper createMapper = Mappers.getMapper(ReminderCreateMapper.class);
    private final ReminderReadMapper readMapper = Mappers.getMapper(ReminderReadMapper.class);

    @Transactional
    public ReminderReadDto create(ReminderCreateDto reminderCreateDto) {
        return Optional.ofNullable(reminderCreateDto)
                       .map(createMapper::reminderCreateDtoToReminder)
                       .map(reminder -> {
                           var user = userService.findById(reminderCreateDto.userId());
                           reminder.setUser(user);
                           return reminder;
                       })
                       .map(reminderRepository::saveAndFlush)
                       .map(readMapper::reminderToReminderReadDto)
                       .orElse(null);
    }

    public List<ReminderReadDto> findAll() {
        return reminderRepository.findAll().stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

    public ReminderReadDto findById(Long id) {
        return reminderRepository.findById(id)
                                 .map(readMapper::reminderToReminderReadDto)
                                 .orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    public boolean delete(Long id) {
        return reminderRepository.findById(id)
                                 .map(reminder -> {
                                     reminderRepository.delete(reminder);
                                     reminderRepository.flush();
                                     return true;
                                 }).orElse(false);
    }

    @Transactional
    public Optional<ReminderReadDto> update(Long id, ReminderCreateDto dto) {
        var reminder = reminderRepository.findById(id)
                                         .orElseThrow(() -> new RuntimeException());
        createMapper.updateEntity(dto, reminder);
        return Optional.of(reminderRepository.saveAndFlush(reminder))
                       .map(readMapper::reminderToReminderReadDto);
    }

}
