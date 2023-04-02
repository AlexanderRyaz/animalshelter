package ru.devpro.animalshelter.service;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {


    public UserService() {

    }

    //метод добавления пользователя в БД
    public boolean creteUser() {    // это либо пользователь, либо сотрудник приюта
        return false;
    }

    // метод нахлждения пользователя по id
    public void findUserById() {

    }

    //метод добавления животного пользователю по id
    public void addUserAnimal() {

    }

    //метод удаления пользователя из БД
    public void deleteUser() {

    }

    //метод нахождения всех пользователей в БД
    public Collection getAllUsers() {
        return null;
    }

}
