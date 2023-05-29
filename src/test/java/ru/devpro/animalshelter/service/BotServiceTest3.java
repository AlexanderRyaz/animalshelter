package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * Тест для выявления ошибки
 */
@SpringBootTest
class BotServiceTest3 {

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private BotService botService;

    @Test
    void process() {
    }
}