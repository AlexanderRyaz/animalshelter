package ru.devpro.animalshelter.controller;

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
    public void createAnimal() {
        return;
    }

    //вывод всех животных
    public Collection getAllAnimals() {
        return animalService.getAllAnimals();
    }

    //изменение животного
    public void editAnimal() {
        return;
    }

    //поиск животного по id
    public void findAnimal() {
        return;
    }

    //удаление животного
    public void deleteAnimal() {
        return;
    }
}
