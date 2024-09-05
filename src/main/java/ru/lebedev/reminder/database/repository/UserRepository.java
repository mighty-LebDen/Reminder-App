package ru.lebedev.remainder.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.lebedev.remainder.database.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
