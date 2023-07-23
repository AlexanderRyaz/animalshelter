package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.dto.DialogDto;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.core.exception.AnimalNotFoundException;
import ru.devpro.animalshelter.core.exception.DateNotFoundException;
import ru.devpro.animalshelter.core.exception.UserNotFoundException;
import ru.devpro.animalshelter.core.repository.AnimalRepository;
import ru.devpro.animalshelter.core.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final TelegramBot telegramBot;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, AnimalRepository animalRepository, TelegramBot telegramBot) {
        this.userRepository = userRepository;
        this.animalRepository = animalRepository;
        this.telegramBot = telegramBot;
    }

    public UserEntity createUser(UserEntity userEntity) {
        logger.info("вызов метода создания пользователя");
        return userRepository.save(userEntity);
    }

    public boolean createUser(DialogDto dialogDto) {
        UserEntity userEntity = userRepository.findById(dialogDto.chatId()).orElse(null);
        if (userEntity == null) {
            userEntity = new UserEntity(dialogDto.name(), dialogDto.chatId(), false);
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }

    public UserEntity findUserById(long id) {
        logger.info("Вызов методв поиска пользователей по id");
        return userRepository.findById(id).orElseThrow(() -> {
            logger.error("Пользователь с id = {} не найден", id);
            return new UserNotFoundException(id);
        });
    }

    public UserEntity addUserAnimal(Long id, Long animalId) {
        logger.info("Вызов метода добавления животного пользователю");
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        Optional<AnimalEntity> optionalAnimalEntity = animalRepository.findById(animalId);
        if (optionalUserEntity.isEmpty()) {
            logger.error("Не найден поьзователь с id = {}", id);
            throw new UserNotFoundException(id);
        }
        if (optionalAnimalEntity.isEmpty()) {
            logger.error("Не найдено животное с id = {}", animalId);
            throw new AnimalNotFoundException(animalId);
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setAnimalEntity(optionalAnimalEntity.get());
        return userRepository.save(userEntity);
    }

    public void deleteUser(long id) {
        logger.info("вызов метода удаления пользователя");

        userRepository.deleteById(id);
    }

    public Collection<UserEntity> getAllUsers() {
        return this.userRepository.findAll();
    }

    public UserEntity extendPeriod(Long id, Integer days) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Пользователь с id = {} не найден", id);
                    return new UserNotFoundException(id);
                });
        if (userEntity.getDate() == null) {
            throw new DateNotFoundException(id);
        }
        LocalDateTime localDateTime = userEntity.getDate();
        if (days > 0) {
            userEntity.setDate(localDateTime.plusDays(days));
            logger.info("Пользователю с id = {} сделано пролдение срока на " + days + "дней", id);
        } else {
            logger.error("Данные указаны неверно");
        }
        return userRepository.save(userEntity);
    }

    public void sendMessageToUser(Long id, String text) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            SendMessage message = new SendMessage(user.get().getChatId(), text);
            telegramBot.execute(message);
        }
    }

    public Collection<ReportEntity> findReportUser(Long id) {
        logger.info("Вызов метода поиска отчетов пользователя");
        return userRepository.findById(id)
                .map(UserEntity::getReportEntity)
                .map(ArrayList::new)
                .orElseThrow(() -> {
            logger.error("Пользователь с id = {} не найден", id);
            return new UserNotFoundException(id);
        });
    }

    public UserEntity addUserIsVolunteer(Long id, Boolean isVolunteer) {
        logger.info("Вызов метода назначения пользователя волонтером");

        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

        if (optionalUserEntity.isEmpty()) {
            logger.error("Не найден поьзователь с id = {}", id);
            throw new UserNotFoundException(id);
        }

        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setIsVolunteer(isVolunteer);
        return userRepository.save(userEntity);
    }

    public UserEntity findUserIsVolunteer(Long id) {
        logger.info("Вызов метода поиска пользователя-волонтера");

        return userRepository.findById(id).filter(UserEntity::getIsVolunteer).orElseThrow(() -> {
            logger.error("Волонтер с id = {} не найден", id);
            return new UserNotFoundException(id);
        });
    }

}
