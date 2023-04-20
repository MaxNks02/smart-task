package com.example.smartbank_task_webflux.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getInfo());
    }

    private Info getInfo() {
        return new Info()
                .title("Smart Task API")
                .description("DEMO TASK")
                .version("Version: 1")
                .contact(getContact());
    }

    private Contact getContact() {
        return new Contact()
                .name("Maman")
                .email("maxnks02@gmail.com")
                .url("https://t.me/VECHNO_MOLODOl");
    }
}
