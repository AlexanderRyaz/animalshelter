package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.devpro.animalshelter.core.record.UserRecord;
import ru.devpro.animalshelter.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // создание пользователя
    @Operation(
            summary = "Запись пользователя в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные добавленного усыновителя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserRecord.class)
                            )
                    )
            }
    )

    @PostMapping
    public UserRecord createUser(@RequestBody @Valid UserRecord userRecord) {
        return userService.createUser(userRecord);
    }

    // нахождение пользователя по id
    @Operation(
            summary = "Поиск пользователя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserRecord.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public UserRecord findUserById(@Parameter(description = "введите id пользователя", example = "1")
                                   @PathVariable Long id) {
        return userService.findUserById(id);
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
    @Operation(
            summary = "Удаление пользователя из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленные данные из Бд",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserRecord.class)
                            )
                    )
            }
    )
    @DeleteMapping("{id}")
    public UserRecord deleteUser(@Parameter(description = "введите id пользователя", example = "1")
                                     @PathVariable Long id) {
        return userService.deleteUser(id);
    }

    // нахождение всех пользователей
    @Operation(
            summary = "нахождение всех пользователей в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вывод всех пользователей из Бд",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserRecord.class))
                            )
                    )
            }
    )
    @GetMapping
    public Collection<UserRecord> getAllUsers() {
        return userService.getAllUsers();
    }
}
