package ru.devpro.animalshelter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.entity.ShelterEntity;
import ru.devpro.animalshelter.core.model.AnimalType;
import ru.devpro.animalshelter.core.repository.ShelterRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest({ShelterService.class, ShelterRepository.class})
class ShelterServiceTest {
    @Autowired
    private ShelterService shelterService;
    @MockBean
    private ShelterRepository shelterRepository;

    @Test
    void createShelter() {
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setId(1L);
        shelterEntity.setName("Good hands");
        shelterEntity.setAddress("Moscow");
        shelterEntity.setOpening_hours("09:00");

        when(shelterRepository.save(any())).thenReturn(shelterEntity);
        ShelterEntity shelter = shelterService.createShelter(shelterEntity);
        assertEquals(1L, shelter.getId());
        assertEquals("Good hands", shelter.getName());
        assertEquals("09:00", shelter.getOpening_hours());
        assertEquals("Moscow", shelter.getAddress());
    }

    @Test
    void findShelterById() {
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setId(1L);
        shelterEntity.setName("Good hands");
        shelterEntity.setAddress("Moscow");
        shelterEntity.setOpening_hours("09:00");

        when(shelterRepository.findById(anyLong())).thenReturn((shelterEntity));
        ShelterEntity shelter = shelterService.findShelterById(1L);
        assertEquals(1L, shelter.getId());
        assertEquals("Good hands", shelter.getName());
        assertEquals("09:00", shelter.getOpening_hours());
        assertEquals("Moscow", shelter.getAddress());
    }

    @Test
    void editShelter() {
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setId(1L);
        shelterEntity.setName("Good hands");
        shelterEntity.setAddress("Moscow");
        shelterEntity.setOpening_hours("09:00");

        ShelterEntity shelterEntity1 = new ShelterEntity();
        shelterEntity1.setId(1L);
        shelterEntity1.setName("Dog");
        shelterEntity1.setOpening_hours("08:00");
        shelterEntity1.setAddress("Piter");

        when(shelterRepository.findById(anyLong())).thenReturn(((shelterEntity)));
        when(shelterRepository.save(any())).thenReturn(shelterEntity1);
        ShelterEntity shelter = shelterService.editShelter(1L, shelterEntity1);
        assertEquals(1L, shelter.getId());
        assertEquals("Dog", shelter.getName());
        assertEquals("08:00", shelter.getOpening_hours());
        assertEquals("Piter", shelter.getAddress());
    }

    @Test
    void getAllShelters() {
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setId(1L);
        shelterEntity.setName("Good hands");
        shelterEntity.setAddress("Moscow");
        shelterEntity.setOpening_hours("09:00");

        when(shelterRepository.findAll()).thenReturn (List.of(shelterEntity));
        Collection<ShelterEntity>shelterEntities = shelterService.getAllShelters();
        assertEquals(1, shelterEntities.size());
    }
}