package ru.lebedev.reminder.database.repository.impl;

import static ru.lebedev.reminder.database.entity.QReminder.reminder;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.AllArgsConstructor;
import ru.lebedev.reminder.database.entity.QReminder;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.database.repository.ReminderCustomRepository;
import ru.lebedev.reminder.filters.Filter;
import ru.lebedev.reminder.filters.QPredicates;
import ru.lebedev.reminder.filters.ReminderSearchFilter;

@AllArgsConstructor
public class ReminderCustomRepositoryImpl implements ReminderCustomRepository {
    private final EntityManager entityManager;
    @Override
    public List<Reminder> findAllByFilter(Filter filter) {
        var predicates = QPredicates.builder()
                .add(filter.date(), reminder.remind::eq)
                .build();
        return new JPAQuery<>(entityManager)
                .select(reminder)
                .from(reminder)
                .where(predicates)
                .fetch();
    }

    @Override
    public List<Reminder> findAllBySearchFilter(ReminderSearchFilter filter) {
        var predicates = QPredicates.builder()
                .add(filter.title(), reminder.title::containsIgnoreCase)
                .add(filter.description(), reminder.description::containsIgnoreCase)
                //.add(filter.date(), reminder.remind::eq)
                .build();
        return new JPAQuery<>(entityManager)
                .select(reminder)
                .from(reminder)
                .where(predicates)
                .fetch();
    }

}
