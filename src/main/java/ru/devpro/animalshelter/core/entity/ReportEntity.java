package ru.devpro.animalshelter.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "report")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime dateTime;

    @Column(name = "name")
    private String animalName;

    @Column(name = "ration")
    private String ration;

    @Column(name = "health")
    private String health;

    @Column(name = "behavior")
    private String behavior;

    @OneToOne
    @JoinColumn(name = "photoId")
    private PhotoEntity photoEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    public ReportEntity() {

    }

    public ReportEntity(String animalName, String ration, String health, String behavior, PhotoEntity photoEntity, LocalDateTime dateTime, UserEntity userEntity) {
        this.animalName = animalName;
        this.ration = ration;
        this.health = health;
        this.behavior = behavior;
        this.photoEntity = photoEntity;
        this.dateTime = dateTime;
        this.userEntity = userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportEntity report = (ReportEntity) o;
        return Objects.equals(id, report.id) && Objects.equals(dateTime, report.dateTime) && Objects.equals(animalName, report.animalName) && Objects.equals(ration, report.ration) && Objects.equals(health, report.health) && Objects.equals(behavior, report.behavior) && Objects.equals(photoEntity, report.photoEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, animalName, ration, health, behavior, photoEntity);
    }

    @Override
    public String toString() {
        return "ReportEntity{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", animalName='" + animalName + '\'' +
                ", ration='" + ration + '\'' +
                ", health='" + health + '\'' +
                ", behavior='" + behavior + '\'' +
                ", photoEntity=" + photoEntity +
                ", userEntity=" + userEntity +
                '}';
    }
}
