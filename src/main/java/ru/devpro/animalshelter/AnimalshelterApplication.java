package ru.devpro.animalshelter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class AnimalshelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalshelterApplication.class, args);
    }

}
