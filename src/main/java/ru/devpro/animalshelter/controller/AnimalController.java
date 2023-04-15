package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.service.AnimalService;


import java.util.Collection;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
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

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные измененного питомца"
            )
    })
    @PutMapping("{id}")
    public ResponseEntity<AnimalEntity> editAnimal(
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
    @DeleteMapping ("{id}")
    public ResponseEntity<AnimalEntity> deleteAnimal(
            @Parameter(description = "id животного которого нужно удалить", example = "1")
            @PathVariable Long id) {

        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }
}
