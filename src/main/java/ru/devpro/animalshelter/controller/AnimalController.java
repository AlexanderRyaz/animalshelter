package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import ru.devpro.animalshelter.core.record.AnimalRecord;
import ru.devpro.animalshelter.service.AnimalService;

import java.util.Collection;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    //добавление животного
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные добавленного питомца"
            )
    })
    @PostMapping
    public AnimalRecord createAnimal(@RequestBody AnimalRecord animalRecord) {
        return animalService.createAnimal(animalRecord);
    }

    //вывод всех животных
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает коллекцию имеющихся питомцев"
            )
    })
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
