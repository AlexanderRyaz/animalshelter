package ru.devpro.animalshelter.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.devpro.animalshelter.core.model.AnimalType;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "animals")
public class AnimalEntity {

    /**
     * Генерация уникального id
     * Поле с именем
     * Поле тип животного: Кот, собака
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "animal_type")
    private AnimalType animalType;

    @Column(name = "animal_name")
    private String animalName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalEntity that)) return false;
        return Objects.equals(id, that.id) && animalType == that.animalType && Objects.equals(animalName, that.animalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, animalType, animalName);
    }

    @Override
    public String toString() {
        return "AnimalEntity{" +
                "id=" + id +
                ", animalType=" + animalType +
                ", animalName='" + animalName + '\'' +
                '}';
    }

}
