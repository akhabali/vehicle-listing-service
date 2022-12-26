package io.github.akhabali;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationEntryPoint.class, args);
    }

    @Bean
    public OpenAPI openAPI(@Value("${app.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Vehicle Listing Service")
                        .description("A RESTful service for managing listings for online advertising service.")
                        .version(appVersion)
                        .contact(new Contact().name("Anas Khabali").email("anas.khabali@gmail.com").url("https://github.com/akhabali"))
                        .license(new License().name("MIT").url("https://github.com/akhabali/vehicle-listing-service/blob/main/LICENSE.md")));
    }

}
