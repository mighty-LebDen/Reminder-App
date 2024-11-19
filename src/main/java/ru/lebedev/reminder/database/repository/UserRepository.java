package ru.lebedev.reminder.database.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.reminder.database.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByTelegram(String telegram);

}
