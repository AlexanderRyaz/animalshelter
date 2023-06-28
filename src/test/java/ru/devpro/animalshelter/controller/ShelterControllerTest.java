package ru.devpro.animalshelter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import ru.devpro.animalshelter.core.model.AnimalType;
import ru.devpro.animalshelter.service.ShelterService;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ShelterController.class, ShelterService.class})
class ShelterControllerTest {
    private static final String SHELTER_MAPPING = "/shelter";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShelterService shelterService;

    @Test
    void createShelter() throws Exception {
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setName("Good Hands");
        shelterEntity.setAddress("Moscow");
        shelterEntity.setOpening_hours("08:00");
        shelterEntity.setId(7L);

        ShelterEntity shelter = new ShelterEntity();
        shelter.setName("Good Hands");
        shelter.setAddress("Moscow");
        shelter.setOpening_hours("08:00");

        when(shelterService.createShelter(any())).thenReturn(shelterEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(SHELTER_MAPPING)
                        .content(new ObjectMapper().writeValueAsString(shelter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.name").value("Good Hands"))
                .andExpect(jsonPath("$.address").value("Moscow"))
                .andExpect(jsonPath("$.opening_hours").value("08:00"));
    }

    @Test
    void findShelter() throws Exception {
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setName("Good Hands");
        shelterEntity.setAddress("Moscow");
        shelterEntity.setOpening_hours("08:00");
        shelterEntity.setId(7L);

        when(shelterService.findShelterById(anyLong())).thenReturn(shelterEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(SHELTER_MAPPING + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.name").value("Good Hands"))
                .andExpect(jsonPath("$.address").value("Moscow"))
                .andExpect(jsonPath("$.opening_hours").value("08:00"));


    }

    @Test
    void findShelterWithNull() throws Exception {


        when(shelterService.findShelterById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(SHELTER_MAPPING + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void editShelter() throws Exception {
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setName("Good Hands");
        shelterEntity.setAddress("Moscow");
        shelterEntity.setOpening_hours("08:00");
        shelterEntity.setId(7L);

        when(shelterService.editShelter(anyLong(), any())).thenReturn(shelterEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(SHELTER_MAPPING + "/" + 7)
                        .content(new ObjectMapper().writeValueAsString(shelterEntity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.name").value("Good Hands"))
                .andExpect(jsonPath("$.address").value("Moscow"))
                .andExpect(jsonPath("$.opening_hours").value("08:00"));
    }

    @Test
    void editShelterWithNull() throws Exception {
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setName("Good Hands");
        shelterEntity.setAddress("Moscow");
        shelterEntity.setOpening_hours("08:00");
        shelterEntity.setId(7L);

        when(shelterService.editShelter(anyLong(), any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(SHELTER_MAPPING + "/" + 7)
                        .content(new ObjectMapper().writeValueAsString(shelterEntity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteShelter() throws Exception {
        doNothing().when(shelterService).deleteShelter(anyLong());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(SHELTER_MAPPING + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllShelters() throws Exception {
        ShelterEntity goodHands = new ShelterEntity();
        goodHands.setId(3L);
        goodHands.setAddress("Moscow");
        goodHands.setName("Good Hands");
        goodHands.setOpening_hours("08:00");

        ShelterEntity getPet = new ShelterEntity();
        getPet.setName("Get Pet");
        getPet.setId(7L);
        getPet.setAddress("Piter");
        getPet.setOpening_hours("09:00");


        when(shelterService.getAllShelters()).thenReturn(List.of(goodHands, getPet));
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(SHELTER_MAPPING + "/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(3, 7)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Good Hands", "Get Pet")))
                .andExpect(jsonPath("$[*].address", containsInAnyOrder("Moscow", "Piter")))
                .andExpect(jsonPath("$[*].opening_hours", containsInAnyOrder("08:00", "09:00")));

    }
}