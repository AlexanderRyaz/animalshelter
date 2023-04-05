package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные добавленного усыновителя"
            )
    })
    public void createUser() {
        return;
    }

    // нахождение пользователя по id
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные усыновителя по id"
            )
    })
    public void findUserById() {
        return;
    }

    // добавления животного пользователю по id
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные добавленного питомца и усыновителя"
            )
    })
    public void addUserAnimal() {
        return;
    }

    // удаление пользователя
    @ApiResponse(responseCode = "200")
    public void deleteUser() {
        return;
    }

    // нахождение всех пользователей
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает массив всех усыновителей"
            )
    })
    public Collection getAllUsers() {
        return userService.getAllUsers();
    }
}
