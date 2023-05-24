package ru.devpro.animalshelter.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.service.BotService;
import ru.devpro.animalshelter.service.ReportService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotListener implements UpdatesListener {

    private final TelegramBot telegramBot;

    private final BotService botService;

    private final ReportService reportService;

    private final Logger logger = LoggerFactory.getLogger(BotListener.class);

    public BotListener(TelegramBot telegramBot, BotService botService, ReportService reportService) {
        this.telegramBot = telegramBot;
        this.botService = botService;
        this.reportService = reportService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Обновление: {}", update);
                botService.process(update);
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = " 0 * * * * *")
    public void sendReminderReport() {

    }

}
