package ru.lebedev.reminder.database.repository;

import java.util.List;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.filters.Filter;
import ru.lebedev.reminder.filters.ReminderSearchFilter;

public interface ReminderCustomRepository {
    List<Reminder> findAllByFilter(Filter filter);
    List<Reminder> findAllBySearchFilter(ReminderSearchFilter filter);
}
