package com.fashion.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fashion Catalog API")
                        .version("1.0")
                        .description("Документація API для управління каталогом моди (з підтримкою JWT)"))
                // 1. Додаємо вимогу авторизації до кожного запиту в Swagger UI
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                // 2. Описуємо, як саме працює авторизація (Bearer + JWT)
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}