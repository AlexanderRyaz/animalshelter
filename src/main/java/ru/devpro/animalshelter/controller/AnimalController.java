package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.devpro.animalshelter.core.record.AnimalRecord;
import ru.devpro.animalshelter.service.AnimalService;
import ru.devpro.animalshelter.service.SaverService;

import java.util.Collection;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;
    private final SaverService saverService;

    public AnimalController(AnimalService animalService, SaverService saverService) {
        this.animalService = animalService;
        this.saverService = saverService;
    }

    //добавление животного
    @Operation(
            summary = "Добавление животного в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные добавленного питомца",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalRecord.class)
                            )
                    )
            }
    )
    @PostMapping
    public AnimalRecord createAnimal(@RequestBody @Valid AnimalRecord animalRecord) {
        return animalService.createAnimal(animalRecord);
    }

    //вывод всех животных
    @Operation(
            summary = "Вывод всех животных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает коллекцию имеющихся питомцев",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalRecord.class)
                            )
                    )
            }
    )
    @GetMapping
    public Collection<AnimalRecord> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    //изменение животного
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные измененного питомца"
            )
    })
    @PutMapping("{id}")
    public AnimalRecord editAnimal(@PathVariable Long id, @RequestBody AnimalRecord animalRecord) {
        return animalService.editAnimal(id, animalRecord);
    }

    //поиск животного по id
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные питомца найденного по id"
            )
    })
    @GetMapping("{id}")
    public AnimalRecord findAnimal(@PathVariable Long id) {
        return animalService.findAnimalById(id);
    }

    //удаление животного
    @ApiResponse(responseCode = "200")
    @DeleteMapping ("{id}")
    public AnimalRecord deleteAnimal(@PathVariable Long id) {
        return animalService.deleteAnimal(id);
    }
}
