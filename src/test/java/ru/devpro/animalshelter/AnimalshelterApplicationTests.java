package ru.devpro.animalshelter;

import com.pengrad.telegrambot.TelegramBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.devpro.animalshelter.controller.AnimalController;
import ru.devpro.animalshelter.controller.ReportController;
import ru.devpro.animalshelter.controller.UserController;
import ru.devpro.animalshelter.listener.BotListener;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalshelterApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private UserController userController;

    @Autowired
    private AnimalController animalController;

    @Autowired
    private ReportController reportController;

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private BotListener botListener;

    @Test
    void contextLoads() {
        Assertions.assertThat(telegramBot).isNotNull();
        Assertions.assertThat(userController).isNotNull();
        Assertions.assertThat(animalController).isNotNull();
        Assertions.assertThat(reportController).isNotNull();
        Assertions.assertThat(botListener).isNotNull();
    }
}
