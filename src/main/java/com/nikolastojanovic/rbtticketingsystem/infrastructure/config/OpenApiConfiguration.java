package com.nikolastojanovic.rbtticketingsystem.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApi () {
        return new OpenAPI()
                .info(new Info().title("RBTTicketingSystem API").description("RBTTicketingSystem API"))
                .components(new Components().addSecuritySchemes("BearerAuth", new SecurityScheme().name("BearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
