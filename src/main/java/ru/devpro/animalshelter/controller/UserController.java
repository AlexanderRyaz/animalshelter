package ru.devpro.animalshelter.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.devpro.animalshelter.service.UserService;

import java.util.Collection;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // создание пользователя
    public void createUser() {
        return;
    }

    // нахлждениt пользователя по id
    public void findUserById() {
        return;
    }

    // добавления животного пользователю по id
    public void addUserAnimal() {
        return;
    }

    // удаление пользователя
    public void deleteUser() {
        return;
    }

    // нахождение всех пользователей
    public Collection getAllUsers() {
        return userService.getAllUsers();
    }
}
