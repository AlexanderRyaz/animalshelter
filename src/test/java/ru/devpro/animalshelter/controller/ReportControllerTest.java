package ru.devpro.animalshelter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.devpro.animalshelter.core.entity.PhotoEntity;
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.service.ReportService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ReportController.class, ReportService.class})
class ReportControllerTest {
    @Autowired
    private ReportController reportController;
    @MockBean
    private ReportService reportService;
    @Autowired
    private MockMvc mockMvc;
    private final static String REPORT_MAPPING = "/report";

    @Test
    void findReport() throws Exception {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setAnimalName("Sharik");
        reportEntity.setId(1L);
        reportEntity.setUserEntity(new UserEntity());
        reportEntity.setPhotoEntity(new PhotoEntity());
        reportEntity.setBehavior("good");
        reportEntity.setHealth("good");
        reportEntity.setRation("meat");
        reportEntity.setDateTime(LocalDateTime.now());

        when(reportService.findById(anyLong())).thenReturn(reportEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(REPORT_MAPPING + "/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.animalName").value("Sharik"))
                .andExpect(jsonPath("$.behavior").value("good"))
                .andExpect(jsonPath("$.health").value("good"))
                .andExpect(jsonPath("$.ration").value("meat"));

    }

    @Test
    void findReportWithNull() throws Exception {
        when(reportService.findById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(REPORT_MAPPING + "/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

}