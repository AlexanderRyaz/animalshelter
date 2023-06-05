package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;
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
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {

        return ResponseEntity.ok(userService.createUser(userEntity));
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
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<UserEntity> findUserById(@Parameter(description = "введите id пользователя", example = "1")
                                   @PathVariable Long id) {
        UserEntity userEntity = userService.findUserById(id);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userEntity);
    }

    // добавления животного пользователю по id
    @Operation(
            summary = "Прикрепление животного за пользователем по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Прикрепление животного за пользователем по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @PatchMapping("/{id}/animal")
    public ResponseEntity<UserEntity> addUserAnimal(
            @Parameter(description = "id пользователя", example = "1")
            @PathVariable Long id,
            @Parameter(description = "id животного", example = "1")
            @RequestParam("animalId") Long animalId) {
        return ResponseEntity.ok(userService.addUserAnimal(id, animalId));
    }

    @Operation(
            summary = "Назначение пользователя волонтером",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Назначение пользователя волонтером",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @PatchMapping("/{id}/user")
    public ResponseEntity<UserEntity> addUserIsVolunteer(
            @Parameter(description = "id пользователя", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Назначить пользователя волонтером?", example = "true")
            @RequestParam("is_volunteer") Boolean isVolunteer) {
        return ResponseEntity.ok(userService.addUserIsVolunteer(id, isVolunteer));
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
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<UserEntity> deleteUser(@Parameter(description = "введите id пользователя", example = "1")
                                     @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
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
                                    array = @ArraySchema(schema = @Schema(implementation = UserEntity.class))
                            )
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Отправка сообщения пользователю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "тправка сообщения пользователю",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @GetMapping("{id}/message")
    public String sendMessageToUser(
            @Parameter(description = "id пользователя", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Текст сообщения", example = "отчет неверно заполнен")
            @RequestParam("text") String text) {
        userService.sendMessageToUser(id, text);
        return "Сообщение отправлено";
    }

    @Operation(
            summary = "Изменение испытательного срока",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменение испытательного срока",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @PatchMapping("{id}/period")
    public ResponseEntity<UserEntity> extendPeriod(
            @Parameter(description = "id пользователя", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Количество дней", example = "14")
            @RequestParam("number") Integer number) {
        return ResponseEntity.ok(userService.extendPeriod(id, number));
    }

    @Operation(
            summary = "Поиск отчетов пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск отчетов пользователя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @GetMapping("{id}/reports")
    public ResponseEntity<Collection<ReportEntity>> findReportUser(
            @Parameter(description = "id пользователя", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.findReportUser(id));
    }
}
