package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.entity.PhotoEntity;
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.core.exception.PhotoNotFoundException;
import ru.devpro.animalshelter.core.repository.PhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


@Service
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Value("${path.to.reports.folder}")
    private String reportsDir;
    private final TelegramBot telegramBot;
    private final PhotoRepository photoRepository;


    public ReportService(TelegramBot telegramBot, PhotoRepository photoRepository) {
        this.telegramBot = telegramBot;
        this.photoRepository = photoRepository;
    }

    private Path createPath(Long chatIdMs, String fileFormat) throws IOException {
        StringBuilder pathFolder = new StringBuilder(reportsDir + "/");
        if (createFolder(pathFolder.toString())) {
            pathFolder.append(chatIdMs).append("/");
            if (createFolder(pathFolder.toString())) {
                long count = Files.find(Paths.get(pathFolder.toString()), 1, (path, attributes) -> attributes.isDirectory()).count();
                pathFolder.append("report-").append(count).append("/");
                if (createFolder(pathFolder.toString())) {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    String fileName = localDateTime.getYear() + "."
                            + localDateTime.getMonthValue() + "."
                            + localDateTime.getDayOfMonth() + "."
                            + localDateTime.getHour() + "."
                            + localDateTime.getMinute()
                            + fileFormat;
                    return Path.of(pathFolder + fileFormat);
                }
            }
        }
        return null;
    }

    private boolean createFolder(String path) {
        java.io.File folder = new java.io.File(path);
        if (!folder.exists()) {
            return folder.mkdir();
        }
        return true;
    }

    public PhotoEntity uploadReportPhoto(Update update) {
        logger.info("Вызов метода Update для загрузки фотографии");
        Long chatIdMs = update.message().chat().id();
        PhotoSize[] photoSizes = update.message().photo();
        int maxIndex = photoSizes.length - 1;
        try {
            GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSizes[maxIndex].fileId()));
            byte[] fileData = telegramBot.getFileContent(getFileResponse.file());
            String filePath = getFileResponse.file().filePath();
            String fileExtension = filePath.substring(filePath.lastIndexOf('.'));

            Path path = createPath(chatIdMs, fileExtension);
            if (path == null) {
                logger.error("ChatId = {}; не удалось найти или создать папку", chatIdMs);
                return null;
            }
            Files.write(path, fileData);

            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setFileData(fileData);
            photoEntity.setFileMediaType(Files.probeContentType(path));
            photoEntity.setFilePath(path.toString());
            photoEntity.setFileSize(photoSizes[maxIndex].fileSize());
            photoRepository.save(photoEntity);
            return photoEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pair<String, byte[]> getPhoto(long id) {
        logger.info("Вызов метода чтения фотографии из БД");
        PhotoEntity photoEntity = photoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Не найдена фотография с id = {}", id);
                    return new PhotoNotFoundException(id);
                });
        return Pair.of(photoEntity.getFileMediaType(), photoEntity.getFileData());
    }

    public ReportEntity findById(long id) {
        return null;
    }
}
