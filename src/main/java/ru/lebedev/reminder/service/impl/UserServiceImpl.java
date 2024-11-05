package ru.lebedev.reminder.service.impl;

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
import ru.lebedev.reminder.exception.UserNotFoundException;
import ru.lebedev.reminder.mapper.UserCreateMapper;
import ru.lebedev.reminder.mapper.UserReadMapper;
import ru.lebedev.reminder.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper = Mappers.getMapper(UserCreateMapper.class);
    private final UserReadMapper userReadMapper = Mappers.getMapper(UserReadMapper.class);

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public UserReadDto create(UserCreateEditDto dto) {
        return Optional.ofNullable(dto)
                       .map(userCreateMapper::toEntity)
                       .map(userRepository::saveAndFlush)
                       .map(userReadMapper::toDto)
                       .orElseThrow(() -> new RuntimeException("error in creating User"));
    }

    @Override
    public UserReadDto findByUsername(String email) {
        return userRepository.findByUsername(email)
                             .map(userReadMapper::toDto)
                             .orElse(null);
    }

}
