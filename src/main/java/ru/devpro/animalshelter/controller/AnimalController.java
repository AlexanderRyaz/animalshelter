package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.service.AnimalService;
import ru.devpro.animalshelter.service.ReportService;


import java.util.Collection;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;
    private final ReportService reportService;

    public AnimalController(AnimalService animalService, ReportService reportService) {
        this.animalService = animalService;
        this.reportService = reportService;
    }

    @Operation(
            summary = "Добавление животного в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные добавленного питомца",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalEntity.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<AnimalEntity> createAnimal(@RequestBody AnimalEntity animalEntity) {
        return ResponseEntity.ok(animalService.createAnimal(animalEntity));
    }

    @Operation(
            summary = "Вывод всех животных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает коллекцию имеющихся питомцев",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalEntity.class)
                            )
                    )
            }
    )
    @GetMapping("/findAll")
    public ResponseEntity<Collection<AnimalEntity>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @Operation(
            summary = "изменение записи животного",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные измененного питомца",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalEntity.class)
                            )
                    )
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<AnimalEntity> editAnimal(
            @Parameter(description = "id животного", example = "1")
            @PathVariable Long id,
            @RequestBody AnimalEntity animalEntity) {
        AnimalEntity editedAnimal = animalService.editAnimal(id, animalEntity);
        if (editedAnimal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animalEntity);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные питомца найденного по id"
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<AnimalEntity> findAnimal(
            @Parameter(description = "id животного которого нужно найти", example = "1")
            @PathVariable Long id) {
        AnimalEntity animalEntity = animalService.findAnimalById(id);
        if (animalEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animalEntity);
    }

    @ApiResponse(responseCode = "200")
    @DeleteMapping("{id}")
    public ResponseEntity<AnimalEntity> deleteAnimal(
            @Parameter(description = "id животного которого нужно удалить", example = "1")
            @PathVariable Long id) {

        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Ппросмотр фото по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Просмотр фото по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportService.class)
                            )
                    )
            }
    )
    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getPhoto(@Parameter(description = "Введите id фотографии", example = "1")
                                           @PathVariable long id) {
        Pair<String, byte[]> pair = reportService.getPhoto(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(pair.getFirst()))
                .contentLength(pair.getSecond().length)
                .body(pair.getSecond());
    }
}
