package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.core.exception.UserNotFoundException;
import ru.devpro.animalshelter.core.repository.AnimalRepository;
import ru.devpro.animalshelter.core.repository.UserRepository;

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

    public UserEntity findUserById(long id) {
        logger.info("Вызов методв поиска пользователей по id");
        return userRepository.findById(id);
    }

    public void addUserAnimal() {

    }

    public void deleteUser(long id) {
        logger.info("вызов метода удаления пользователя");

        userRepository.deleteById(id);
    }

    public Collection<UserEntity> getAllUsers() {
        return this.userRepository.findAll();
    }

}
