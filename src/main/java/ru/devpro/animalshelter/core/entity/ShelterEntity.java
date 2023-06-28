package ru.devpro.animalshelter.core.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "shelters")
public class ShelterEntity {
    /**
     * Генерация уникального ID
     * Название приюта
     * Адресс приюта
     * Часы работы приюта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "opening_hours")
    private String opening_hours;

}
