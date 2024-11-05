package ru.lebedev.reminder.database.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.database.entity.SentStatus;

public interface ReminderRepository extends
        JpaRepository<Reminder, Long>,
        ReminderCustomRepository,
        QuerydslPredicateExecutor<Reminder> {

    @Query("select r from Reminder r where r.status = 'NOT_SENT' AND r.remind <= :now")
    List<Reminder> findReminderByRemindBefore(@Param("now") LocalDateTime now);

    @Modifying
    @Query("UPDATE Reminder r SET r.status = :status WHERE r.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") SentStatus status);


}
