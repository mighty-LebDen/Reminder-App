package ru.lebedev.reminder.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.lebedev.reminder.database.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
