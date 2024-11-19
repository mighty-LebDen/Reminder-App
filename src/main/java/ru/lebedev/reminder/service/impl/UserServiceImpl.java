package ru.lebedev.reminder.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.database.repository.UserRepository;
import ru.lebedev.reminder.dto.UserCreateEditDto;
import ru.lebedev.reminder.dto.UserReadDto;
import ru.lebedev.reminder.exception.ExceptionStatus;
import ru.lebedev.reminder.exception.ReminderServiceException;
import ru.lebedev.reminder.mapper.UserMapper;
import ru.lebedev.reminder.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new ReminderServiceException(
                                     "user with %d id not found".formatted(id),
                                     ExceptionStatus.USER_NOT_FOUND_EXCEPTION
                             ));
    }

    @Override
    @Transactional
    public UserReadDto create(@NotNull UserCreateEditDto dto) {
        return Optional.of(dto)
                       .map(userMapper::toEntity)
                       .map(userRepository::saveAndFlush)
                       .map(userMapper::toDto)
                       .orElseThrow(() -> new ReminderServiceException(
                               "error in creating User",
                               ExceptionStatus.USER_CREATE_ISSUE
                       ));
    }

    @Override
    public UserReadDto findByUsername(String email) {
        return userRepository.findByUsername(email)
                             .map(userMapper::toDto)
                             .orElse(null);
    }

    public UserReadDto findByTelegram(String telegram) {
        return userRepository.findByTelegram(telegram)
                             .map(userMapper::toDto)
                             .orElse(null);
    }

    @Transactional
    @Override
    public UserReadDto update(Long id, UserCreateEditDto dto) {
        var maybeUser = userRepository.findById(id)
                                      .orElseThrow(() -> new ReminderServiceException(
                                              "User with %d id, not found".formatted(id),
                                              ExceptionStatus.USER_NOT_FOUND_EXCEPTION
                                      ));
        userMapper.updateEntity(dto, maybeUser);
        return Optional.of(userRepository.saveAndFlush(maybeUser))
                       .map(userMapper::toDto)
                       .orElseThrow(() -> new ReminderServiceException(
                               "User update issue",
                               ExceptionStatus.USER_UPDATE_ISSUE
                       ));
    }

    @Transactional
    @Override
    public UserReadDto addChatId(Long id, Long chatId) {
        var maybeUser = userRepository.findById(id)
                                      .orElseThrow(() -> new ReminderServiceException(
                                              "User with %d id, not found".formatted(id),
                                              ExceptionStatus.USER_NOT_FOUND_EXCEPTION
                                      ));
        maybeUser.setChatId(chatId);
        return Optional.of(userRepository.saveAndFlush(maybeUser))
                       .map(userMapper::toDto)
                       .orElseThrow(() -> new ReminderServiceException(
                               "User update issue",
                               ExceptionStatus.USER_UPDATE_ISSUE
                       ));
    }

}
