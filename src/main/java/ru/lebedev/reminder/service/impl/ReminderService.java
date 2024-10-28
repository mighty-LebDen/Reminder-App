package ru.lebedev.reminder.service.impl;

import static ru.lebedev.reminder.database.entity.QReminder.reminder;

import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.database.repository.ReminderRepository;
import ru.lebedev.reminder.dto.ReminderCreateDto;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.filters.Filter;
import ru.lebedev.reminder.filters.QPredicates;
import ru.lebedev.reminder.filters.ReminderSearchFilter;
import ru.lebedev.reminder.mapper.ReminderCreateMapper;
import ru.lebedev.reminder.mapper.ReminderReadMapper;
import ru.lebedev.reminder.service.UserService;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReminderService {

    private final UserService userService;

    private final ReminderRepository reminderRepository;
    private final ReminderCreateMapper createMapper = Mappers.getMapper(ReminderCreateMapper.class);
    private final ReminderReadMapper readMapper = Mappers.getMapper(ReminderReadMapper.class);

    @Transactional
    public ReminderReadDto create(ReminderCreateDto reminderCreateDto) {
        return Optional.ofNullable(reminderCreateDto)
                       .map(createMapper::reminderCreateDtoToReminder)
                       .map(reminder -> {
                           var user = Optional.ofNullable(userService.findById(reminderCreateDto.userId()))
                                              .orElseThrow(() -> new RuntimeException(
                                                      "bad create request"));
                           reminder.setUser(user);
                           return reminder;
                       })
                       .map(reminderRepository::saveAndFlush)
                       .map(readMapper::reminderToReminderReadDto)
                       .orElseThrow(() -> new RuntimeException("bad create request"));
    }

    public List<ReminderReadDto> findAll() {
        return reminderRepository.findAll().stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

    public List<ReminderReadDto> findAllWithPagination(Pageable pageable) {
        return reminderRepository.findAll(pageable)
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

    public ReminderReadDto findById(Long id) {
        return reminderRepository.findById(id)
                                 .map(readMapper::reminderToReminderReadDto)
                                 .orElseThrow(() -> new RuntimeException("No Remind with such id"));
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
                                         .orElseThrow(() -> new RuntimeException(
                                                 "No Remind with such id"));
        createMapper.updateEntity(dto, reminder);
        return Optional.of(reminderRepository.saveAndFlush(reminder))
                       .map(readMapper::reminderToReminderReadDto);
    }

    public List<ReminderReadDto> findAllByFilter(Filter filter) {

        return reminderRepository.findAllByFilter(filter)
                                 .stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .collect(Collectors.toList());
    }

    public List<ReminderReadDto> findAllBySearchFilter(ReminderSearchFilter filter) {
        return reminderRepository.findAllBySearchFilter(filter)
                                 .stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

    public List<ReminderReadDto> sort(String field) {
        Sort sort = Sort.by(Sort.Order.asc(field));
        return reminderRepository.findAll(sort)
                                 .stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

}
