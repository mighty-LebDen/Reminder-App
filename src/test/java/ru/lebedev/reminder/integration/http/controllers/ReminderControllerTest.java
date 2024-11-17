package ru.lebedev.reminder.integration.http.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.lebedev.reminder.TestData.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.lebedev.reminder.integration.IntegrationTestBase;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class ReminderControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Value("${url.api}")
    private final String urlApi;

    @Test
    void createShouldReturnReminderReadDtoWhenValidInput() throws Exception {
        mockMvc.perform(post(urlApi + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(REMINDER_CREATE_EDIT_DTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(11))
               .andExpect(jsonPath("$.title").value("Meeting"));
    }

    @Test
    void listShouldReturnListReminderReadDtoWhenValidInput() throws Exception {
        mockMvc.perform(get(urlApi + "/list")
                                .param("total", "1")
                                .param("current", "0")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].title").value("Meeting"));
    }

    @Test
    void findShouldReturnListReminderReadDtoWhenValidInput() throws Exception {
        mockMvc.perform(get(urlApi + "/find")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(TITLE_SEARCH_FILTER)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].title").value("Meeting"));
    }

    @Test
    void sortShouldReturnListReminderReadDtoWhenValidInput() throws Exception {
        String direction = "asc";
        String field = "id";
        mockMvc.perform(get(urlApi + "/sort")
                                .param("direction", direction)
                                .param("field", field))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[1].id").value(2));

    }

    @Test
    void filterShouldReturnListReminderReadDtoWhenValidInput() throws Exception {
        mockMvc.perform(get(urlApi + "/filter")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(DATE_TIME_FILTER)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].title").value("Meeting"));
    }

    @Test
    void updateShouldReminderReadDtoWhenValidInput() throws Exception {
        mockMvc.perform(put(urlApi + "/" + REMINDER.getId() + "/" + "/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(REMINDER_EDIT_DTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(REMINDER.getId()))
               .andExpect(jsonPath("$.title").value(REMINDER_EDIT_DTO.title()))
               .andExpect(jsonPath("$.description").value(REMINDER_EDIT_DTO.description()));
    }

    @Test
    void delete_test() throws Exception {
        mockMvc.perform(delete(urlApi + "/" + REMINDER.getId() + "/" + "/delete"))
               .andExpect(status().isOk());
    }

}
