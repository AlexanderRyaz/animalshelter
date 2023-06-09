package ru.devpro.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.service.BotService;

import java.util.List;

@Service
public class BotListener implements UpdatesListener {

    private final TelegramBot telegramBot;

    private final BotService botService;

    private final Logger logger = LoggerFactory.getLogger(BotListener.class);

    public BotListener(TelegramBot telegramBot, BotService botService) {
        this.telegramBot = telegramBot;
        this.botService = botService;
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

}
