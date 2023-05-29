package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.core.model.AnimalType;
import ru.devpro.animalshelter.core.repository.AnimalRepository;
import ru.devpro.animalshelter.core.repository.UserRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.devpro.animalshelter.configuration.BotConstants.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BotServiceTest {

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private BotService botService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnimalService animalService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AnimalRepository animalRepository;

    @MockBean
    private ReportService reportService;

    @LocalServerPort
    private int port;



    @ParameterizedTest
    @MethodSource("menu")
    public void startDialogTest(String text) throws URISyntaxException, IOException {
        String json = Files.readString(Paths.get(Objects.requireNonNull(BotServiceTest.class.getResource("message.json")).toURI()));
        Update update = getUpdate(json, text);

        botService.process(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertThat(actual.getParameters().get("chat_id")).isEqualTo(111L);
        assertThat(actual.getParameters().get("reply_markup")).isNotNull();
        assertThat(actual.getParameters().get("text")).isEqualTo(GREETING_MSG);
    }

    @Test
    public void reportTest() throws URISyntaxException, IOException {
        String jsonText = Files.readString(Paths.get(Objects.requireNonNull(BotServiceTest.class.getResource("message.json")).toURI()));

//        AnimalEntity animalEntity = new AnimalEntity(1L, AnimalType.DOG, "Guchka");
//        animalRepository.save(animalEntity);
//
//        UserEntity userEntity = new UserEntity("Oleg",2L, false );
//        userEntity.setUserName("Oleg");
//        userEntity.setChatId(2L);
//        userEntity.setId(1L);
//        userEntity.setIsVolunteer(false);
//        userRepository.save(userEntity);
//
        UserEntity userEntity = new UserEntity("Oleg", 1L, false);
//        userEntity.setId(5L);
        assertThat(userEntity).isNotNull();

        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(1L);
        animalEntity.setAnimalName("Guchka");
        animalEntity.setAnimalType(AnimalType.DOG);
        assertThat(animalEntity).isNotNull();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animalEntity));
        when(userRepository.save(any())).thenReturn(userEntity);

        userService.addUserAnimal(1L, 1L);

        List<Update> updateList = new ArrayList<>(List.of(
                getUpdate(jsonText, SEND_REPORT_CMD),
                getUpdate(jsonText, "1) Ест 2) Гавкает 3) Скачет")));

        botService.process(updateList.get(0));
        botService.process(updateList.get(1));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(2)).execute(argumentCaptor.capture());
        List<SendMessage> actualList = argumentCaptor.getAllValues();
        Assertions.assertThat(actualList.size()).isEqualTo(2);

        assertThat(actualList.get(0).getParameters().get("chat_id")).isEqualTo(111L);
        assertThat(actualList.get(0).getParameters().get("text")).isEqualTo(SEND_REPORT_MSG);

        assertThat(actualList.get(1).getParameters().get("chat_id")).isEqualTo(111L);
        assertThat(actualList.get(1).getParameters().get("text")).isEqualTo("Отправьте фото животного.");
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }

    private static Stream<Arguments> menu() {
        return Stream.of(
                Arguments.of(INITIAL_MSG),
                Arguments.of(GO_BACK_CMD)
        );
    }

}