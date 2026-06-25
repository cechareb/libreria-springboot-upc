package edu.upc.sistemas.libreriaspringboot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "API Librería Spring Boot",
                version = "1.0.0",
                description = "Documentación de la API REST para la gestión de autores y libros. " +
                        "Proyecto sin Lombok, con capas Entity, Repository, Service y Controller.",
                contact = @Contact(name = "Cesar", email = "cesar@ejemplo.com")
        )
)
@SpringBootApplication
public class LibreriaSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibreriaSpringbootApplication.class, args);
    }

}
