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
import ru.lebedev.reminder.exception.ExceptionStatus;
import ru.lebedev.reminder.exception.ReminderServiceException;
import ru.lebedev.reminder.filters.DateAndTimeFilter;
import ru.lebedev.reminder.filters.SearchFilter;
import ru.lebedev.reminder.mapper.ReminderMapper;
import ru.lebedev.reminder.service.UserService;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ReminderService {

    private final UserService userService;

    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper = Mappers.getMapper(ReminderMapper.class);

    @Transactional
    public ReminderReadDto create(ReminderCreateEditDto reminderCreateDto) {
        var userId = reminderCreateDto.userId();
        return Optional.of(reminderCreateDto)
                       .map(reminderMapper::toEntity)
                       .map(reminder -> {
                           var user = Optional.ofNullable(userService.findById(userId))
                                              .orElseThrow(() -> new ReminderServiceException(
                                                      "user with %d id not found".formatted(
                                                              userId),
                                                      ExceptionStatus.USER_NOT_FOUND_EXCEPTION
                                              ));
                           reminder.setUser(user);
                           reminder.setEmailSentStatus(SentStatus.NOT_SENT);
                           return reminder;
                       })
                       .map(reminderRepository::saveAndFlush)
                       .map(reminderMapper::toDto)
                       .orElseThrow(() -> new ReminderServiceException(
                               "remind create issue",
                               ExceptionStatus.REMINDER_CREATE_ISSUE
                       ));
    }

    public List<ReminderReadDto> findAllWithPagination(Pageable pageable) {
        return reminderRepository.findAll(pageable)
                                 .map(reminderMapper::toDto)
                                 .toList();
    }

    @Transactional
    public void delete(Long id) {
        reminderRepository.findById(id)
                          .map(reminder -> {
                              reminderRepository.delete(reminder);
                              reminderRepository.flush();
                              return true;
                          })
                          .orElseThrow(() -> new ReminderServiceException(
                                  "Remind with %d id, not found".formatted(id),
                                  ExceptionStatus.REMINDER_NOT_FOUND_EXCEPTION
                          ));
    }

    @Transactional
    public ReminderReadDto update(Long id, ReminderCreateEditDto dto) {
        var reminder = reminderRepository.findById(id)
                                         .orElseThrow(() -> new ReminderServiceException(
                                                 "Remind with %d id, not found".formatted(id),
                                                 ExceptionStatus.REMINDER_NOT_FOUND_EXCEPTION
                                         ));
        reminderMapper.updateEntity(dto, reminder);
        return Optional.of(reminderRepository.saveAndFlush(reminder))
                       .map(reminderMapper::toDto)
                       .orElseThrow(() -> new ReminderServiceException(
                               "remind update issue",
                               ExceptionStatus.REMINDER_UPDATE_ISSUE
                       ));
    }

    public List<ReminderReadDto> findAllByDateAndTimeFilter(DateAndTimeFilter filter) {
        return reminderRepository.findAllByFilter(filter)
                                 .stream()
                                 .map(reminderMapper::toDto)
                                 .toList();
    }

    public List<ReminderReadDto> findAllBySearchFilter(SearchFilter filter) {
        return reminderRepository.findAllBySearchFilter(filter)
                                 .stream()
                                 .map(reminderMapper::toDto)
                                 .toList();
    }

    public List<ReminderReadDto> sort(String direction, String field) {
        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(sortDirection, field);
        return reminderRepository.findAll(sort)
                                 .stream()
                                 .map(reminderMapper::toDto)
                                 .toList();
    }

    @Transactional
    public void updateEmailSentStatus(Long id, SentStatus status) {
        reminderRepository.updateEmailSentStatus(id, status);
    }

    @Transactional
    public void updateTelegramSentStatus(Long id, SentStatus status) {
        reminderRepository.updateTelegramSentStatus(id, status);
    }

    public List<ReminderReadDto> findReminderToEmailSent(LocalDateTime now) {
        return reminderRepository.findReminderToEmailSent(now)
                                 .stream()
                                 .map(reminderMapper::toDto)
                                 .toList();
    }

    public List<ReminderReadDto> findReminderToTelegramSent(LocalDateTime now) {
        return reminderRepository.findReminderToTelegramSent(now)
                                 .stream()
                                 .map(reminderMapper::toDto)
                                 .toList();
    }

}
