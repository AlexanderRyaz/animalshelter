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
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.core.entity.ShelterEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.core.model.AnimalType;
import ru.devpro.animalshelter.service.AnimalService;
import ru.devpro.animalshelter.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserController.class, UserService.class})
class UserControllerTest {
    private static final String ANIMAL_USER = "/user";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUser() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("Sasha");
        userEntity.setChatId(1L);
        userEntity.setId(7L);
        userEntity.setIsVolunteer(false);

        UserEntity user = new UserEntity();
        user.setUserName("Sasha");
        user.setIsVolunteer(false);

        when(userService.createUser(any(UserEntity.class))).thenReturn(userEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(ANIMAL_USER)
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.userName").value("Sasha"))
                .andExpect(jsonPath("$.isVolunteer").value(false))
                .andExpect(jsonPath("$.chatId").value(1L));
    }

    @Test
    void findUserById() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("Sasha");
        userEntity.setChatId(1L);
        userEntity.setId(7L);
        userEntity.setIsVolunteer(false);

        when(userService.findUserById(anyLong())).thenReturn(userEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ANIMAL_USER + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.userName").value("Sasha"))
                .andExpect(jsonPath("$.isVolunteer").value(false))
                .andExpect(jsonPath("$.chatId").value(1L));
    }
    @Test
    void findUserWithNull() throws Exception {


        when(userService.findUserById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ANIMAL_USER + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void addUserAnimal() throws Exception {
        UserEntity userEntity = new UserEntity("Oleg", 1L, false);
        when(userService.addUserAnimal(1L,3L)).thenReturn(userEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(ANIMAL_USER + "/7/animal" )
                        .param("animalId","3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(ANIMAL_USER + "/" + 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsers() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("Sasha");
        userEntity.setChatId(1L);
        userEntity.setId(7L);
        userEntity.setIsVolunteer(false);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUserName("Sergei");
        userEntity1.setChatId(3L);
        userEntity1.setId(2L);
        userEntity1.setIsVolunteer(true);

        when(userService.getAllUsers()).thenReturn(List.of(userEntity, userEntity1));
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ANIMAL_USER + "/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(7, 2)))
                .andExpect(jsonPath("$[*].userName", containsInAnyOrder("Sasha", "Sergei")))
                .andExpect(jsonPath("$[*].chatId", containsInAnyOrder(1, 3)))
                .andExpect(jsonPath("$[*].isVolunteer", containsInAnyOrder(false, true)));
    }
    @Test
    void sendMessageToUser() throws Exception {
        doNothing().when(userService).sendMessageToUser(1L, "test");
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ANIMAL_USER + "/7/message" )
                        .param("text","test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void extendPeriod() throws Exception {
        when(userService.extendPeriod(1L, 10)).thenReturn(new UserEntity());
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(ANIMAL_USER + "/7/period" )
                        .param("number","10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void findReportUser() throws Exception {
        when(userService.findReportUser(1L)).thenReturn(List.of(new ReportEntity()));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ANIMAL_USER + "/7/reports" )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}