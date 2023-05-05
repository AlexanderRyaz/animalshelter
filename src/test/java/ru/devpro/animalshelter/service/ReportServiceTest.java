package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.devpro.animalshelter.core.entity.PhotoEntity;
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.core.repository.PhotoRepository;
import ru.devpro.animalshelter.core.repository.ReportRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest({TelegramBot.class, ReportRepository.class, ReportService.class, PhotoRepository.class})
class ReportServiceTest {
    @MockBean
    private TelegramBot telegramBot;
    @MockBean
    private ReportRepository reportRepository;
    @Autowired
    private ReportService reportService;
    @MockBean
    private PhotoRepository photoRepository;

    @Test
    void uploadReportPhoto() throws IOException {
//        doNothing().when(telegramBot).execute(any());
//        when(telegramBot.getFileContent(any())).thenReturn(new byte[]{1, 2, 3});
    }

    @Test
    void getPhoto() {
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setId(1L);
        photoEntity.setFileMediaType("jpeg");
        photoEntity.setFilePath("a/b");
        photoEntity.setFileSize(1000L);
        photoEntity.setFileData(new byte[]{1, 2, 3});

//       when(reportRepository.findById(anyLong())).thenReturn(photoEntity);

    }

    @Test
    void findById() {
    }
}