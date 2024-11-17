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

    @Query("select r from Reminder r where r.emailSentStatus = 'NOT_SENT' AND r.remind <= :now")
    List<Reminder> findReminderToEmailSent(@Param("now") LocalDateTime now);

    @Query("select r from Reminder r join fetch r.user u "
           + "where r.telegramSentStatus = 'NOT_SENT' "
           + "AND u.chatId is not null "
           + "AND r.remind <= :now")
    List<Reminder> findReminderToTelegramSent(@Param("now") LocalDateTime now);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Reminder r SET r.emailSentStatus = :status WHERE r.id = :id")
    void updateEmailSentStatus(@Param("id") Long id, @Param("status") SentStatus status);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Reminder r SET r.telegramSentStatus = :status WHERE r.id = :id")
    void updateTelegramSentStatus(@Param("id") Long id, @Param("status") SentStatus status);


}
