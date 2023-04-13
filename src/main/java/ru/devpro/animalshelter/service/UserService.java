package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.dto.DialogDto;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.core.exception.UserNotFoundException;
import ru.devpro.animalshelter.core.record.MapperRecord;
import ru.devpro.animalshelter.core.record.UserRecord;
import ru.devpro.animalshelter.core.repository.AnimalRepository;
import ru.devpro.animalshelter.core.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final MapperRecord mapperRecord;
    private final TelegramBot telegramBot;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, AnimalRepository animalRepository, MapperRecord mapperRecord, TelegramBot telegramBot) {
        this.userRepository = userRepository;
        this.animalRepository = animalRepository;
        this.mapperRecord = mapperRecord;
        this.telegramBot = telegramBot;
    }

    public UserRecord createUser(UserRecord userRecord) {
        UserEntity userEntity = mapperRecord.toEntity(userRecord);
        return mapperRecord.toRecord(userRepository.save(userEntity));
    }

    /* сломано

    //метод добавления пользователя в БД
    public boolean creteUser(DialogDto dialogDto) {    // это либо пользователь, либо сотрудник приюта
        UserRecord userRecord = findUserByChatId(dialogDto.chatId());
        if (userRecord == null) {
            userRecord = new UserRecord(dialogDto.name(), dialogDto.chatId(), false);
            userRepository.save(mapperRecord.toEntity(userRecord));
            return true;
        }
        return false;
    }

    private UserRecord findUserByChatId(Long chatId) {
        return mapperRecord.toRecord(userRepository.findUserEntityChatId(chatId).orElse(null));
    }
*/


    // метод нахлждения пользователя по id
    public UserRecord findUserById(Long id) {
        logger.info("Вызов методв поиска пользователей по id");
        return mapperRecord.toRecord(userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Не найден пользователь с id = {}", id);
                    return new UserNotFoundException(id);
                }));
    }

    //метод добавления животного пользователю по id
    public void addUserAnimal() {

    }

    //метод удаления пользователя из БД
    public UserRecord deleteUser(Long id) {
        logger.info("вызов метода удаления пользователя");
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("не найден пользователь с id = {}", id);
                    return new UserNotFoundException(id);
                });
        userRepository.delete(userEntity);
        return mapperRecord.toRecord(userEntity);
    }

    //метод нахождения всех пользователей в БД
    public Collection<UserRecord> getAllUsers() {
        return userRepository.findAll().stream()
                .map(mapperRecord::toRecord)
                .collect(Collectors.toList());
    }

}
