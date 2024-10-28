package ru.lebedev.reminder.database.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.lebedev.reminder.database.entity.Reminder;

public interface ReminderRepository extends
        JpaRepository<Reminder, Long>,
        ReminderCustomRepository,
        QuerydslPredicateExecutor<Reminder> {

    List<Reminder> findAllBy(Sort sort);

}
