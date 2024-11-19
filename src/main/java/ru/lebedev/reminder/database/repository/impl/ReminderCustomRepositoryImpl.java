package ru.lebedev.reminder.database.repository.impl;

import static ru.lebedev.reminder.database.entity.QReminder.reminder;
import static ru.lebedev.reminder.database.entity.QUser.*;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.database.repository.ReminderCustomRepository;
import ru.lebedev.reminder.filters.DateTimeFilter;
import ru.lebedev.reminder.filters.QPredicates;
import ru.lebedev.reminder.filters.SearchFilter;

@AllArgsConstructor
public class ReminderCustomRepositoryImpl implements ReminderCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Reminder> findAllByDateTimeFilter(DateTimeFilter filter) {
        var predicates = QPredicates.builder()
                                    .add(
                                            Optional.ofNullable(filter.dateBefore())
                                                    .map(date -> filter.timeBefore() == null
                                                            ? date.atStartOfDay()
                                                            : date.atTime(filter.timeBefore()))
                                                    .orElse(null),
                                            Optional.ofNullable(filter.dateAfter())
                                                    .map(date -> filter.timeAfter() == null
                                                            ? date.plusDays(1)
                                                                  .atStartOfDay()
                                                                  .minusNanos(1L)
                                                            : date.atTime(filter.timeAfter()))
                                                    .orElse(LocalDateTime.now()),
                                            reminder.remind::between
                                    )
                                    .add(
                                            Optional.ofNullable(filter.timeBefore())
                                                    .map(date -> filter.dateBefore() == null
                                                            ? date.atDate(LocalDate.now())
                                                            : date.atDate(filter.dateBefore()))
                                                    .orElse(null),
                                            Optional.ofNullable(filter.timeAfter())
                                                    .map(date -> filter.dateAfter() == null
                                                            ? date.atDate(LocalDate.now())
                                                            : date.atDate(filter.dateAfter()))
                                                    .orElse(null),
                                            reminder.remind::between
                                    )
                                    .build();

        return new JPAQuery<>(entityManager)
                .select(reminder)
                .from(reminder).join(reminder.user, user).fetchJoin()
                .where(predicates)
                .fetch();
    }

    @Override
    public List<Reminder> findAllBySearchFilter(SearchFilter filter) {
        var predicates = QPredicates.builder()
                                    .add(filter.title(), reminder.title::containsIgnoreCase)
                                    .add(
                                            filter.description(),
                                            reminder.description::containsIgnoreCase
                                    )
                                    .add(
                                            Optional.ofNullable(filter.date())
                                                    .map(date -> filter.time() == null
                                                            ? date.atStartOfDay()
                                                            : date.atTime(filter.time()))
                                                    .orElse(null),
                                            reminder.remind::after
                                    )
                                    .add(
                                            Optional.ofNullable(filter.time())
                                                    .map(time -> filter.date() == null
                                                            ? time.atDate(LocalDate.now())
                                                            : time.atDate(filter.date()))
                                                    .orElse(null), reminder.remind::after)
                                    .build();

        return new JPAQuery<>(entityManager)
                .select(reminder)
                .from(reminder).join(reminder.user, user).fetchJoin()
                .where(predicates)
                .fetch();
    }

}
