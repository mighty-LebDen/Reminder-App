package ru.lebedev.reminder.http.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.lebedev.reminder.dto.ReminderCreateDto;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.dto.ReminderUpdateRequest;
import ru.lebedev.reminder.service.impl.ReminderService;

@RestController
@AllArgsConstructor
@RequestMapping("${url.api}")
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping("/create")
    public ReminderReadDto create(@RequestBody ReminderCreateDto dto) {
        System.out.println(dto);
        return reminderService.create(dto);
    }

    @GetMapping("/list")
    public List<ReminderReadDto> findAll() {
        return reminderService.findAll();
    }

//    @GetMapping("/id")
//    public ReminderReadDto findById(Long id) {
//        return reminderService.findById(2L);
//    }

    @PutMapping("/update")
    public ReminderReadDto update(@RequestBody ReminderUpdateRequest request) {
        var reminder = reminderService.update(request.id(), request.dto());
        return reminder.orElseThrow(() -> new RuntimeException());
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody Long id) {
        reminderService.delete(id);
    }

}
