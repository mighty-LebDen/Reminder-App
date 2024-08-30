package remainder.database.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import ru.lebedev.remainder.database.entity.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
	List<Reminder> findAllBy(Sort sort);
}
