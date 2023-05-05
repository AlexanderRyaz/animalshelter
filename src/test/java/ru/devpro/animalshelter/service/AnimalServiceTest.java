package ru.devpro.animalshelter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.entity.ShelterEntity;
import ru.devpro.animalshelter.core.exception.AnimalNotFoundException;
import ru.devpro.animalshelter.core.model.AnimalType;
import ru.devpro.animalshelter.core.repository.AnimalRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AnimalService.class, AnimalRepository.class})
class AnimalServiceTest {
    @Autowired
    private AnimalService animalService;
    @MockBean
    private AnimalRepository animalRepository;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void createAnimal() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(1L);
        animalEntity.setAnimalName("Guchka");
        animalEntity.setAnimalType(AnimalType.DOG);

        when(animalRepository.save(any())).thenReturn(animalEntity);
        AnimalEntity animal = animalService.createAnimal(animalEntity);
        assertEquals(1L, animal.getId());
        assertEquals("Guchka", animal.getAnimalName());
        assertEquals(AnimalType.DOG, animal.getAnimalType());
    }

    @Test
    void findAnimalById() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(1L);
        animalEntity.setAnimalName("Guchka");
        animalEntity.setAnimalType(AnimalType.DOG);

        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animalEntity));
        Optional<AnimalEntity> animal = animalService.findAnimalById(1L);
        assertEquals(1L, animal.get().getId());
        assertEquals("Guchka", animal.get().getAnimalName());
        assertEquals(AnimalType.DOG, animal.get().getAnimalType());
    }

    @Test
    void editAnimal() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(1L);
        animalEntity.setAnimalName("Guchka");
        animalEntity.setAnimalType(AnimalType.DOG);

        AnimalEntity animalEntity1 = new AnimalEntity();
        animalEntity1.setId(1L);
        animalEntity1.setAnimalName("Guc");
        animalEntity1.setAnimalType(AnimalType.DOG);

        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animalEntity));
        when(animalRepository.save(any())).thenReturn(animalEntity1);
        AnimalEntity animal = animalService.editAnimal(1L, animalEntity1);
        assertEquals(1L, animal.getId());
        assertEquals("Guc", animal.getAnimalName());
        assertEquals(AnimalType.DOG, animal.getAnimalType());
    }
    @Test
    void editAnimalWithNull() throws Exception {

        when(animalRepository.findById(anyLong())).thenReturn(null);
        AnimalEntity animalEntity = animalService.editAnimal(7L, new AnimalEntity());
        assertNull(animalEntity);

    }

    @Test
    void getAllAnimals() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(1L);
        animalEntity.setAnimalName("Guchka");
        animalEntity.setAnimalType(AnimalType.DOG);

        when(animalRepository.findAll()).thenReturn(List.of(animalEntity));
        Collection<AnimalEntity> allAnimals = animalService.getAllAnimals();
        assertEquals(1, allAnimals.size());
    }

    @Test
    void deleteAnimal() {
        doNothing().when(animalRepository).deleteById(anyLong());
        animalService.deleteAnimal(5L);
        verify(animalRepository,times(1)).deleteById(anyLong());
    }
}