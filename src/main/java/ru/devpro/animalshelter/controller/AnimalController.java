package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import ru.devpro.animalshelter.service.AnimalService;

import java.util.Collection;

@RestController
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
    public void createAnimal() {
        return;
    }

    //вывод всех животных
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает коллекцию имеющихся питомцев"
            )
    })
    public Collection getAllAnimals() {
        return animalService.getAllAnimals();
    }

    //изменение животного
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные измененного питомца"
            )
    })
    public void editAnimal() {
        return;
    }

    //поиск животного по id
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные питомца найденного по id"
            )
    })
    public void findAnimal() {
        return;
    }

    //удаление животного
    @ApiResponse(responseCode = "200")
    public void deleteAnimal() {
        return;
    }
}
