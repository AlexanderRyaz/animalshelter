package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import ru.devpro.animalshelter.core.entity.PhotoEntity;
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.core.exception.PhotoNotFoundException;
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
        photoEntity.setFileMediaType("image/jpeg");
        photoEntity.setFilePath("a/b");
        photoEntity.setFileSize(1000L);
        photoEntity.setFileData(new byte[]{1, 2, 3});
        when(photoRepository.findById(anyLong())).thenReturn(Optional.of(photoEntity));
        Pair<String, byte[]> photo = reportService.getPhoto(1L);
        assertEquals("image/jpeg", photo.getFirst());
    }

    @Test
    void findById() {
    }

    @Test
    void getPhotoNotFound() {

        when(photoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PhotoNotFoundException.class, () -> reportService.getPhoto(1L));

    }
}