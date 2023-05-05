package ru.devpro.animalshelter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.model.AnimalType;
import ru.devpro.animalshelter.service.AnimalService;
import ru.devpro.animalshelter.service.ReportService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AnimalController.class, AnimalService.class, ReportService.class})
class AnimalControllerTest {
    private static final String ANIMAL_MAPPING = "/animal";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportService reportService;
    @Autowired
    private AnimalController animalController;
    @MockBean
    private AnimalService animalService;

    @Test
    void createAnimal() throws Exception {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setAnimalType(AnimalType.DOG);
        animalEntity.setAnimalName("Sharik");
        animalEntity.setId(7L);

        AnimalEntity animal = new AnimalEntity();
        animal.setAnimalName("Sharik");
        animal.setAnimalType(AnimalType.DOG);

        when(animalService.createAnimal(any())).thenReturn(animalEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(ANIMAL_MAPPING)
                        .content(new ObjectMapper().writeValueAsString(animal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.animalName").value("Sharik"))
                .andExpect(jsonPath("$.animalType").value("DOG"));
    }

    @Test
    void getAllAnimals() throws Exception {
        AnimalEntity cat = new AnimalEntity();
        cat.setAnimalType(AnimalType.CAT);
        cat.setAnimalName("Katya");
        cat.setId(3L);

        AnimalEntity dog = new AnimalEntity();
        dog.setAnimalType(AnimalType.DOG);
        dog.setAnimalName("Jessy");
        dog.setId(11L);


        when(animalService.getAllAnimals()).thenReturn(List.of(cat, dog));
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ANIMAL_MAPPING + "/findAll")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(3, 11)))
                .andExpect(jsonPath("$[*].animalName", containsInAnyOrder("Katya", "Jessy")))
                .andExpect(jsonPath("$[*].animalType", containsInAnyOrder("CAT", "DOG")));
    }

    @Test
    void editAnimal() throws Exception {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setAnimalType(AnimalType.DOG);
        animalEntity.setAnimalName("Sharik");
        animalEntity.setId(7L);

        when(animalService.editAnimal(anyLong(), any())).thenReturn(animalEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(ANIMAL_MAPPING + "/" + 7)
                        .content(new ObjectMapper().writeValueAsString(animalEntity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.animalName").value("Sharik"))
                .andExpect(jsonPath("$.animalType").value("DOG"));

    }

    @Test
    void editAnimalWithNull() throws Exception {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setAnimalType(AnimalType.DOG);
        animalEntity.setAnimalName("Sharik");
        animalEntity.setId(7L);

        when(animalService.editAnimal(anyLong(), any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(ANIMAL_MAPPING + "/" + 7)
                        .content(new ObjectMapper().writeValueAsString(animalEntity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void findAnimal() throws Exception {

        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setAnimalType(AnimalType.DOG);
        animalEntity.setAnimalName("Sharik");
        animalEntity.setId(7L);

        when(animalService.findAnimalById(anyLong())).thenReturn(Optional.of(animalEntity));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ANIMAL_MAPPING + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.animalName").value("Sharik"))
                .andExpect(jsonPath("$.animalType").value("DOG"));
    }

    @Test
    void findAnimalWithNull() throws Exception {


        when(animalService.findAnimalById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ANIMAL_MAPPING + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAnimal() throws Exception {

        doNothing().when(animalService).deleteAnimal(anyLong());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(ANIMAL_MAPPING + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}