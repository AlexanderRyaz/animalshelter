package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.animalshelter.core.entity.ShelterEntity;
import ru.devpro.animalshelter.service.ShelterService;

import java.util.Collection;

@RestController
@RequestMapping("/shelter")
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @Operation(
            summary = "Добавление приюта",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные добавленного приюта",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterEntity.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ShelterEntity> createShelter(@RequestBody ShelterEntity shelterEntity) {
        return ResponseEntity.ok(shelterService.createShelter(shelterEntity));
    }

    @Operation(
            summary = "Поиск приюта по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные приюта",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterEntity.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<ShelterEntity> findShelter(
            @Parameter(description = "id приюта который нужно найти", example = "4")
            @PathVariable long id) {
        ShelterEntity shelterEntity = shelterService.findShelterById(id);
        if (shelterEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shelterEntity);
    }

    @Operation(
            summary = "Изменение данных о приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные приюта",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterEntity.class)
                            )
                    )
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<ShelterEntity> editShelter(@RequestBody ShelterEntity shelterEntity) {
        ShelterEntity editedShelter = shelterService.editShelter(1L, shelterEntity);
        if (editedShelter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedShelter);
    }

    @Operation(
            summary = "Удаление данных о приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приют удален"

                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<ShelterEntity> deleteShelter(
            @Parameter(description = "id приюта который необходимо удалить")
            @PathVariable long id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить список всех приютов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список приютов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterEntity.class)
                            )
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<ShelterEntity>> getAllShelters() {
        return ResponseEntity.ok(shelterService.getAllShelters());
    }
}
