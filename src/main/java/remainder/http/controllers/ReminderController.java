package remainder.http.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.lebedev.remainder.dto.ReminderCreateDto;
import ru.lebedev.remainder.dto.ReminderReadDto;
import ru.lebedev.remainder.service.ReminderService;

@RestController
@AllArgsConstructor
@RequestMapping("${url.api}")
public class ReminderController {
	private final ReminderService reminderService;

	
	@PostMapping("/create")
	public ReminderReadDto create(@RequestBody ReminderCreateDto dto) {
		return reminderService.create(dto);
	}

	
	@DeleteMapping("/delete")
	public void delete(@RequestBody Long id) {
		reminderService.delete(id);
	}
}
