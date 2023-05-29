package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.devpro.animalshelter.core.dialog.DialogInterface;
import ru.devpro.animalshelter.core.dto.DialogDto;
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.core.exception.IntervalDateIncorrectException;
import ru.devpro.animalshelter.core.model.ContainReport;
import ru.devpro.animalshelter.core.repository.ReportRepository;
import ru.devpro.animalshelter.core.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.trim;
import static ru.devpro.animalshelter.configuration.BotConstants.*;

@Service
public class BotService {
    private final Logger logger = LoggerFactory.getLogger(BotService.class);
    private final TelegramBot telegramBot;
    private final Map<String, DialogInterface> supportedDialogs;
    private final ReportRepository reportRepository;
    private final ReportService reportService;
    private final UserRepository userRepository;

    private ContainReport containReport = new ContainReport();

    private ReportEntity reportEntity;

    private final String parseReport = "1[)](?<ration>[\\W+]+)2[)](?<health>[\\W+]+)3[)](?<behavior>[\\W+]+)";

    private final String parsePhone = "(?<phone>[+]7-\\d{3}-\\d{3}-\\d{2}-\\d{2})(\\s)(?<name>[\\W+]+)";


    public BotService(TelegramBot bot, Map<String, DialogInterface> supportedDialogs, ReportRepository reportRepository, ReportService reportService, UserRepository userRepository) {
        this.telegramBot = bot;
        this.supportedDialogs = supportedDialogs;
        this.reportRepository = reportRepository;
        this.reportService = reportService;
        this.userRepository = userRepository;

    }


    /**
     *
     * Exception try-catch {@link IntervalDateIncorrectException}
     * if {@link IntervalDateIncorrectException}
     * a message comes out {@link ru.devpro.animalshelter.configuration.BotConstants}
     * {@code  ERROR_MSG} - "Что-то пошло не так..."
     * @param update
     *
     */
    public void process(Update update) {
        if (update == null || update.message().document() != null) {
            logger.debug("Получен документ или null update");
            return;
        }
        for (DialogInterface dialog : supportedDialogs.values()) {
            Message incomeMessage = update.message();
            if (update.message() == null) {
                logger.debug("ChatId = {}; null message", incomeMessage.chat().id());
                return;
            }
            if (incomeMessage.photo() != null) {
                if (update.message().photo()[update.message().photo().length - 1].fileId() != null
                        && Objects.equals(containReport.getUserEntity().getChatId(), incomeMessage.chat().id())) {
                    containReport.setPhotoEntity(reportService.uploadReportPhoto(update));
                    saveReport(incomeMessage.chat().id());
                    sendResponse(incomeMessage.chat().id(), "Отчет принят!", WELCOME_KEYBOARD);
                    return;
                } else {
                    logger.debug("ChatId = {}; Фотография не полученна", incomeMessage.chat().id());
                }
            } else if (incomeMessage.text() != null) {
                if (incomeMessage.text().matches(parsePhone)) {
                    logger.info("ChatId = {}; Номер телефона в сообщении", incomeMessage.chat().id());
                    parsePhone(incomeMessage.text(), incomeMessage.chat().id());
                    sendResponse(incomeMessage.chat().id(), "Ваш номер телефона сохранен", SHELTER_KEYBOARD);
                    return;
                }
                if (incomeMessage.text().matches(parseReport)) {
                    UserEntity userEntity = userRepository.getUserEntitiesByChatId(incomeMessage.chat().id());
                    if (userEntity.getAnimalEntity() != null) {
                        logger.info("ChatId = {}; Получение отчета", incomeMessage.chat().id());
                        if (userEntity.getDate() == null) {
                            userEntity.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
                            userRepository.save(userEntity);
                        }
                        containReport.setUserEntity(userEntity);
                        containReport.setAnimalName(userEntity.getAnimalEntity().getAnimalName());
                        parseReport(incomeMessage.text());
                        sendResponse(incomeMessage.chat().id(), "Отчет получен. Ждем фото животного", WELCOME_KEYBOARD);
                    } else {
                        logger.info("ChatId = {}; Пользователь не брал животного, отчет отклонен", incomeMessage.chat().id());
                        sendResponse(incomeMessage.chat().id(), "Вы не являетесь усыновителем", WELCOME_KEYBOARD);
                    }
                    return;
                }
                DialogDto dto = new DialogDto(incomeMessage.chat().id(), update.message().from().firstName(), incomeMessage.text());
                if (dialog.isSupport(dto) && dialog.process(dto)) {
                    sendResponse(dto.chatId(), dialog.getMessage(dto.chatId()), dialog.getKeyboard());
                    return;
                } else {
                    logger.debug("ChatID={}; Пустой текс", incomeMessage.chat().id());
                }
            }
        }
    }

    private void parseReport(String text) {
        Pattern pattern = Pattern.compile(parseReport);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            logger.info("Начало сбора данных для отчета");
            containReport.setRation(trim(matcher.group("ration")));
            containReport.setBehavior(trim(matcher.group("behavior")));
            containReport.setHealth(trim(matcher.group("health")));
            containReport.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        }
    }

    private void parsePhone(String text, Long chatId) {
        Pattern pattern = Pattern.compile(parsePhone);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            UserEntity userEntity = userRepository.getUserEntitiesByChatId(chatId);
            if (userEntity == null) {
                userEntity = new UserEntity(chatId);
                userEntity.setIsVolunteer(false);
            }
            userEntity.setUserName(matcher.group("name"));
            userEntity.setPhone(matcher.group("phone"));
            userRepository.save(userEntity);
            logger.info("Сохранение контактных данных пользователей");
        }
    }

    private void saveReport(Long chatId) {
        if (Objects.equals(containReport.getUserEntity().getChatId(), chatId)) {

            ReportEntity report = new ReportEntity(containReport.getAnimalName(),
                    containReport.getRation(),
                    containReport.getHealth(),
                    containReport.getBehavior(),
                    containReport.getPhotoEntity(),
                    containReport.getDateTime(),
                    containReport.getUserEntity()
            );
            reportRepository.save(report);
            logger.info("Отчет сохранен");
        }
        containReport = new ContainReport();
    }

    public void sendResponse(Long chatId, String message, ReplyKeyboardMarkup keyboard) {
        if (message.equals(SHELTER_CONTACTS_MSG)) {
            sendResponse(chatId, reportService.getPhoto(1).getSecond(), null);
        }
        SendMessage preparedMessage = new SendMessage(chatId, message);
        if (keyboard != null) preparedMessage.replyMarkup(keyboard);
        telegramBot.execute(preparedMessage);
    }

    public void sendResponse(Long chatId, byte[] photo, ReplyKeyboardMarkup keyboard) {
        SendPhoto sendPhoto = new SendPhoto(chatId, photo);
        if (keyboard != null) sendPhoto.replyMarkup(keyboard);
        telegramBot.execute(sendPhoto);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    @Transactional(readOnly = true)
    public void findByDate() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        reportRepository.findAll().stream().filter(Objects::nonNull)
                .filter(reportEntity -> ChronoUnit.DAYS.between(reportEntity.getDateTime(), now) == 1)
                .map(ReportEntity::getUserEntity)
                .forEach(userEntity -> telegramBot.execute(new SendMessage(userEntity.getChatId(), "Вы просрочили отправку отчета")));
    }
}
