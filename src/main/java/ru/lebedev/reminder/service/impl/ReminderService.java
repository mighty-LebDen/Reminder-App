package ru.lebedev.reminder.service.impl;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import ru.lebedev.reminder.database.entity.SentStatus;
import ru.lebedev.reminder.database.repository.ReminderRepository;
import ru.lebedev.reminder.dto.ReminderCreateEditDto;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.exception.ReminderNotFoundException;
import ru.lebedev.reminder.exception.UserNotFoundException;
import ru.lebedev.reminder.filters.DateAndTimeFilter;
import ru.lebedev.reminder.filters.SearchFilter;
import ru.lebedev.reminder.mapper.ReminderCreateMapper;
import ru.lebedev.reminder.mapper.ReminderReadMapper;
import ru.lebedev.reminder.service.UserService;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ReminderService {

    private final UserService userService;

    private final ReminderRepository reminderRepository;
    private final ReminderCreateMapper createMapper = Mappers.getMapper(ReminderCreateMapper.class);
    private final ReminderReadMapper readMapper = Mappers.getMapper(ReminderReadMapper.class);

    @Transactional
    public ReminderReadDto create(ReminderCreateEditDto reminderCreateDto) {
        return Optional.ofNullable(reminderCreateDto)
                       .map(createMapper::reminderCreateDtoToReminder)
                       .map(reminder -> {
                           var user = Optional.ofNullable(userService.findById(reminderCreateDto.userId()))
                                              .orElseThrow(() -> new UserNotFoundException(
                                                      reminderCreateDto.userId()));
                           reminder.setUser(user);
                           reminder.setStatus(SentStatus.NOT_SENT);
                           return reminder;
                       })
                       .map(reminderRepository::saveAndFlush)
                       .map(readMapper::reminderToReminderReadDto)
                       .orElseThrow(() -> new RuntimeException("error in create Remind"));
    }

    public List<ReminderReadDto> findAllWithPagination(Pageable pageable) {
        return reminderRepository.findAll(pageable)
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

    @Transactional
    public boolean delete(Long id) {
        reminderRepository.findById(id)
                          .map(reminder -> {
                              reminderRepository.delete(reminder);
                              reminderRepository.flush();
                              return true;
                          }).orElseThrow(() -> new ReminderNotFoundException(id));
        return false;
    }

    @Transactional
    public Optional<ReminderReadDto> update(Long id, ReminderCreateEditDto dto) {
        var reminder = reminderRepository.findById(id)
                                         .orElseThrow(() -> new ReminderNotFoundException(id));
        createMapper.updateEntity(dto, reminder);
        return Optional.of(reminderRepository.saveAndFlush(reminder))
                       .map(readMapper::reminderToReminderReadDto);
    }

    public List<ReminderReadDto> findAllByDateAndTimeFilter(DateAndTimeFilter filter) {
        return reminderRepository.findAllByFilter(filter)
                                 .stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

    public List<ReminderReadDto> findAllBySearchFilter(SearchFilter filter) {
        return reminderRepository.findAllBySearchFilter(filter)
                                 .stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

    public List<ReminderReadDto> sort(String direction, String field) {
        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(sortDirection, field);
        return reminderRepository.findAll(sort)
                                 .stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();
    }

    @Transactional
    public void updateStatus(Long id, SentStatus status) {
        reminderRepository.updateStatus(id, status);
    }

    public List<ReminderReadDto> findReminderByRemindBefore(LocalDateTime now) {
        return reminderRepository.findReminderByRemindBefore(now)
                                 .stream()
                                 .map(readMapper::reminderToReminderReadDto)
                                 .toList();

    }

}
