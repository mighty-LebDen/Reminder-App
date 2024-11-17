package ru.lebedev.reminder.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.lebedev.reminder.TestData.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.lebedev.reminder.TestData;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.database.entity.SentStatus;
import ru.lebedev.reminder.database.repository.ReminderRepository;
import ru.lebedev.reminder.dto.ReminderCreateEditDto;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.exception.ExceptionStatus;
import ru.lebedev.reminder.exception.ReminderServiceException;
import ru.lebedev.reminder.filters.DateTimeFilter;
import ru.lebedev.reminder.filters.SearchFilter;
import ru.lebedev.reminder.mapper.ReminderMapper;
import ru.lebedev.reminder.service.UserService;
import ru.lebedev.reminder.service.impl.ReminderService;

@ExtendWith({
        MockitoExtension.class
})
class ReminderServiceTest {

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReminderService reminderService;

    @Test
    void testCreate_ReminderSuccessfullyCreated() {

        doReturn(TEST_USER).when(userService).findById(TEST_USER.getId());
        doAnswer(invocation -> new Reminder(
                1L,
                REMINDER_WITHOUT_ID.getTitle(),
                REMINDER_WITHOUT_ID.getDescription(),
                REMINDER_WITHOUT_ID.getRemind(),
                REMINDER_WITHOUT_ID.getUser(),
                REMINDER_WITHOUT_ID.getEmailSentStatus(),
                REMINDER_WITHOUT_ID.getTelegramSentStatus()
        )).when(reminderRepository).saveAndFlush(any(Reminder.class));
        ReminderReadDto result = reminderService.create(REMINDER_CREATE_EDIT_DTO);
        assertEquals(REMINDER_READ_DTO, result);
        verify(reminderRepository, times(1)).saveAndFlush(any(Reminder.class));
    }

    @Test
    void testCreate_UserNotFound() {
        ReminderCreateEditDto dto = new ReminderCreateEditDto(
                "title",
                "desc",
                LocalDateTime.now().plusDays(1),
                888L
        );
        when(userService.findById(dto.userId())).thenReturn(null);

        Exception exception = assertThrows(
                ReminderServiceException.class,
                () -> reminderService.create(dto)
        );
        assertTrue(exception.getMessage().contains("user with"));
    }

    @Test
    void testFindAllWithPagination() {
        Pageable pageable = mock(Pageable.class);
        Page reminderPage = mock(Page.class);
        when(reminderPage.map(any())).thenReturn(Page.empty());
        when(reminderRepository.findAll(pageable)).thenReturn(reminderPage);
        List<ReminderReadDto> result = reminderService.findAllWithPagination(pageable);
        assertNotNull(result);
        verify(reminderRepository).findAll(pageable);
    }

    @Test
    void testDelete_ReminderExists() {
        Long reminderId = REMINDER.getId();
        doReturn(Optional.of(REMINDER)).when(reminderRepository).findById(reminderId);
        reminderService.delete(reminderId);
        verify(reminderRepository).delete(REMINDER);
        verify(reminderRepository).flush();
    }

    @Test
    void testDelete_ReminderNotFound() {
        Long reminderId = REMINDER.getId();
        when(reminderRepository.findById(reminderId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                ReminderServiceException.class,
                () -> reminderService.delete(reminderId)
        );
        assertTrue(exception.getMessage().contains("Remind with"));
    }

    @Test
    void testUpdate_ReminderSuccessfullyUpdated() {
        Long reminderId = REMINDER.getId();
        Reminder updatedReminder = Reminder.builder()
                                           .id(reminderId)
                                           .title(REMINDER_EDIT_DTO.title())
                                           .description(REMINDER_EDIT_DTO.description())
                                           .remind(REMINDER_EDIT_DTO.remind())
                                           .emailSentStatus(REMINDER.getEmailSentStatus())
                                           .emailSentStatus(REMINDER.getTelegramSentStatus())
                                           .user(TEST_USER)
                                           .build();
        ReminderReadDto expectedDto = new ReminderReadDto(
                reminderId,
                REMINDER_EDIT_DTO.title(),
                REMINDER_EDIT_DTO.description(),
                REMINDER_EDIT_DTO.remind(),
                USER_READ_DTO
        );
        when(reminderRepository.findById(reminderId)).thenReturn(Optional.of(REMINDER));
        when(reminderRepository.saveAndFlush(REMINDER)).thenReturn(updatedReminder);
        ReminderReadDto actualDto = reminderService.update(reminderId, REMINDER_EDIT_DTO);
        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
        verify(reminderRepository).findById(reminderId);
        verify(reminderRepository).saveAndFlush(REMINDER);
    }

    @Test
    void testUpdate_ReminderNotFound() {
        Long reminderId = REMINDER_WITHOUT_ID.getId();

        doReturn(Optional.empty()).when(reminderRepository).findById(reminderId);

        Exception exception = assertThrows(
                ReminderServiceException.class,
                () -> reminderService.update(
                        reminderId,
                        REMINDER_CREATE_EDIT_DTO
                )
        );
        assertTrue(exception.getMessage().contains("Remind with"));
    }

    @Test
    void testSort() {
        String direction = "asc";
        String field = "date";
        Sort expectedSort = Sort.by(Sort.Direction.ASC, field);
        doReturn(List.of(REMINDER)).when(reminderRepository).findAll(expectedSort);
        List<ReminderReadDto> result = reminderService.sort(direction, field);
        var resultDto = result.get(0);
        assertEquals(1, result.size());
        assertThat(REMINDER_READ_DTO.id()).isEqualTo(resultDto.id());
        verify(reminderRepository).findAll(expectedSort);
    }

}
