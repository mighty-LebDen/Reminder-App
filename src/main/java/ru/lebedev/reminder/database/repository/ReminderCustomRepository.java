package ru.lebedev.reminder.database.repository;

import java.util.List;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.filters.DateAndTimeFilter;
import ru.lebedev.reminder.filters.SearchFilter;

public interface ReminderCustomRepository {
    List<Reminder> findAllByFilter(DateAndTimeFilter filter);
    List<Reminder> findAllBySearchFilter(SearchFilter filter);
}
