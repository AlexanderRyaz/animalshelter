package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.core.model.AnimalType;
import ru.devpro.animalshelter.core.repository.AnimalRepository;
import ru.devpro.animalshelter.core.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@WebMvcTest({UserService.class, UserRepository.class, AnimalRepository.class, TelegramBot.class})
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AnimalRepository animalRepository;
    @MockBean
    private TelegramBot telegramBot;

    @Test
    void createUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserName("Oleg");
        userEntity.setChatId(2L);
        userEntity.setIsVolunteer(true);

        when(userRepository.save(any())).thenReturn(userEntity);
        UserEntity user = userService.createUser(userEntity);
        assertEquals(1L, user.getId());
        assertEquals("Oleg", user.getUserName());
        assertEquals(2L, user.getChatId());
        assertTrue(true);

    }

    @Test
    void findUserById() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserName("Oleg");
        userEntity.setChatId(2L);
        userEntity.setIsVolunteer(true);

        when(userRepository.findById(anyLong())).thenReturn(userEntity);
        UserEntity user = userService.findUserById(1L);
        assertEquals(1L, user.getId());
        assertEquals("Oleg", user.getUserName());
        assertEquals(2L, user.getChatId());
        assertTrue(true);
    }


    @Test
    void getAllUsers() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserName("Oleg");
        userEntity.setChatId(2L);
        userEntity.setIsVolunteer(true);

        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        Collection<UserEntity> allUsers = userService.getAllUsers();
        assertEquals(1, allUsers.size());
    }
    @Test
    void deleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());
        userService.deleteUser(5L);
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}