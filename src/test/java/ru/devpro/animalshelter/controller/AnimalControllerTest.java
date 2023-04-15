package ru.devpro.animalshelter.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.devpro.animalshelter.service.AnimalService;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @Test
    void createAnimal()  {
    }

    @Test
    void getAllAnimals() {
    }

    @Test
    void editAnimal() {
    }

    @Test
    void findAnimal() {
    }

    @Test
    void deleteAnimal() {
    }
}