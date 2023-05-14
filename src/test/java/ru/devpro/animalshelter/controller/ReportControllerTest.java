package ru.devpro.animalshelter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.service.ReportService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ReportService.class, ReportController.class})
class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportService reportService;

    @Test
    void findReport() throws Exception {
        when(reportService.findById(anyLong())).thenReturn(new ReportEntity());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void findReportNotFound() throws Exception {
        when(reportService.findById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}