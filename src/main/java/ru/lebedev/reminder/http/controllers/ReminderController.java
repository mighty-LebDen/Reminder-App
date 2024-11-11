package ru.lebedev.reminder.http.controllers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.lebedev.reminder.dto.ReminderCreateEditDto;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.filters.DateAndTimeFilter;
import ru.lebedev.reminder.filters.SearchFilter;
import ru.lebedev.reminder.service.impl.ReminderService;

@RestController
@AllArgsConstructor
@RequestMapping("${url.api}")
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping("/create")
    public ReminderReadDto create(@Validated @NotNull @RequestBody ReminderCreateEditDto dto) {
        return reminderService.create(dto);
    }

    @GetMapping("/list")
    public List<ReminderReadDto> findAll(
            @RequestParam(name = "total", defaultValue = "3") @Positive Integer total,
            @RequestParam(name = "current", defaultValue = "0") Integer current) {
        return reminderService.findAllWithPagination(PageRequest.of(current, total));
    }

    @PutMapping("/{id}/update")
    public ReminderReadDto update(
            @PathVariable @Positive Long id,
            @Validated @RequestBody ReminderCreateEditDto request) {
        return reminderService.update(id, request);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@Positive @PathVariable Long id) {
        reminderService.delete(id);
    }

    @GetMapping("/find")
    public List<ReminderReadDto> findBySearchFilter(
            @Validated
            @RequestBody SearchFilter filter) {
        return reminderService.findAllBySearchFilter(filter);
    }

    @GetMapping("/sort")
    public List<ReminderReadDto> findAllBySort(
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "field", defaultValue = "id") String field) {
        return reminderService.sort(direction, field);
    }

    @GetMapping("/filter")
    public List<ReminderReadDto> findAllByFilter(@Validated @RequestBody DateAndTimeFilter filter) {
        return reminderService.findAllByDateAndTimeFilter(filter);
    }

}
